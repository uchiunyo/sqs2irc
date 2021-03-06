package org.ukiuni.sqs2irc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSON;

import com.amazonaws.services.sqs.model.Message;

public class ExecutorManager {
	private static Map<String, IRC> ircServerMap = new HashMap<String, IRC>();
	private static List<SQS> sqsList = new ArrayList<SQS>();

	public static void execute() {
		execute("config.xml");
	}
	
	public static void execute(String configPath) {
		Config config = Config.load(configPath);
		List<SQSConfig> sqsConfigs = config.getSqsConfig();
		for (final SQSConfig sqsConfig : sqsConfigs) {
			final SQS sqs = new SQS(sqsConfig.getAWSAccessKey(), sqsConfig.getAWSSecretKey(), sqsConfig.getSQSURL());
			final IRC ircServer;
			if (ircServerMap.containsKey(sqsConfig.getIRCServer())) {
				ircServer = ircServerMap.get(sqsConfig.getIRCServer());
			} else {
				try {
					ircServer = new IRC(sqsConfig.getIRCServer(), sqsConfig.getIRCPort(), sqsConfig.getIRCNickname());
					ircServerMap.put(sqsConfig.getIRCServer(), ircServer);
				} catch (IOException e) {
					Log.error("skip server " + sqsConfig.getIRCServer(), e);
					continue;
				}
			}

			sqs.listen(new SQS.SQSListener() {
				public void onMessage(Message message) {
					try {
						ircServer.sendMessage(sqsConfig.getIRCChannel(), JSON.decode(message.getBody(),Map.class).get("Message").toString());
						sqs.deleteMessage(message.getReceiptHandle());
					} catch (IOException e) {
						Log.error("sendMessage error to server " + sqsConfig.getIRCServer() + ", message is " + message.getBody(), e);
					}
				}
			});
			sqsList.add(sqs);
		}
	}

	public static void stop() {
		for (SQS sqs : sqsList) {
			sqs.stopListen();
		}
		for (IRC irc : ircServerMap.values()) {
			try {
				irc.stop();
			} catch (IOException e) {
				Log.error("stop erro on server ", e);
				continue;
			}
		}
	}
}

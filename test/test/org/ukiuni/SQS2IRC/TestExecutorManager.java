package test.org.ukiuni.SQS2IRC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ukiuni.irc4j.IRCClient;
import org.ukiuni.irc4j.IRCEventHandler;
import org.ukiuni.sqs2irc.Config;
import org.ukiuni.sqs2irc.ExecutorManager;
import org.ukiuni.sqs2irc.SQSConfig;

public class TestExecutorManager {

	@Test
	public void test() throws IOException, InterruptedException {
		String message = "hellow. iam sqs2IRC!";
		ExecutorManager.execute("config_test.xml");
		SNSUtil.send(message);
		Config config = Config.load("config_test.xml");
		SQSConfig sqs = config.getSqsConfig().get(0);
		IRCClient irc = new IRCClient(sqs.getIRCServer(), sqs.getIRCPort(), "tester", "localhost", "realTest");
		final List<String> receivedMessages = new ArrayList<String>();
		irc.addHandler(new IRCEventHandler() {

			@Override
			public void onServerMessage(int id, String message) {

			}

			@Override
			public void onMessage(String channelName, String from, String message) {
				receivedMessages.add(message);

			}

			@Override
			public void onError(Throwable e) {

			}
		});
		irc.connect();
		irc.sendJoin(sqs.getIRCChannel());
		Thread.sleep(3000);
		Assert.assertTrue(receivedMessages.contains(message));
		irc.sendQuit();
	}
}

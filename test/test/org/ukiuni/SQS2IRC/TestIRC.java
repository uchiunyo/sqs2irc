package test.org.ukiuni.SQS2IRC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;
import org.ukiuni.irc4j.IRCClient;
import org.ukiuni.irc4j.IRCEventHandler;
import org.ukiuni.irc4j.server.IRCServer;
import org.ukiuni.sqs2irc.IRC;

public class TestIRC {

	@Test
	public void testSendMessage() throws IOException, InterruptedException {
		IRCClient client;
		String channel = "#testChannel";
		int port = 10888;
		String serverHostname = "localhost";
		IRCServer ircServer = null;
		
		try {
			IrcConfig config;
			config = JAXB.unmarshal(TestIRC.class.getClassLoader().getResourceAsStream("TestIRCConfig.xml"), IrcConfig.class);
			channel = config.getIRCChannel();
			port = config.getIRCPort();
			serverHostname = config.getIRCServer();
			client = new IRCClient(serverHostname, port, config.getIRCNickname(), serverHostname, config.getIRCNickname());
			client.connect();
		} catch (Exception e) {
			ircServer = new IRCServer();
			ircServer.start();
			client = new IRCClient(serverHostname, port, "test1", serverHostname, "test1");
			client.connect();
		}
		
		final List<String> messageList = new ArrayList<String>();
		client.addHandler(new IRCEventHandler() {
			@Override
			public void onServerMessage(int id, String message) {
			}

			@Override
			public void onMessage(String channelName, String from, String message) {
				messageList.add(from + ":" + message);
			}

			@Override
			public void onError(Throwable e) {
			}
		});
		Thread.sleep(3000);
		client.sendJoin(channel);
		Thread.sleep(3000);

		IRC irc = new IRC(serverHostname, port, "test2");
		Thread.sleep(3000);
		irc.sendMessage(channel, "testMessage");

		Thread.sleep(3000);

		Assert.assertEquals("test2:testMessage", messageList.get(0));

		client.sendQuit();
		if (null != ircServer) {
			ircServer.stop();	
		}
	}

	public static class IrcConfig {
		private String IRCServer;
		private int IRCPort;
		private String IRCChannel;
		private String IRCNickname;

		public String getIRCServer() {
			return IRCServer;
		}

		public void setIRCServer(String iRCServer) {
			IRCServer = iRCServer;
		}

		public int getIRCPort() {
			return IRCPort;
		}

		public void setIRCPort(int iRCPort) {
			IRCPort = iRCPort;
		}

		public String getIRCChannel() {
			return IRCChannel;
		}

		public void setIRCChannel(String iRCChannel) {
			IRCChannel = iRCChannel;
		}

		public String getIRCNickname() {
			return IRCNickname;
		}

		public void setIRCNickname(String iRCNickname) {
			IRCNickname = iRCNickname;
		}
	}

}

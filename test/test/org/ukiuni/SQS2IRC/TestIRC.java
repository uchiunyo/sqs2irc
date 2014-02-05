package test.org.ukiuni.SQS2IRC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ukiuni.irc4j.IRCClient;
import org.ukiuni.irc4j.IRCEventHandler;
import org.ukiuni.irc4j.server.IRCServer;
import org.ukiuni.sqs2irc.IRC;

public class TestIRC {

	@Test
	public void test() throws IOException, InterruptedException {
		int port = 10888;
		String channel = "testChannel";
		IRCServer ircServer = new IRCServer();
		ircServer.start();
		IRCClient client = new IRCClient("localhost", port, "test1", "localhost", "test1");
		client.connect();
		final List<String> messageList = new ArrayList<String>();
		client.addHandler(new IRCEventHandler() {
			@Override
			public void onServerMessage(int id, String message) {
			}
			
			@Override
			public void onMessage(String channelName, String from, String message) {
				messageList.add(from+":"+message);
			}
			
			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				
			}
		});
		Thread.sleep(3000);
		client.sendJoin(channel);
		Thread.sleep(3000);
		
		IRC irc = new IRC("localhost", port, "test2");
		Thread.sleep(3000);
		irc.sendMessage(channel, "testMessage");
		
		Thread.sleep(3000);
		
		Assert.assertEquals("test2:testMessage",messageList.get(0));
		
		client.sendQuit();
		ircServer.stop();
	}

}

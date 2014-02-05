package org.ukiuni.sqs2irc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ukiuni.irc4j.IRCClient;

public class IRC {
	private IRCClient ircClient;
	private List<String> joinedChannel = new ArrayList<String>();
	public IRC(String ircServer, int port, String ircNickname) throws IOException {
		ircClient = new IRCClient(ircServer, port, ircNickname, "localhost", ircNickname);
		ircClient.connect();
	}

	public void stop() throws IOException {
		ircClient.sendQuit();
	}

	public void sendMessage(String ircChannel, String body) throws IOException {
		if(!joinedChannel.contains(ircChannel)){
			ircClient.sendJoin(ircChannel);
		}
		ircClient.sendMessage(ircChannel, body);
	}
}

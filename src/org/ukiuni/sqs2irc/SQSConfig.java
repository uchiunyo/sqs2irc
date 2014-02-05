package org.ukiuni.sqs2irc;

public class SQSConfig {
	//TBD parameters must start small char but elicpse source generator create accessor starts small char, so below parameter starts large char.
	private String AWSAccessKey;
	private String AWSSecretKey;
	private String SQSEndpoint;
	private String IRCServer;
	private int IRCPort;
	private String IRCChannel;
	private String IRCNickname;
	public String getAWSAccessKey() {
		return AWSAccessKey;
	}
	public void setAWSAccessKey(String aWSAccessKey) {
		AWSAccessKey = aWSAccessKey;
	}
	public String getAWSSecretKey() {
		return AWSSecretKey;
	}
	public void setAWSSecretKey(String aWSSecretKey) {
		AWSSecretKey = aWSSecretKey;
	}
	public String getSQSEndpoint() {
		return SQSEndpoint;
	}
	public void setSQSEndpoint(String sQSEndpoint) {
		SQSEndpoint = sQSEndpoint;
	}
	public String getIRCServer() {
		return IRCServer;
	}
	public void setIRCServer(String iRCServer) {
		IRCServer = iRCServer;
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
	public int getIRCPort() {
		return IRCPort;
	}
	public void setIRCPort(int iRCPort) {
		IRCPort = iRCPort;
	}
}

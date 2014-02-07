package org.ukiuni.sqs2irc;

import javax.xml.bind.JAXB;

public class ProxyConfig {
	private String proxyHost;
	private int proxyPort;
	private String proxyUserName;
	private String proxyPassword;
	private boolean proxyNeeded = false;

	public static ProxyConfig load() {
		ProxyConfig config;
		try {
			config = JAXB.unmarshal(Config.class.getClassLoader().getResourceAsStream("proxyConfig.xml"), ProxyConfig.class);
			config.setProxyNeeded(true);
		} catch (Exception e) {
			return new ProxyConfig();
		}

		return config;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public boolean isProxyNeeded() {
		return proxyNeeded;
	}

	public void setProxyNeeded(boolean proxyNeeded) {
		this.proxyNeeded = proxyNeeded;
	}
}

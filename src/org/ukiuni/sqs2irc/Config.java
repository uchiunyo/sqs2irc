package org.ukiuni.sqs2irc;

import java.util.List;

import javax.xml.bind.JAXB;

public class Config {
	private List<SQSConfig> sqsConfig;
	
	public static Config load() {
		return load("config.xml");
	}
	
	public static Config load(String configPath) {
		Config config = JAXB.unmarshal(Config.class.getClassLoader().getResourceAsStream(configPath), Config.class);
		
		return config;
	}

	public List<SQSConfig> getSqsConfig() {
		return sqsConfig;
	}

	public void setSqsConfig(List<SQSConfig> sqsConfig) {
		this.sqsConfig = sqsConfig;
	}
}

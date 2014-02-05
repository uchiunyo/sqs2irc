package org.ukiuni.sqs2irc;

import java.util.ArrayList;
import java.util.List;

public class Config {
	private List<SQSConfig> sqsConfig;
	public static Config load() {
		Config config = new Config();
		config.sqsConfig = new ArrayList<SQSConfig>();
		//TODO loadConfig
		
		return config;
	}

	public List<SQSConfig> getSQSConfig() {
		return sqsConfig;
	}
}

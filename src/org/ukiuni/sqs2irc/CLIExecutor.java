package org.ukiuni.sqs2irc;

public class CLIExecutor {
	public static void main(String[] args) {
		ExecutorManager.execute();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				ExecutorManager.stop();
			}
		});
	}
}

package org.ukiuni.sqs2irc;

import com.amazonaws.services.sqs.model.Message;

public class SQS {

	public SQS(String awsAccessKey, String awsSecretKey, String sqsEndpoint) {
		// TODO Auto-generated constructor stub
	}

	public void listen(SQSListener sqsListener) {
		// TODO Auto-generated method stub

	}

	public void deleteMessage(String receiptHandle) {
		// TODO Auto-generated method stub

	}

	public void stopListen() {
		// TODO Auto-generated method stub

	}

	public static interface SQSListener {
		public void onMessage(Message message);
	}
}

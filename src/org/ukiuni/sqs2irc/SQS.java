package org.ukiuni.sqs2irc;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class SQS {
	private AmazonSQS sqs;
	private String queueURL;
	private List<Thread> receiveThreadList = new ArrayList<Thread>();
	private boolean listening = true;

	public SQS(String awsAccessKey, String awsSecretKey, String queueURL) {
		sqs = createAmazonSQSClient(awsAccessKey, awsSecretKey);
		this.queueURL = queueURL;
	}

	public void listen(final SQSListener sqsListener) {
		Thread thread = new Thread() {
			public void run() {
				while (listening) {
					ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueURL).withMaxNumberOfMessages(10).withWaitTimeSeconds(20);
					List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
					for (Message message : messages) {
						sqsListener.onMessage(message);
					}
				}
			}
		};
		thread.start();
		receiveThreadList.add(thread);
	}

	public void deleteMessage(String receiptHandle) {
		DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
		deleteMessageRequest.setQueueUrl(queueURL);
		deleteMessageRequest.setReceiptHandle(receiptHandle);
		sqs.deleteMessage(deleteMessageRequest);
	}

	@SuppressWarnings("deprecation")
	public void stopListen() {
		listening = false;
		for (Thread thread : receiveThreadList) {
			try {
				thread.suspend();
			} catch (Throwable e) {
			}
		}
		receiveThreadList.clear();
	}

	public static interface SQSListener {
		public void onMessage(Message message);
	}

	private AmazonSQSClient createAmazonSQSClient(String accessKey, String secretKey) {
		ProxyConfig proxyConfig = ProxyConfig.load();
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		ClientConfiguration configuration = new ClientConfiguration();
		if (proxyConfig.isProxyNeeded()) {
			configuration.setProxyHost(proxyConfig.getProxyHost());
			configuration.setProxyPort(proxyConfig.getProxyPort());
			if (null != proxyConfig.getProxyUserName()) {
				configuration.setProxyUsername(proxyConfig.getProxyUserName());
				configuration.setProxyPassword(proxyConfig.getProxyPassword());
			}
		}
		return new AmazonSQSClient(credentials, configuration);
	}
}

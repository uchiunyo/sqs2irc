package test.org.ukiuni.SQS2IRC;

import javax.xml.bind.JAXB;

import org.ukiuni.sqs2irc.ProxyConfig;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SNSUtil {

	public static void main(String[] args) {
		send("Complete!!!!");
	}
	public static void send(String message) {
		SnsConfig config = JAXB.unmarshal(SNSUtil.class.getClassLoader().getResourceAsStream("SNSForTest.xml"), SnsConfig.class);
		String arn = config.getArn();
		String awsAccessKey = config.getAwsAccessKey();
		String awsSecretKey = config.getAwsSecretKey();
		String endpoint = config.getEndpoint();
		AmazonSNSClient client = createAmazonSQSClient(awsAccessKey, awsSecretKey);
		client.setEndpoint(endpoint);
		PublishRequest publishRequest = new PublishRequest(arn, "ShinkeiNotificationManager");
		publishRequest.setMessage(message);
		PublishResult result = client.publish(publishRequest);
		System.out.println("result = "+result.getMessageId());
	}

	private static AmazonSNSClient createAmazonSQSClient(String accessKey, String secretKey) {
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
		return new AmazonSNSClient(credentials, configuration);
	}

	public static class SnsConfig {
		private String arn;
		private String awsAccessKey;
		private String awsSecretKey;
		private String endpoint;

		public String getAwsAccessKey() {
			return awsAccessKey;
		}

		public void setAwsAccessKey(String awsAccessKey) {
			this.awsAccessKey = awsAccessKey;
		}

		public String getAwsSecretKey() {
			return awsSecretKey;
		}

		public void setAwsSecretKey(String awsSecretKey) {
			this.awsSecretKey = awsSecretKey;
		}

		public String getArn() {
			return arn;
		}

		public void setArn(String arn) {
			this.arn = arn;
		}

		public String getEndpoint() {
			return endpoint;
		}

		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}

	}
}

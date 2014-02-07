package test.org.ukiuni.SQS2IRC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSON;

import org.junit.Assert;
import org.junit.Test;
import org.ukiuni.sqs2irc.Config;
import org.ukiuni.sqs2irc.SQS;
import org.ukiuni.sqs2irc.SQS.SQSListener;
import org.ukiuni.sqs2irc.SQSConfig;

import com.amazonaws.services.sqs.model.Message;

public class TestSQS {

	@Test
	public void testListen() throws InterruptedException {
		Config config = Config.load("config_test.xml");
		List<SQSConfig> sqsConfigs = config.getSqsConfig();
		String message = "テストメッセージですよ。";
		final List<Message> sqsRecevedMessages = new ArrayList<Message>();
		for (final SQSConfig sqsConfig : sqsConfigs) {
			final SQS sqs = new SQS(sqsConfig.getAWSAccessKey(), sqsConfig.getAWSSecretKey(), sqsConfig.getSQSURL());
			sqs.listen(new SQSListener() {
				@Override
				public void onMessage(Message message) {
					sqsRecevedMessages.add(message);
					sqs.deleteMessage(message.getReceiptHandle());
				}
			});
		}

		SNSUtil.send(message);
		Thread.sleep(5000);
		Assert.assertEquals(message, JSON.decode(sqsRecevedMessages.get(0).getBody(),Map.class).get("Message").toString());
	}

}

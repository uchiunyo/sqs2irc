package test.org.ukiuni.SQS2IRC;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ukiuni.sqs2irc.Config;

public class TestConfig {

	@Test
	public void testLoad() {
		Config config = Config.load("config_test.xml");
		assertEquals(3,config.getSqsConfig().size());
		assertEquals("sqsurl1",config.getSqsConfig().get(1).getSQSURL());
		assertEquals("sqsurl2",config.getSqsConfig().get(2).getSQSURL());
	}

}

package org.ukiuni.sqs2irc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ExecutorListener
 * 
 */
@WebListener
public class ExecutorListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ExecutorListener() {
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ExecutorManager.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ExecutorManager.execute();
	}

}

package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import singletonPattern.LogToFile;

public class WebContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LogToFile.getInstance().logStop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LogToFile.getInstance().logStart();
	}

}

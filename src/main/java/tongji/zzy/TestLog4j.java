package tongji.zzy;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;

public class TestLog4j {
	public static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestLog4j.class);

	public static void main(String[] args) {
//		PropertyConfigurator.configure("log4j.properties");
//		Logger logger = Logger.getLogger(TestLog4j.class);
//		logger.info("logging...");
	
		PropertyConfigurator.configure("log4j.properties");
		logger.info("test logging...");
		

	}

}

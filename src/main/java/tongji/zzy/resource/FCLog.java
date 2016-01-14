package tongji.zzy.resource;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tongji.zzy.TestLog4j;

public class FCLog {

	public static Logger getLogger(Class clazz) {
		PropertyConfigurator.configure("log4j.properties");
		return LoggerFactory.getLogger(clazz);
	}
	
	public static void main(String[] args) {
		Logger log = FCLog.getLogger(FCLog.class);
		log.info("test." + new Throwable(new IllegalArgumentException()).getMessage());
		
	}


}

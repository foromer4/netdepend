package picscout.depend.dependency.utils;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;


public class ConfigHelper {
	
	private static Configuration config;
	public static void init() {
		ConfigurationFactory factory = new ConfigurationFactory("/config.xml");
		try {
			config = factory.getConfiguration();
		} catch (ConfigurationException e) {
			//log exception [O.S]
			
		}
	}
	
	public static String readString(String key, String defaultValue) {
		String res =config.getString(key);
		if(res == null) {
			res = defaultValue;
		}
		return res;
	}

}

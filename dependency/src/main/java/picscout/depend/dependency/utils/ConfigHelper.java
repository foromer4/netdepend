package picscout.depend.dependency.utils;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.XMLConfiguration;


public class ConfigHelper {
	
	private static Configuration config;
	public static void init(String filePath) {
		try {
			config = new XMLConfiguration(filePath);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

package picscout.depend.dependency.utils;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


public class ConfigUtils {
	
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
	
	public static Boolean readBoolean(String key, boolean defaultValue) {
		Boolean res =config.getBoolean(key);
		if(res == null) {
			res = defaultValue;
		}
		return res;
	}
	
	/**
	 * Read a list of string
	 * @param key key to read
	 * @param defaultValue default array of result
	 * @param seperator regex. optional, if null or empty ; will be used.
	 * @return
	 */
	public static String[] readList(String key ,String[] defaultValue, String seperator) {
		String resList = config.getString(key);
		if(resList == null) {
			return defaultValue;
		}
		
		if(seperator == null || seperator.isEmpty()) {
			seperator = ";";
		}
		
		String[] result = resList.split(seperator);
		return result;
	}

}

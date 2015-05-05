package picscout.depend.dependency.utils;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;


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
		Boolean res = defaultValue;
		try		{
		res =config.getBoolean(key);
		}
		catch (Exception e)		{
			//OK to fail to find element
		}
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
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String name, T defaultImplemntation) {
		try {
		BeanDeclaration decl = new XMLBeanDeclaration((HierarchicalConfiguration) config, name);
		return (T)BeanHelper.createBean(decl);
		}
		catch (Exception e) {
			//Failed to get type os OK, use default
			return defaultImplemntation;
		}
		
	}

}

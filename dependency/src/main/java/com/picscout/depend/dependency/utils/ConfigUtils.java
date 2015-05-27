package com.picscout.depend.dependency.utils;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;

/**
 * Config wrapper, handles config values including injections from beans.
 * @author OSchliefer
 *
 */
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
	
	/**
	 * Read string from config
	 * @param key key to read
	 * @param defaultValue default value , if not found
	 * @return value of key, or default if not found
	 */
	public static String readString(String key, String defaultValue) {
		String res =config.getString(key);
		if(res == null) {
			res = defaultValue;
		}
		return res;
	}
	
	/**
	 * Read boolean value
	 * @param key key 
	 * @param defaultValue value if not found
	 * @return value of key, or default if not found
	 */
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
	 * @return list of strings
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
	
	
	/**
	 * get instance from bean
	 * @param name name of key
	 * @param defaultImplemntation default instance, if fails to load
	 * @param <T> type
	 * @return injected instance
	 */
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

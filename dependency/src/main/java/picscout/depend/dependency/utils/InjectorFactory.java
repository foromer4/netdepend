package picscout.depend.dependency.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import picscout.depend.dependency.main.Runner;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorFactory {
	
	private static Injector injector;
	private static final Logger logger = LogManager.getLogger(InjectorFactory.class
			.getName());
	
	public static Injector getInjector() {
		if(injector == null){
			initInjector();
		}
		return injector;
	}
	
	private static void initInjector() {
		AbstractModule module = ConfigUtils.<AbstractModule>getInstance("injection.AppInjector", null);
		if(module != null) {
			logger.info("Init AppInjector as type:" + module.getClass().getName());
		}
		else {
			logger.error("Failed to init AppInjector, it is not well defined in the config.");
		}
		injector = Guice.createInjector(module);
	}
	

}

package picscout.depend.dependency.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.interfaces.ISolutionMapper;
import picscout.depend.dependency.interfaces.IStatePersist;
import picscout.depend.dependency.utils.ConfigUtils;

/**
 * Main enrty point
 * 
 * @author oschliefer
 *
 */
public class Runner {

	private IMapBuilder builder;
	private IStatePersist persister;
	private Boolean isInitialized = false;
	private static final Logger logger = LogManager.getLogger(Runner.class
			.getName());
	private static String[] roots;
	private boolean shoulUsePersistedState;
    private Injector injector;
	
	/**
	 * 
	 */
	public Runner() {
			
	  
	}

	public void CalculateDependencies() {
		logger.info("Starting calculation of dependencies");
		initIfRequired();
		if (builder == null) {
			builder = injector.getInstance(IMapBuilder.class);
		}
		builder.parse(roots);
		persister.persist(builder);
	}

	public List<IProjectDescriptor> getProjectsThatDepeantOnProject(
			IProjectDescriptor projectDescriptor) {
		initIfRequired();
		loadBuilder();
		IProjectDependencyMapper mapper = builder.getProjectMapper();
		return mapper.getProjectsThatDepeantOn(projectDescriptor);
	}
	
	
	public List<IProjectDescriptor> getProjectsThatDepeantOnProject(
			List<IProjectDescriptor> projectDescriptors) {
		initIfRequired();
		loadBuilder();
		IProjectDependencyMapper mapper = builder.getProjectMapper();
		return mapper.getProjectsThatDepeantOn(projectDescriptors);
	}

	public List<ISolution> getSolutionsThatDependOnProject(
			IProjectDescriptor projectDescriptor) {
		initIfRequired();
		loadBuilder();
		ISolutionMapper solutionMapper = builder.getSolutionMapper();
		List<IProjectDescriptor> descriptors = new ArrayList<IProjectDescriptor>();
		descriptors.add(projectDescriptor);
		return solutionMapper.getSolutionsByProjects(descriptors);
	}

	public List<ISolution> getSolutionsThatDependOnSolutionsByNames(
			List<String> names) {
		initIfRequired();
		loadBuilder();
		ISolutionMapper solutionMapper = builder.getSolutionMapper();
		return solutionMapper.getSolutionsBySolutionsNames(names);
	}

	private void init() {

		initConfig();
		
		initInjector(); 		  
		
		
		roots = ConfigUtils.readList("rootPath", new String[] { "c:\\temp" },
				null);
		logger.info("Root directories to scan are:" + roots);
		initPersister();	
		isInitialized = true;
	}

	private void initPersister() {
		persister  = injector.getInstance(IStatePersist.class);		
		shoulUsePersistedState = ConfigUtils.readBoolean("shoulUsePersistedState" , true);
	}

	private void initConfig() {
		String configPath = System.getProperty("config_file_path");
		if (configPath == null || configPath.isEmpty()) {
			logger.info("no config path passed in. use default");
			configPath = Runner.class.getResource("/config.xml").toString();
		}

		logger.info("Setting config path to be:" + configPath);
		ConfigUtils.init(configPath);
	}

	private void initInjector() {
		AbstractModule module = ConfigUtils.<AbstractModule>getInstance("injection.AppInjector", null);
		if(module != null) {
			logger.info("Init AppInjector as type:" + module.getClass().getName());
		}
		else {
			logger.error("Failed to init AppInjector, it is not well defined in the config.");
		}
		injector = Guice.createInjector(module);
	}

	private void loadBuilder() {
		if(shoulUsePersistedState) {
		builder = persister.load();
		}
		if (builder == null) {
			builder = injector.getInstance(IMapBuilder.class);
			builder.parse(roots);
		}
	}

	private void initIfRequired() {
		if (!isInitialized) {
			init();
		}
	}

}

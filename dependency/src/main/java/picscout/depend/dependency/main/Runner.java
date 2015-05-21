package picscout.depend.dependency.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.interfaces.ISolutionMapper;
import picscout.depend.dependency.interfaces.IStatePersist;
import picscout.depend.dependency.utils.ConfigUtils;
import picscout.depend.dependency.utils.InjectorFactory;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
/**
 * Main entry point. can be used to run calculation of dependencies of to get dependent projects/solutions from a pre saved map.
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
	private String configPath;
	
	/**
	 * C'tor, use config and logpath from envrionment variables ('config_file_path' , and 'log4j.config')
	 */
	public Runner() {
		configPath = System.getProperty("config_file_path");	  
	}
	
	/**
	 * C'tor, take explicit definitions of config and logpath.
	 */
	public Runner(String configPath, String log4jPath) {
		this.configPath = configPath;
		LogManager.resetConfiguration(); 
		DOMConfigurator.configure(log4jPath);		
		logger.info("Config path set externally to: " + configPath + " ,log4j config path set externally to: " + log4jPath);
	}

	/**
	 * Calculate dependenices, save them in state file
	 */
	public void CalculateDependencies() {
		logger.info("Starting calculation of dependencies");
		initIfRequired();
		if (builder == null) {
			builder = InjectorFactory.getInjector().getInstance(IMapBuilder.class);
		}
		builder.parse(roots);
		persister.persist(builder);
	}

	/**
	 * Get an ordered list of projects that depend on the given project.
	 * @param projectDescriptor requested project to get dependencies for.
	 * @return dependency chain.
	 */
	public List<IProjectDescriptor> getProjectsThatDepeandOnProject(
			IProjectDescriptor projectDescriptor) {
		initIfRequired();
		loadBuilder();
		IProjectDependencyMapper mapper = builder.getProjectMapper();
		return mapper.getProjectsThatDepeantOn(projectDescriptor);
	}
	
	/**
	 * Get an ordered list of projects that depend on the given projects.
	 * @param projectDescriptors requested projects to get dependencies for.
	 * @return dependency chain
	 */
	public List<IProjectDescriptor> getProjectsThatDepeandOnProjects(
			List<IProjectDescriptor> projectDescriptors) {
		initIfRequired();
		loadBuilder();
		IProjectDependencyMapper mapper = builder.getProjectMapper();
		return mapper.getProjectsThatDepeantOn(projectDescriptors);
	}

	/**
	 * Get solutions that depend on a specific project.
	 * @param projectDescriptor project to get dependecnies for
	 * @return dependecny chain
	 */
	public List<ISolution> getSolutionsThatDependOnProject(
			IProjectDescriptor projectDescriptor) {
		initIfRequired();
		loadBuilder();
		ISolutionMapper solutionMapper = builder.getSolutionMapper();
		List<IProjectDescriptor> descriptors = new ArrayList<IProjectDescriptor>();
		descriptors.add(projectDescriptor);
		return solutionMapper.getSolutionsByProjects(descriptors);
	}

	/**
	 * Get solutions that depend on solutions
	 * @param names solutions to get dependencies for
	 * @return
	 */
	public List<ISolution> getSolutionsThatDependOnSolutionsByNames(
			List<String> names) {
		initIfRequired();
		loadBuilder();
		ISolutionMapper solutionMapper = builder.getSolutionMapper();
		return solutionMapper.getSolutionsBySolutionsNames(names);
	}

	private void init() {

		initConfig();
		roots = ConfigUtils.readList("rootPath", new String[] { "c:\\temp" },
				null);
		StringBuilder builder = new StringBuilder("Root directories to scan are:");
		for(String root: roots) {
			builder.append(root).append(";");
		}
		logger.info(builder);
		initPersister();	
		isInitialized = true;
	}

	private void initPersister() {
		persister  = InjectorFactory.getInjector().getInstance(IStatePersist.class);		
		shoulUsePersistedState = ConfigUtils.readBoolean("shoulUsePersistedState" , true);
	}

	private void initConfig() {
		
		if (configPath == null || configPath.isEmpty()) {
			logger.info("no config path passed in. use default");
			configPath = Runner.class.getResource("/config.xml").toString();
		}
		
		else
		{
			logger.info("Env. config path is:" + configPath);
		}

		logger.info("Setting config path to be:" + configPath);
		ConfigUtils.init(configPath);
	}	

	private void loadBuilder() {
		if(shoulUsePersistedState) {
		builder = persister.load();
		}
		if (builder == null) {
			builder = InjectorFactory.getInjector().getInstance(IMapBuilder.class);
			builder.parse(roots);
		}
	}

	private void initIfRequired() {
		if (!isInitialized) {
			init();
		}
	}

}

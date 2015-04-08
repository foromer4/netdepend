package picscout.depend.dependency.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import picscout.depend.dependency.classes.MapBuilder;
import picscout.depend.dependency.classes.ProjectDependencyMapper;
import picscout.depend.dependency.classes.StatePersist;
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

	public void CalculateDependencies() {
		logger.info("Starting calculation of dependencies");
		initIfRequired();
		initBuilder();
		persister.persist(builder);
	}

	public List<IProjectDescriptor> getProjectsThatDepeantOnProject(
			IProjectDescriptor projectDescriptor) {
		initIfRequired();
		IProjectDependencyMapper mapper = builder.getProjectMapper();
		return mapper.getProjectsThatDepeantOn(projectDescriptor);
	}

	public List<ISolution> getSolutionsThatDependOnProject(
			IProjectDescriptor projectDescriptor) {
		initIfRequired();
		ISolutionMapper solutionMapper = builder.getSolutionMapper();
		List<IProjectDescriptor> descriptors = new ArrayList<IProjectDescriptor>();
		descriptors.add(projectDescriptor);
		return solutionMapper.getSolutionsByProjects(descriptors);
	}

	public List<ISolution> getSolutionsThatDependOnSolutionsByNames(List<String> names) {
		initIfRequired();
		ISolutionMapper solutionMapper = builder.getSolutionMapper();
		return solutionMapper.getSolutionsBySolutionsNames(names);
	}

	private void init() {
		String configPath = Runner.class.getResource("/config.xml").toString();
		logger.info("Setting config path to be:" + configPath);
		ConfigUtils.init(configPath);
		persister = new StatePersist();
		isInitialized = true;
	}

	private void initBuilder() {
		String root = ConfigUtils.readString("rootPath", "c:\\temp");

		logger.info("Root directory to scan is:" + root);

		builder = new MapBuilder(root);

		builder.parse();
	}

	private void initIfRequired() {
		if (!isInitialized) {
			init();
		}
		builder = persister.load();
		if (builder == null) {
			initBuilder();
		}
	}

}

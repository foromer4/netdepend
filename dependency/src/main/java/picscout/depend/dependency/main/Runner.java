package picscout.depend.dependency.main;

import java.util.ArrayList;
import java.util.List;

import picscout.depend.dependency.classes.MapBuilder;
import picscout.depend.dependency.classes.StatePersist;
import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.interfaces.ISolutionMapper;
import picscout.depend.dependency.interfaces.IStatePersist;
import picscout.depend.dependency.utils.ConfigUtils;

public class Runner {

	private IMapBuilder builder;
	private IStatePersist persister;
	private Boolean isInitialized = false;

	public void CalculateDependencies() {
		initIfRequired();
		initBuilder();
		persister.persist(builder);
	}

	public List<IProjectDescriptor> getProjectsThatDepeantOn(
			IProjectDescriptor projectDescriptor) {
		initIfRequired();
		loadOrInitBuilder();
		IProjectDependencyMapper mapper = builder.getProjectMapper();
		return mapper.getProjectsThatDepeantOn(projectDescriptor);
	}

	public List<ISolution> getSolutionsByProject(
			IProjectDescriptor projectDescriptor) {
		initIfRequired();
		loadOrInitBuilder();
		ISolutionMapper solutionMapper = builder.getSolutionMapper();
		List<IProjectDescriptor> descriptors = new ArrayList<IProjectDescriptor>();
		descriptors.add(projectDescriptor);

		return solutionMapper.getSolutionsByProjects(descriptors);
	}

	private void init() {
		String configPath = Runner.class.getResource("/config.xml").toString();
		ConfigUtils.init(configPath);
		persister = new StatePersist();
		isInitialized = true;
	}

	private void initBuilder() {
		String root = ConfigUtils.readString("rootPath", "c:\\temp");
		// TODO- use injections
		builder = new MapBuilder(root);

		builder.parse();
	}

	private void initIfRequired() {
		if (!isInitialized) {
			init();
		}
	}

	private void loadOrInitBuilder() {
		builder = persister.load();
		if (builder == null) {
			initBuilder();
		}
	}

}

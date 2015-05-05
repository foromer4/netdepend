package picscout.depend.dependency.classes;

import java.io.File;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectStore;
import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.interfaces.ISolutionMapper;
import picscout.depend.dependency.utils.ConfigUtils;
import picscout.depend.dependency.utils.FileUtilsHelper;

/**
 * Parse files into mappers for projects/solutions
 * 
 * @author OSchliefer
 *
 */
public class MapBuilder implements IMapBuilder {

	/* (non-Javadoc)
	 * @see picscout.depend.dependency.classes.IMapBuilder#getProjectStore()
	 */
	public IProjectStore getProjectStore() {
		return projectStore;
	}	

	/* (non-Javadoc)
	 * @see picscout.depend.dependency.classes.IMapBuilder#getProjectMapper()
	 */
	public IProjectDependencyMapper getProjectMapper() {
		return projectMapper;
	}
	
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.classes.IMapBuilder#getSolutionMapper()
	 */
	public ISolutionMapper getSolutionMapper() {
		return solutionMapper;
	}


	private String[] rootPaths;
	private final static String csProjExtenstion = "csproj";
	private final static String solutionExtenstion = "sln";
	private static final Logger logger = LogManager.getLogger(MapBuilder.class
			.getName());
	private IProjectStore projectStore;
	private IProjectDependencyMapper projectMapper;
	private ISolutionMapper solutionMapper;

	public MapBuilder(String[] rootPaths) {
		this.rootPaths = rootPaths;

		// TODO - factories for store
		projectStore = new ProjectStore();
		projectMapper = new ProjectDependencyMapper(projectStore);
		solutionMapper = new SolutionMapper();
		solutionMapper.init(projectMapper);
		
		
	}

	/* (non-Javadoc)
	 * @see picscout.depend.dependency.classes.IMapBuilder#parse()
	 */
	public void parse() {
		parseProjects();
		parseSolutions();		
	}

	

	private void parseProjects() {
		
		logger.info("Parsing projects");
		String[] projectExtensions = { csProjExtenstion };
		Collection<File> projectsFiles = FileUtilsHelper.listFiles(rootPaths,
				projectExtensions);
		for (File file : projectsFiles) {
			try {
				// TODO - factory for project
				IProject project = new Project(file.getCanonicalPath()
						.toString());
				project.parse();
				projectStore.addProject(project);

			} catch (Exception e) {
				logger.warn("problem parsing file: " + file.toString(), e);
			}
		}

		projectMapper.init();

	}

	private void parseSolutions() {
		logger.info("Parsing solutions");
		String[] solutionExtensions = { solutionExtenstion };
		Collection<File> solutionFiles = FileUtilsHelper.listFiles(rootPaths,
				solutionExtensions);
		for (File file : solutionFiles) {
			try {
				// TODO - factory for solution
				ISolution solution = new Solution(file.getCanonicalPath()
						.toString());
				solution.parse();
				solutionMapper.add(solution);

			} catch (Exception e) {
				logger.warn("problem parsing file: " + file.toString(), e);
			}
		}

	}

}

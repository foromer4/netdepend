package picscout.depend.dependency.classes;

import java.io.File;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectBuilder;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectStore;
import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.interfaces.ISolutionBuilder;
import picscout.depend.dependency.interfaces.ISolutionMapper;
import picscout.depend.dependency.utils.FileUtilsHelper;
import picscout.depend.dependency.utils.InjectorFactory;

import javax.inject.Singleton;
import javax.inject.Inject;

/**
 * Parse files into mappers for projects/solutions
 * 
 * @author OSchliefer
 *
 */
@Singleton
public class MapBuilder implements IMapBuilder {
	private String[] rootPaths;
	private final static String csProjExtenstion = "csproj";
	private final static String solutionExtenstion = "sln";
	private static final Logger logger = LogManager.getLogger(MapBuilder.class
			.getName());
	private IProjectStore projectStore;
	private IProjectDependencyMapper projectMapper; //TODO -delete
	private ISolutionMapper solutionMapper;
	private IProjectBuilder projectBuilder;
	private ISolutionBuilder solutionBuilder;
	
	
	public MapBuilder() {
		initMembers();
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


	/* (non-Javadoc)
	 * @see picscout.depend.dependency.classes.IMapBuilder#parse()
	 */
	public void parse(String[] rootPaths) {
		this.rootPaths = rootPaths;	
		parseProjects();
		parseSolutions();	
		
		
	}

	

	private void initMembers() {
		projectBuilder = InjectorFactory.getInjector().getInstance(IProjectBuilder.class);
		solutionBuilder = InjectorFactory.getInjector().getInstance(ISolutionBuilder.class);
		projectStore = InjectorFactory.getInjector().getInstance(IProjectStore.class);
		projectMapper = InjectorFactory.getInjector().getInstance(IProjectDependencyMapper.class);
		solutionMapper = InjectorFactory.getInjector().getInstance(ISolutionMapper.class);
	
	}


	private void parseProjects() {
		
		logger.info("Parsing projects");
		String[] projectExtensions = { csProjExtenstion };
		Collection<File> projectsFiles = FileUtilsHelper.listFiles(rootPaths,
				projectExtensions);
		for (File file : projectsFiles) {
			try {				
				IProject project =  projectBuilder.build(file.getCanonicalPath()
						.toString());
				project.parse();
				projectStore.addProject(project);

			} catch (Exception e) {
				logger.warn("problem parsing file: " + file.toString(), e);
			}
		}
	}

	private void parseSolutions() {
		logger.info("Parsing solutions");
		String[] solutionExtensions = { solutionExtenstion };
		Collection<File> solutionFiles = FileUtilsHelper.listFiles(rootPaths,
				solutionExtensions);
		for (File file : solutionFiles) {
			try {				
				ISolution solution = solutionBuilder.build(file.getCanonicalPath()
						.toString());
				solution.parse();
				solutionMapper.add(solution);

			} catch (Exception e) {
				logger.warn("problem parsing file: " + file.toString(), e);
			}
		}

	}

}

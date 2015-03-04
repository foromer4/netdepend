package picscout.depend.dependency.classes;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;

import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectStore;
import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.utils.FileUtilsHelper;

/**
 * Parse files into mappers for projects/solutions
 * @author OSchliefer
 *
 */
public class MapBuilder {
	

	
	private String rootPath;
	private final static String  csProjExtenstion = "csproj";
	private final static String  solutionExtenstion = "sln"; 
    private static final Logger logger = LogManager.getLogger(MapBuilder.class.getName());
    private IProjectStore projectStore;
    private IProjectDependencyMapper projectMapper;
    private ISolutionMapper solutionMapper;
    
	public MapBuilder(String rootPath) {
		this.rootPath = rootPath;
		
		//TODO - factories for store
		projectStore = new ProjectStore();
		projectMapper = new ProjectDependencyMapper(projectStore);
		solutionMapper = new SolutionMapper();
		solutionMapper.init(projectMapper);
	}
	
	
	public void parse() {
		parseProjects();
		parseSolutions();
	}
	
	private void parseProjects() {
		String[] projectExtensions = {csProjExtenstion};
		Collection<File> projectsFiles = FileUtilsHelper.listFiles(rootPath, projectExtensions);
		for(File file : projectsFiles) {
			try {
				//TODO - factory for project
				IProject project = new Project(file.getCanonicalPath().toString());
				project.parse();
				projectStore.addProject(project);
				
			} catch (Exception e) {
				logger.warn("problem parsing file: " + file.toString(), e);
			}
		}
		
		projectMapper.init();
		
	}
	
	private void parseSolutions() {
		String[] solutionExtensions = {solutionExtenstion};
		Collection<File> solutionFiles = FileUtilsHelper.listFiles(rootPath, solutionExtensions);
		for(File file : solutionFiles) {
			try {
				//TODO - factory for solution
				ISolution solution = new Solution(file.getCanonicalPath().toString());
				solution.parse();
				solutionMapper.add(solution);
				
			} catch (Exception e) {
				logger.warn("problem parsing file: " + file.toString(), e);
			}
		}
		
		
		
	}
	
	
	

}

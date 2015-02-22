package picscout.depend.dependency.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectStore;

/**
 * Maps the dependendcies between project.
 * @author OSchliefer
 *
 */
public class ProjectDependencyMapper {
	
	private IProjectStore store;
	private HashSet<String> alreadyParsedProjectsGuids;
	/**
	 * For each project (guid), hold a set of all projects (guids) that depend on that 
	 * project.
	 */
	private Map<String, HashSet<String>> projectReveresedDependencies;
	public ProjectDependencyMapper(IProjectStore store) {
		this.store = store;
		projectReveresedDependencies = new HashMap<String,  HashSet<String>>();
		alreadyParsedProjectsGuids = new HashSet<String>();
	}
	
	
	public Map<String, HashSet<String>>  getMap() {
		if(projectReveresedDependencies == null) {
			createMap();
		}
		return projectReveresedDependencies;
	}


	private void createMap() {
		for(IProject project : store.getAllMappedProjects()) {	
			alreadyParsedProjectsGuids.clear();
			parseProject(project);
		}		
	}


	private void parseProject(IProject project) {
		if(alreadyParsedProjectsGuids.contains(project.getDescriptor().getGuid())) {
			return;
		}
		IProject projectDependencyParent;
		for(String guid: project.getDependenciesGuids()) {
			projectDependencyParent = store.getProjectByGuid(guid);
			if(projectDependencyParent != null) {
				saveDependency(projectDependencyParent, project);
				parseProject(projectDependencyParent);
			}
		}
		
		for(String assemblyName: project.getDepnedenciesAssembliesNames()) {
			projectDependencyParent = store.getProjectByAssemblyName(assemblyName);
			if(projectDependencyParent != null) {
				saveDependency(projectDependencyParent, project);
				parseProject(projectDependencyParent);
				
			}
		}
		alreadyParsedProjectsGuids.add(project.getDescriptor().getGuid());
	}
	
	private void saveDependency(IProject parentProject, IProject dependentProject) {
		String parentGuid = parentProject.getDescriptor().getGuid();
		if(!projectReveresedDependencies.containsKey(parentGuid)){
			projectReveresedDependencies.put(parentGuid, new HashSet<String>());
		}
		String dependentGuid = dependentProject.getDescriptor().getGuid();
		projectReveresedDependencies.get(parentGuid).add(dependentGuid);
	}

}

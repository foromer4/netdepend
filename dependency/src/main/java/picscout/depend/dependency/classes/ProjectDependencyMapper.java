package picscout.depend.dependency.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
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
	private Map<IProjectDescriptor, HashSet<IProjectDescriptor>> projectReveresedDependencies;
	public ProjectDependencyMapper(IProjectStore store) {
		this.store = store;
		
		alreadyParsedProjectsGuids = new HashSet<String>();
	}
	
	/**
	 * Initialise the dependency map, calculate the map
	 */
	public void init()
	{
		createMap();		
	}
	
	/**
	 * Get hashset containing all projects that depend on the given project.
	 * @param projectDesceipror descriptor 
	 * @return hashet if found, null otherwise.
	 */
	public HashSet<IProjectDescriptor> getProjectsThatDepeantOn(IProjectDescriptor projectDesceipror)
	{
		return getMap().get(projectDesceipror);
	}
	
	/**
	 * Get map off project as key, projects that depend on this project as value.
	 * @return entire map based on the given store.
	 */
	public Map<IProjectDescriptor, HashSet<IProjectDescriptor>>  getMap() {
		if(projectReveresedDependencies == null) {
			init();
		}
		return projectReveresedDependencies;
	}


	private void createMap() {
		projectReveresedDependencies = new HashMap<IProjectDescriptor,  HashSet<IProjectDescriptor>>();
		for(IProject project : store.getAllMappedProjects()) {	
			alreadyParsedProjectsGuids.clear();
			parseProject(project);
		}		
	}


	private void parseProject(IProject project) {
		if(alreadyParsedProjectsGuids.contains(project.getDescriptor().getGuid())) {
			return;
		}
		alreadyParsedProjectsGuids.add(project.getDescriptor().getGuid());
		
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
	}
	
	private void saveDependency(IProject parentProject, IProject dependentProject) {
		IProjectDescriptor parentDescriptor = parentProject.getDescriptor();
		if(!projectReveresedDependencies.containsKey(parentDescriptor)){
			projectReveresedDependencies.put(parentDescriptor, new HashSet<IProjectDescriptor>());
		}
		IProjectDescriptor dependentDescriptor = dependentProject.getDescriptor();
		projectReveresedDependencies.get(parentDescriptor).add(dependentDescriptor);
	}

}

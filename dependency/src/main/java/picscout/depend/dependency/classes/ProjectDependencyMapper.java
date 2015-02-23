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
	private HashSet<IProjectDescriptor> alreadyParsedProjectsGuids;
	/**
	 * For each project (guid), hold a set of all projects (guids) that depend on that 
	 * project.
	 */
	private Map<IProjectDescriptor, HashSet<IProjectDescriptor>> projectReveresedDependencies;
	public ProjectDependencyMapper(IProjectStore store) {
		this.store = store;
		projectReveresedDependencies = new HashMap<IProjectDescriptor,  HashSet<IProjectDescriptor>>();
		alreadyParsedProjectsGuids = new HashSet<IProjectDescriptor>();
	}
	
	
	public Map<IProjectDescriptor, HashSet<IProjectDescriptor>>  getMap() {
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
		if(alreadyParsedProjectsGuids.contains(project.getDescriptor())) {
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
		alreadyParsedProjectsGuids.add(project.getDescriptor());
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

package picscout.depend.dependency.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
import picscout.depend.dependency.interfaces.IProjectStore;

/**
 * Maps the dependendcies between project.
 * 
 * @author OSchliefer
 *
 */
public class ProjectDependencyMapper {

	private IProjectStore store;
	private HashSet<String> alreadyParsedProjectsGuids;
	/**
	 * For each project (guid), hold a set of all projects (guids) that depend
	 * on that project.
	 */
	private Map<IProjectDescriptor, List<IProjectDescriptor>> projectReveresedDependencies;

	public ProjectDependencyMapper(IProjectStore store) {
		this.store = store;

		alreadyParsedProjectsGuids = new HashSet<String>();
	}

	/**
	 * Initialise the dependency map, calculate the map
	 */
	public void init() {
		createMap();
	}

	/**
	 * Get arraylist containing all projects that depend on the given project.
	 * 
	 * @param projectDesceipror
	 *            descriptor
	 * @return list if found, null otherwise. order is by closest project to most far ,
	 * for each dependency chain. chains can be one after another.
	 */
	public List<IProjectDescriptor> getProjectsThatDepeantOn(
			IProjectDescriptor projectDesceipror) {
		return getMap().get(projectDesceipror);
	}

	/**
	 * Get map off project as key, projects that depend on this project as
	 * value.
	 * 
	 * @return entire map based on the given store.
	 */
	public Map<IProjectDescriptor, List<IProjectDescriptor>> getMap() {
		if (projectReveresedDependencies == null) {
			init();
		}
		return projectReveresedDependencies;
	}

	private void createMap() {
		projectReveresedDependencies = new HashMap<IProjectDescriptor, List<IProjectDescriptor>>();
		for (IProject project : store.getAllMappedProjects()) {
			alreadyParsedProjectsGuids.clear();
			ArrayList<IProjectDescriptor> chain = new ArrayList<IProjectDescriptor>();			
			parseProject(project, chain);
		}
	}	

	private void parseProject(IProject project,ArrayList<IProjectDescriptor> chain) {
		if (alreadyParsedProjectsGuids.contains(project.getDescriptor()
				.getGuid())) {
			return;
		}
		chain.add(project.getDescriptor());
		alreadyParsedProjectsGuids.add(project.getDescriptor().getGuid());

		IProject projectDependencyParent;
		for (String guid : project.getDependenciesGuids()) {
			projectDependencyParent = store.getProjectByGuid(guid);
			if (projectDependencyParent != null) {
				saveDependency(projectDependencyParent, chain);
				parseProject(projectDependencyParent, chain);
			}
		}

		for (String assemblyName : project.getDepnedenciesAssembliesNames()) {
			projectDependencyParent = store
					.getProjectByAssemblyName(assemblyName);
			if (projectDependencyParent != null) {
				saveDependency(projectDependencyParent, chain);
				parseProject(projectDependencyParent, chain);
			}
		}
		chain.remove(chain.size() - 1);// when done parsing remove this project from the chain
	}

	private void saveDependency(IProject parentProject,
			ArrayList<IProjectDescriptor> chain) {
		IProjectDescriptor parentDescriptor = parentProject.getDescriptor();
		if (!projectReveresedDependencies.containsKey(parentDescriptor)) {
			projectReveresedDependencies.put(parentDescriptor,
					new ArrayList<IProjectDescriptor>());
		}
		List<IProjectDescriptor> bucket = projectReveresedDependencies
				.get(parentDescriptor);
		for(int i = chain.size() - 1; i >= 0; i--){ //Reverse dependency chani
			IProjectDescriptor dependentDescriptor = chain.get(i);
			if(!bucket.contains(dependentDescriptor) && !dependentDescriptor.equals(parentDescriptor)){				
		bucket.add(dependentDescriptor);		
			}
		}
	}
}

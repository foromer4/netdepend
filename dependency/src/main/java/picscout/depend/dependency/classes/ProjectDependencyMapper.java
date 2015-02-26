package picscout.depend.dependency.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
import picscout.depend.dependency.interfaces.IProjectStore;

/**
 * Maps the dependencies between projects. For each project we can get a list of
 * project that depend on it by the correct order.(closest first). Note that
 * each project can have more than one chain, in that case chains are one after
 * another.
 * 
 * @author OSchliefer
 *
 */
public class ProjectDependencyMapper implements IProjectDependencyMapper {

	private IProjectStore store;
	private HashSet<String> alreadyParsedProjectsGuids;
	private static final Logger logger = LogManager.getLogger(ProjectDependencyMapper.class.getName());
	/**
	 * For each project (guid), hold a set of all projects (guids) that depend
	 * on that project.
	 */
	private Map<IProjectDescriptor, List<IProjectDescriptor>> projectReveresedDependencies;

	public ProjectDependencyMapper(IProjectStore store) {
		this.store = store;

		alreadyParsedProjectsGuids = new HashSet<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see picscout.depend.dependency.classes.IProjectDependencyMapper#init()
	 */
	public void init() {
		createMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see picscout.depend.dependency.classes.IProjectDependencyMapper#
	 * getProjectsThatDepeantOn
	 * (picscout.depend.dependency.interfaces.IProjectDescriptor)
	 */
	public List<IProjectDescriptor> getProjectsThatDepeantOn(
			IProjectDescriptor projectDesceipror) {
		if (projectReveresedDependencies == null) {
			init();
		}
		List<IProjectDescriptor> result = projectReveresedDependencies
				.get(projectDesceipror);
		if (result == null) {
			return null;
		}
		return new ArrayList<IProjectDescriptor>(
				projectReveresedDependencies.get(projectDesceipror));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see picscout.depend.dependency.classes.IProjectDependencyMapper#getMap()
	 */
	public Map<IProjectDescriptor, List<IProjectDescriptor>> getMap() {
		if (projectReveresedDependencies == null) {
			init();
		}
		HashMap<IProjectDescriptor, List<IProjectDescriptor>> result = new HashMap<IProjectDescriptor, List<IProjectDescriptor>>();
		for (IProjectDescriptor key : projectReveresedDependencies.keySet()) {
			result.put(key, getProjectsThatDepeantOn(key));
		}
		return result;
	}

	private void createMap() {
		projectReveresedDependencies = new HashMap<IProjectDescriptor, List<IProjectDescriptor>>();
		for (IProject project : store.getAllMappedProjects()) {
			alreadyParsedProjectsGuids.clear();
			ArrayList<IProjectDescriptor> chain = new ArrayList<IProjectDescriptor>();
			parseProject(project, chain);
		}
	}

	private void parseProject(IProject project,
			ArrayList<IProjectDescriptor> chain) {
		try{
		if (alreadyParsedProjectsGuids.contains(project.getDescriptor()
				.getGuid())) {
			return;
		}
		chain.add(project.getDescriptor());
		alreadyParsedProjectsGuids.add(project.getDescriptor().getGuid());
		
		for (String guid : project.getDependenciesGuids()) {
			parseProjectDependency(chain, guid);
		}

		for (String assemblyName : project.getDepnedenciesAssembliesNames()) {
			parseAssemblyDependency(chain, assemblyName);
		}
		chain.remove(chain.size() - 1);// when done parsing remove this project
										// from the chain
		}
		catch (Exception ex) {
			logger.warn("Problem parsing file dependency" , ex);
		}
	}

	private void parseProjectDependency(ArrayList<IProjectDescriptor> chain,
			String guid) {
		IProject projectDependencyParent;
		projectDependencyParent = store.getProjectByGuid(guid);
		if (projectDependencyParent != null) {
			saveDependency(projectDependencyParent, chain);
			parseProject(projectDependencyParent, chain);
		}
	}

	private void parseAssemblyDependency(ArrayList<IProjectDescriptor> chain,
			String assemblyName) {
		IProject projectDependencyParent;
		projectDependencyParent = store
				.getProjectByAssemblyName(assemblyName);
		if (projectDependencyParent != null) {
			saveDependency(projectDependencyParent, chain);
			parseProject(projectDependencyParent, chain);
		}
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
		for (int i = chain.size() - 1; i >= 0; i--) { // Reverse dependency
														// chain
			IProjectDescriptor dependentDescriptor = chain.get(i);
			if (!bucket.contains(dependentDescriptor)
					&& !dependentDescriptor.equals(parentDescriptor)) {
				bucket.add(dependentDescriptor);
			}
		}
	}
}

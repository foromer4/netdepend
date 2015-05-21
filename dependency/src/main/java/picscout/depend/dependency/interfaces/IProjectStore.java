package picscout.depend.dependency.interfaces;

import java.util.Collection;

/**
 * Holds all projects mapped to guid and assembly name
 * @author OSchliefer
 *
 */
public interface IProjectStore {

	/**
	 * Add project to the repository
	 * @param project project to add
	 */
	public abstract void addProject(IProject project);

	/**
	 * Get project mapped to guid
	 * @param projectGuid guid 
	 * @return project if found, null otherwise
	 */
	public abstract IProject getProjectByGuid(String projectGuid);

	/**
	 * Get project mapped to assembly name
	 * @param  assembly name
	 * @return projectGuid project if found, null otherwise
	 */
	public abstract IProject getProjectByAssemblyName(String assemblyName);
	
	/**
	 * Get all projects in this store
	 * @return all projects
	 */
	public abstract Collection<IProject> getAllMappedProjects();

}
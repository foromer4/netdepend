package picscout.depend.dependency.interfaces;

import java.util.List;
import java.util.Map;

public interface IProjectDependencyMapper {

	/**
	 * Initialise the dependency map, calculate the map
	 */
	public abstract void init();

	/**
	 * Get list containing all projects that depend on the given project.
	 * 
	 * @param projectDesceipror
	 *            descriptor
	 * @return list if found, null otherwise. order is by closest project to
	 *         most far , for each dependency chain. chains can be one after
	 *         another.
	 */
	public abstract List<IProjectDescriptor> getProjectsThatDepeantOn(
			IProjectDescriptor projectDescriptor);
	
	
	/**
	 * Get list containing all projects that depend on the given projects.
	 * 
	 * @param projectDesceipror
	 *            descriptor
	 * @return list if found, null otherwise. order is by closest project to
	 *         most far , for each dependency chain. chains can be one after
	 *         another.
	 */
	public abstract List<IProjectDescriptor> getProjectsThatDepeantOn(
			List<IProjectDescriptor> projectDescriptors);	

	/**
	 * Get map off project as key, projects that depend on this project as
	 * value.
	 * 
	 * @return entire map based on the given store.
	 */
	public abstract Map<IProjectDescriptor, List<IProjectDescriptor>> getMap();

}
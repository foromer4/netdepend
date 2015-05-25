package picscout.depend.dependency.interfaces;

import java.util.List;

/**
 * Map dependencies between solutions
 * @author OSchliefer
 *
 */
public interface ISolutionMapper {
	/**
	 * Add a solution to the mapper
	 * @param solution solution to add
	 */
	public abstract void add(ISolution solution);
	
	/**
	 * Get all solutions in the mapper that depend on given projects.
	 * @param projectDescriptors projects 
	 * @return all dependent solutions.
	 */
	public abstract List<ISolution> getSolutionsByProjects(List<IProjectDescriptor> projectDescriptors);
	
	/**
	 * Get all solutions in the mapper that depend on given solutions
	 * @param names solution names
	 * @return dependent solutions
	 */
	public abstract List<ISolution> getSolutionsBySolutionsNames(List<String> names);
}
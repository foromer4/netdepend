package picscout.depend.dependency.interfaces;

/**
 * Builds a solution.
 * @author OSchliefer
 *
 */
public interface ISolutionBuilder {

	/**
	 * Build a solution  by a given .sln file
	 * @param fullPath path to sln file including file name and extension.
	 * @return solution
	 */
	public ISolution build(String fullPath);
}

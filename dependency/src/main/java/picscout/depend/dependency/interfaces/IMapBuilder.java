package picscout.depend.dependency.interfaces;


/**
 * Builder of dependency maps.
 * @author OSchliefer
 *
 */
public interface IMapBuilder {
	
	/**
	 * Mapper for projects dependencies
	 * @return projects mapper
	 */
	public abstract IProjectDependencyMapper getProjectMapper();

	/**
	 * Mapper for solution dependencies.
	 * @return solutions mapper
	 */
	public abstract ISolutionMapper getSolutionMapper();

	/**
	 * Create dependency chain, based on given root paths to read projects/solutions in. read is recursive.
	 * @param rootPaths
	 */
	public abstract void parse(String[] rootPaths);

}
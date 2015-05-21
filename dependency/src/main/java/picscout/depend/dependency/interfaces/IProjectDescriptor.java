package picscout.depend.dependency.interfaces;

/**
 * Descriptor for a project
 * @author OSchliefer
 *
 */
public interface IProjectDescriptor {

	/**
	 * Get simple name of project
	 * @return name including extension
	 */
	public abstract String getName();

	/**
	 * Get guid of project
	 * @return
	 */
	public abstract String getGuid();

	/**
	 * Full path of project
	 * @return
	 */
	public abstract String getFullPath();

	/**
	 * Assembly name of project
	 * @return
	 */
	public abstract String getAssemblyName();

}
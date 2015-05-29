package com.picscout.depend.dependency.interfaces;

/**
 * Descriptor for a project
 * 
 * @author OSchliefer
 *
 */
public interface IProjectDescriptor {

	/**
	 * Get simple name of project
	 * 
	 * @return name including extension
	 */
	public abstract String getName();

	/**
	 * Get guid of project
	 * 
	 * @return guid
	 */
	public abstract String getGuid();

	/**
	 * Full path of project
	 * 
	 * @return full path
	 */
	public abstract String getFullPath();

	/**
	 * Assembly name of project
	 * 
	 * @return assemblyt name
	 */
	public abstract String getAssemblyName();

}
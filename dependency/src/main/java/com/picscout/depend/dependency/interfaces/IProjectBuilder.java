package com.picscout.depend.dependency.interfaces;

/**
 * Construct a project from a path
 * @author OSchliefer
 *
 */
public interface IProjectBuilder {
	/**
	 * Contuct a new project
	 * @param fullPath path for csproj
	 * @return project
	 */
	public IProject build(String fullPath);
}

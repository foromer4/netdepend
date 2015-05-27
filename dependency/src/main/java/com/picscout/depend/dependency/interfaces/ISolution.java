package com.picscout.depend.dependency.interfaces;

import java.util.List;

/**
 * Solution as defined in .sln file
 * @author OSchliefer
 *
 */
public interface ISolution {

	/**
	 * Full path for the .sln file including name
	 * @return full path
	 */
	public abstract String getFullPath();

	/**
	 * Path to file not including file name
	 * @return path
	 */
	public abstract String getPath();

	/**
	 * Simple name including extension
	 * @return name
	 */
	public abstract String getName();

	/**
	 * All project descriptors in this soltion's .sln file
	 * @return descriptors
	 */
	public abstract List<IProjectDescriptor> getProjectsDescriptors();

	/**
	 * Parse .sln file
	 */
	public abstract void parse();

}
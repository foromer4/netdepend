package com.picscout.depend.dependency.interfaces;

import java.util.List;

/**
 * Project has a descriptor, and ability to calculate dependent projects based on its csproj file.
 * @author OSchliefer
 *
 */
public interface IProject {
	
	/**
	 * Get the project descriptor.
	 * @return descriptor
	 */
	public IProjectDescriptor getDescriptor();	

	/**
	 * Get assemblies that this project depends on.
	 * @return assemblies
	 */
	public List<String> getDepnedenciesAssembliesNames();
	
	/**
	 * Get guids of other prjects this project depends on (within solution)
	 * @return guids of projects
	 */
	public List<String> getDependenciesGuids();		

	/**
	 * Parse the csproj file of this project
	 */
	public void parse();

}
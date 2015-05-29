package com.picscout.depend.dependency.classes;

import javax.inject.Singleton;
import com.picscout.depend.dependency.interfaces.IProject;
import com.picscout.depend.dependency.interfaces.IProjectBuilder;

/**
 * @see IProjectBuilder
 * @author OSchliefer
 *
 */
@Singleton
public class ProjectBuilder implements IProjectBuilder {

	public IProject build(String fullPath) {
		return new Project(fullPath);
	}

}

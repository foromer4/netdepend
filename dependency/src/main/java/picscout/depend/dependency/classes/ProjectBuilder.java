package picscout.depend.dependency.classes;

import javax.inject.Singleton;
import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectBuilder;

@Singleton
public class ProjectBuilder implements IProjectBuilder{

	public IProject build(String fullPath) {
		return new Project(fullPath);
	}

}

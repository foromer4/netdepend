package picscout.depend.dependency;

public class ProjectBuilder {
	
	public IProject create(String fullPath)
	{
		return new Project(fullPath);
	}
}

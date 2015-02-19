package picscout.depend.dependency.interfaces;

import java.util.Collection;
import java.util.List;

public interface IProjectStore {

	public abstract void addProject(IProject project);

	public abstract IProject getProjectByGuid(String projectGuid);

	public abstract IProject getProjectByAssemblyName(String assemblyName);
	
	public abstract Collection<IProject> getAllMappedProjects();

}
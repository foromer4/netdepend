package com.picscout.depend.dependency.classes;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.picscout.depend.dependency.interfaces.IProject;
import com.picscout.depend.dependency.interfaces.IProjectStore;
import javax.inject.Singleton;


/**
 * Save projects mapped by guid and also mapped by assembly name,
 * enable to query for projects by those keys.
 * @see IProjectStore
 * @author OSchliefer
 *
 */
@Singleton
public class ProjectStore implements IProjectStore {
	
	private Map<String, IProject> projectByGuid = new HashMap<String,IProject>(); 
	private Map<String, IProject> projectByAssembly = new HashMap<String,IProject>();
	
	/* (non-Javadoc)
	 * @see com.picscout.depend.dependency.IProjectStore#addProject(com.picscout.depend.dependency.IProject)
	 */
	public void addProject(IProject project)	{
		projectByGuid.put(project.getDescriptor().getGuid(), project);
		projectByAssembly.put(project.getDescriptor().getAssemblyName(), project);			
	}
	
	/* (non-Javadoc)
	 * @see com.picscout.depend.dependency.IProjectStore#getProjectByGuid(java.lang.String)
	 */
	public IProject getProjectByGuid(String projectGuid)	{
		return projectByGuid.get(projectGuid);
	}
	
	/* (non-Javadoc)
	 * @see com.picscout.depend.dependency.IProjectStore#getProjectByAssemblyName(java.lang.String)
	 */
	public IProject getProjectByAssemblyName(String assemblyName)	{
		return projectByAssembly.get(assemblyName);
	}
	
	public Collection<IProject> getAllMappedProjects() {
		return projectByGuid.values();
	}
	
	
	

}

package picscout.depend.dependency;

import java.util.HashMap;
import java.util.Map;

/**
 * Save projects mapped by guid and also mapped by assembly name,
 * enable to query for projects by those keys.
 * @author OSchliefer
 *
 */
public class ProjectStore {
	
	private Map<String, IProject> projectByGuid = new HashMap<String,IProject>(); 
	private Map<String, IProject> projectByAssembly = new HashMap<String,IProject>();
	
	public void addProject(IProject project)	{
		projectByGuid.put(project.getDescriptor().getGuid(), project);
		projectByAssembly.put(project.getDescriptor().getAssemblyName(), project);		
	}
	
	public IProject getProjectByGuid(String projectGuid)	{
		return projectByGuid.get(projectGuid);
	}
	
	public IProject getProjectByAssemblyName(String assemblyName)	{
		return projectByAssembly.get(assemblyName);
	}
	
	

}

package picscout.depend.dependency;

import java.util.List;

public interface IProject {
	
	public String getFullPath();

	public String getPath();
	
	public String getFileName();

	public String getAssemblyName();

	public List<String> getDepnedenciesAssembliesNames();
	
	public List<String> getDependenciesGuids();	
	
	public String getGuid();

	public void parse();

}
package picscout.depend.dependency;

import java.util.List;

public interface IProject {
	
	public ProjectDescriptor getDescriptor();	

	public List<String> getDepnedenciesAssembliesNames();
	
	public List<String> getDependenciesGuids();		

	public void parse();

}
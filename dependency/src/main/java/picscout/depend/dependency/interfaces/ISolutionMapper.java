package picscout.depend.dependency.interfaces;

import java.util.List;

public interface ISolutionMapper {
	public abstract void add(ISolution solution);
	public abstract List<ISolution> getSolutionsByProjects(List<IProjectDescriptor> projectDescriptors);	
	public abstract List<ISolution> getSolutionsBySolutionsNames(List<String> names);
}
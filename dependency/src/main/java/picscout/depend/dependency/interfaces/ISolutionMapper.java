package picscout.depend.dependency.interfaces;

import java.util.List;

public interface ISolutionMapper {
	public abstract void add(ISolution solution);
	public abstract List<ISolution> getSolutionsByProject(IProjectDescriptor projectDescriptor);
	public void init(IProjectDependencyMapper projectMapper);
}
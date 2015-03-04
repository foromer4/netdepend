package picscout.depend.dependency.classes;

import java.util.List;

import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
import picscout.depend.dependency.interfaces.ISolution;

public interface ISolutionMapper {
	public abstract void add(ISolution solution);
	public abstract List<ISolution> getSolutionsByProject(IProjectDescriptor projectDescriptor);
	public void init(IProjectDependencyMapper projectMapper);
}
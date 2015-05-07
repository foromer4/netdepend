package picscout.depend.dependency.interfaces;


public interface IMapBuilder {
	
	public abstract IProjectDependencyMapper getProjectMapper();

	public abstract ISolutionMapper getSolutionMapper();

	public abstract void parse(String[] rootPaths);

}
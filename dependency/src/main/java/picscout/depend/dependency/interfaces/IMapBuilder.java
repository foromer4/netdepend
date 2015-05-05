package picscout.depend.dependency.interfaces;


public interface IMapBuilder {

	public abstract IProjectStore getProjectStore();

	public abstract IProjectDependencyMapper getProjectMapper();

	public abstract ISolutionMapper getSolutionMapper();

	public abstract void parse(String[] rootPaths);

}
package picscout.depend.dependency;

public interface IProjectDescriptor {

	public abstract String toString();

	public abstract String getName();

	public abstract String getGuid();

	public abstract String getFullPath();

	public abstract String getAssemblyName();

}
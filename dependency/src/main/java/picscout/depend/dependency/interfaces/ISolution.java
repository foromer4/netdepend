package picscout.depend.dependency.interfaces;

import java.util.List;

public interface ISolution {

	public abstract String getFullPath();

	public abstract String getPath();

	public abstract String getName();

	public abstract List<IProjectDescriptor> getProjectsDescriptors();

	public abstract void parse();

}
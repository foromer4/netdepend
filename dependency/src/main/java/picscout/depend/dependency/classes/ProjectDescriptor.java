package picscout.depend.dependency.classes;

import java.io.File;
import java.io.IOException;

import picscout.depend.dependency.interfaces.IProjectDescriptor;

/**
 * Descriptor for project might contain some or all of these fields
 * @author OSchliefer
 *
 */
public final class ProjectDescriptor implements IProjectDescriptor {

	private String fullPath;
	private String name;
	private String guid;	
	private String assemblyName;
	
	
	public ProjectDescriptor(String fullPath,String name, String guid, String assemblyName)
	{
		File file = new File(fullPath);
		try {
			this.fullPath = file.getCanonicalPath();
		} catch (IOException e) {
			this.fullPath = fullPath;
		}
		this.name = name;
		this.guid = guid;
		this.assemblyName = assemblyName;		
	}
	
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProjectDescriptor#toString()
	 */
	@Override
	public String toString() {
		return "ProjectDescriptor [fullPath=" + fullPath + ", name=" + name
				+ ", guid=" + guid + ", assemblyName=" + assemblyName + "]";
	}

	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProjectDescriptor#getName()
	 */
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProjectDescriptor#getGuid()
	 */
	public String getGuid() {
		return guid;
	}
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProjectDescriptor#getFullPath()
	 */
	public String getFullPath() {
		return fullPath;
	}
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProjectDescriptor#getAssemblyName()
	 */
	public String getAssemblyName() {
		return assemblyName;
	}

		
}

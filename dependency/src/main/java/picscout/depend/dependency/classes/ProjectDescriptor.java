package picscout.depend.dependency.classes;

import java.io.File;
import java.io.IOException;

import picscout.depend.dependency.interfaces.IProjectDescriptor;

/**
 * Descriptor for project might contain some or all of these fields.
 * Equality and hash code are based on guid, so do not compare with
 * partial descriptors containins only assembly name.
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assemblyName == null) ? 0 : assemblyName.hashCode());
		result = prime * result
				+ ((fullPath == null) ? 0 : fullPath.hashCode());
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectDescriptor other = (ProjectDescriptor) obj;
		if (assemblyName == null) {
			if (other.assemblyName != null)
				return false;
		} else if (!assemblyName.equals(other.assemblyName))
			return false;
		if (fullPath == null) {
			if (other.fullPath != null)
				return false;
		} else if (!fullPath.equals(other.fullPath))
			return false;
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
		
}

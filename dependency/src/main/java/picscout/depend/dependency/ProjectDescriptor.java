package picscout.depend.dependency;

import java.io.File;
import java.io.IOException;

/**
 * Descriptor for project might contain some or all of these fields
 * @author OSchliefer
 *
 */
public final class ProjectDescriptor {

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
	
	@Override
	public String toString() {
		return "ProjectDescriptor [fullPath=" + fullPath + ", name=" + name
				+ ", guid=" + guid + ", assemblyName=" + assemblyName + "]";
	}

	public String getName() {
		return name;
	}
	public String getGuid() {
		return guid;
	}
	public String getFullPath() {
		return fullPath;
	}
	public String getAssemblyName() {
		return assemblyName;
	}
	
}

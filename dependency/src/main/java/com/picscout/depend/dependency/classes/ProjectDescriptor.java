package com.picscout.depend.dependency.classes;

import java.io.File;
import java.io.IOException;

import com.picscout.depend.dependency.interfaces.IProjectDescriptor;

/**
 * Descriptor for project might contain some or all of these fields. Equality
 * and hash code are based on guid, so do not compare with partial descriptors
 * containins only assembly name.
 * 
 * @see IProjectDescriptor
 * @author OSchliefer
 *
 */
public final class ProjectDescriptor implements IProjectDescriptor {

	private String fullPath;
	private String name;
	private String guid;
	private String assemblyName;

	public ProjectDescriptor(String fullPath, String name, String guid,
			String assemblyName) {
		File file = new File(fullPath);
		try {
			this.fullPath = file.getCanonicalPath();
		} catch (IOException e) {
			this.fullPath = fullPath;
		}
		this.name = name;
		this.guid = guid.toLowerCase();
		this.assemblyName = assemblyName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProjectDescriptor#toString()
	 */
	@Override
	public String toString() {
		return "ProjectDescriptor [fullPath=" + fullPath + ", name=" + name
				+ ", guid=" + guid + ", assemblyName=" + assemblyName + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProjectDescriptor#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProjectDescriptor#getGuid()
	 */
	public String getGuid() {
		return guid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProjectDescriptor#getFullPath()
	 */
	public String getFullPath() {
		return fullPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProjectDescriptor#getAssemblyName()
	 */
	public String getAssemblyName() {
		return assemblyName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
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
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		return true;
	}
}

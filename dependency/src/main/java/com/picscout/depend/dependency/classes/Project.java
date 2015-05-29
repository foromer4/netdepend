package com.picscout.depend.dependency.classes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import com.picscout.depend.dependency.interfaces.IProject;
import com.picscout.depend.dependency.interfaces.IProjectDescriptor;
import com.picscout.depend.dependency.utils.XmlUtils;

/**
 * @see IProject
 * @author OSchliefer
 *
 */
public class Project implements IProject {

	private static final String PROJECT_GUID = "ProjectGuid";
	private final static String PROJECT_REFRENCE_XPATH = "/Project/ItemGroup/ProjectReference/Project";
	private final static String ASSEMBLY_REFRENCE_XPATH = "/Project/ItemGroup/Reference";
	private static final String ASSEMBLY_NAME = "AssemblyName";
	private String fullPath;
	private IProjectDescriptor descriptor;
	private List<String> depnedenciesAssembliesNames;
	private List<String> dependenciesGuids;

	public Project(String fullPath) {
		this.fullPath = fullPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProject#getDepnedenciesNames()
	 */
	public List<String> getDepnedenciesAssembliesNames() {
		return depnedenciesAssembliesNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProject#getDepnedenciesNames()
	 */
	public List<String> getDependenciesGuids() {
		return dependenciesGuids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProject#getFullPath()
	 */
	public IProjectDescriptor getDescriptor() {
		return descriptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.IProject#parse()
	 */
	public void parse() {
		generateDescriptor();

		parseDependencies();
	}

	private void generateDescriptor() {
		String assemblyName = extractAssemblyName();
		String guid = extractGuid();
		File file = new File(fullPath);
		String name = FilenameUtils.removeExtension(file.getName());
		descriptor = new ProjectDescriptor(fullPath, name, guid, assemblyName);
	}

	private void parseDependencies() {
		extractProjectDependenices();
		extractAssemblyDependencies();
	}

	private void extractAssemblyDependencies() {
		depnedenciesAssembliesNames = new ArrayList<String>();
		List<String> results = XmlUtils.readAttributeFromXML(fullPath,
				ASSEMBLY_REFRENCE_XPATH, "Include");
		;
		for (String result : results) {
			depnedenciesAssembliesNames.add(result);
		}
	}

	private void extractProjectDependenices() {
		dependenciesGuids = new ArrayList<String>();
		List<String> results = XmlUtils.readTextFromXML(fullPath,
				PROJECT_REFRENCE_XPATH);
		for (String result : results) {
			dependenciesGuids.add(RemoveCurlyBraces(result));
		}
	}

	private String RemoveCurlyBraces(String str) {
		return str.replace("{", "").replace("}", "");
	}

	private String extractAssemblyName() {
		return XmlUtils.readValueFromXML(fullPath, ASSEMBLY_NAME);
	}

	private String extractGuid() {
		return RemoveCurlyBraces(XmlUtils.readValueFromXML(fullPath,
				PROJECT_GUID));
	}
}

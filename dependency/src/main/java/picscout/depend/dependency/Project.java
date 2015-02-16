package picscout.depend.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Project implements IProject {

	
	private static final String PROJECT_GUID = "ProjectGuid";
	private final static String PROJECT_REFRENCE_XPATH = "/Project/ItemGroup/ProjectReference/Project";
	private final static String ASSEMBLY_REFRENCE_XPATH = "/Project/ItemGroup/Reference";
	private static final String ASSEMBLY_NAME = "AssemblyName";	
	private final String fullPath;
    private final String path;
	private final String fileName;
	private String assemblyName;
	private String guid;
	private List<String> depnedenciesAssembliesNames;
	private List<String> dependenciesGuids;
	
	
	public Project(String fullPath)
	{
		this.fullPath = fullPath;
		File file =  new File(fullPath);
		this.fileName = file.getName();
		this.path = file.getParent();
	}
	
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProject#getPath()
	 */
	public String getPath() {
		return path;
	}


	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProject#getAssemblyName()
	 */
	public String getAssemblyName() {
		return assemblyName;
	}
	

	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProject#getDepnedenciesNames()
	 */
	public List<String> getDepnedenciesAssembliesNames() {
		return depnedenciesAssembliesNames;
	}
	
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProject#getDepnedenciesNames()
	 */
	public List<String> getDependenciesGuids() {
		return dependenciesGuids;
	}

	

	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProject#getName()
	 */
	public String getFileName() {
		return fileName;
	}
	
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProject#getName()
	 */
	public String getGuid() {
		return guid;
	}
	
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProject#getFullPath()
	 */
	public String getFullPath() {
		return fullPath;
	}
	
	/* (non-Javadoc)
	 * @see picscout.depend.dependency.IProject#parse()
	 */
	public void parse()
	{
		parseAssemblyName();
		parseGuid();
		parseDependencies();
	}

	private void parseDependencies() {		
		
		extractProjectDependenices();			
		extractAssemblyDependencies();
	}

	private void extractAssemblyDependencies() {
		depnedenciesAssembliesNames = new ArrayList<String>();
		List<String> results = XmlUtils.readAttributeFromXML(fullPath, ASSEMBLY_REFRENCE_XPATH, "Include");;
		for(String result : results)		{
			depnedenciesAssembliesNames.add(result);
		}
	}

	private void extractProjectDependenices() {
		dependenciesGuids =  new ArrayList<String>();
		List<String> results = XmlUtils.readTextFromXML(fullPath, PROJECT_REFRENCE_XPATH);
		for(String result : results)		{
			dependenciesGuids.add(RemoveCurlyBraces(result));
		}
	}
	
	
	private String RemoveCurlyBraces(String str){
		return str.replace("{", "").replace("}", "");
	}

	private void parseAssemblyName() {
		assemblyName = XmlUtils.readValueFromXML(fullPath, ASSEMBLY_NAME);
	}
	
	private void parseGuid() {
		guid = RemoveCurlyBraces(XmlUtils.readValueFromXML(fullPath, PROJECT_GUID));
	}
}

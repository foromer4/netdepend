package picscout.depend.dependency.classes;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import picscout.depend.dependency.utils.FileUtils;


public class Solution {

	private final String fullPath;
	private final String path;
	private final String name;
	private List<ProjectDescriptor> projects = new ArrayList<ProjectDescriptor>();
    static final Logger logger = LogManager.getLogger(Solution.class.getName());
	
	public Solution(String fullPath) {
		this.fullPath = fullPath;
		File file = new File(fullPath);
		this.name = file.getName();
		this.path = file.getParent();

	}

	public String getFullPath() {
		return fullPath;
	}
	
	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}
	
	public List<ProjectDescriptor> getProjects()	{
		return projects;
	}

	public void parse() {
		List<String> lines = null;
		try {
			lines = FileUtils.readFile(fullPath);
		} catch (IOException e) {
		}

		for (String line : lines) {
			parseLine(line.toLowerCase());
		}
	}

	private void parseLine(String line) {

		try {
			line = line.toLowerCase().replaceAll("\\s+", "");
			String regex = Pattern.quote("project(\"{")
					+ "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[0-9a-f]{12}"
					+ Pattern.quote("}\")=");

			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				int endIndex = matcher.end();
				line = line.substring(endIndex).replace("\"", "");
				String[] splittedData = line.split(",");
				extractProjectDescriptor(splittedData);
			}
		}
		catch (Exception ex) {
			logger.warn("Error parsing data in solution: " + fullPath, ex);
		}
	}

	private void extractProjectDescriptor(String[] splittedData) {
		String projectName = splittedData[0];
		String projectFullPath = path +"\\" + splittedData[1];				
		String projectGuid = splittedData[2].replace("{", "").replace("}", "");;
		ProjectDescriptor descriptor = new ProjectDescriptor(projectFullPath, projectName, projectGuid, null);
        
		logger.info("added new project descriptor to solution: " + descriptor.toString());
		projects.add(descriptor);
	}
	
 
}

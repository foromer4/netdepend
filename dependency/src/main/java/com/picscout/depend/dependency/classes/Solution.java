package com.picscout.depend.dependency.classes;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.picscout.depend.dependency.interfaces.IProjectDescriptor;
import com.picscout.depend.dependency.interfaces.ISolution;
import com.picscout.depend.dependency.utils.FileUtilsHelper;

/**
 * See ISolution
 * 
 * @author OSchliefer
 *
 */
public class Solution implements ISolution {

	private final String fullPath;
	private final String path;
	private final String name;
	private List<IProjectDescriptor> projects = new ArrayList<IProjectDescriptor>();
	static final Logger logger = LogManager.getLogger(Solution.class.getName());

	public Solution(String fullPath) {
		this.fullPath = fullPath;
		File file = new File(fullPath);
		this.name = file.getName();
		this.path = file.getParent();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.classes.ISolution#getFullPath()
	 */
	public String getFullPath() {
		return fullPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.classes.ISolution#getPath()
	 */
	public String getPath() {
		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.classes.ISolution#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.classes.ISolution#getProjects()
	 */
	public List<IProjectDescriptor> getProjectsDescriptors() {
		return projects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.picscout.depend.dependency.classes.ISolution#parse()
	 */
	public void parse() {
		List<String> lines = null;
		try {
			lines = FileUtilsHelper.readFile(fullPath);
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
		} catch (Exception ex) {
			logger.warn("Error parsing data in solution: " + fullPath, ex);
		}
	}

	private void extractProjectDescriptor(String[] splittedData) {
		String projectName = splittedData[0];
		String projectFullPath = path + "\\" + splittedData[1];
		String projectGuid = splittedData[2].replace("{", "").replace("}", "");
		;
		ProjectDescriptor descriptor = new ProjectDescriptor(projectFullPath,
				projectName, projectGuid, null);
		logger.info("added new project descriptor to solution: "
				+ descriptor.toString());
		projects.add(descriptor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullPath == null) ? 0 : fullPath.hashCode());
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
		Solution other = (Solution) obj;
		if (fullPath == null) {
			if (other.fullPath != null)
				return false;
		} else if (!fullPath.equals(other.fullPath))
			return false;
		return true;
	}

}

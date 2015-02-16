package picscout.depend.dependency;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Solution {

	private final String fullPath;
	private final String path;
	private final String name;
	private List<IProject> projects = new ArrayList<IProject>();
	private ProjectBuilder projectBuilder= new ProjectBuilder();

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
	
	List<IProject> getProjects()	{
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
				String projectName = path +"\\" + splittedData[1];
				IProject project = projectBuilder.create(projectName);
				project.parse();
				projects.add(project);
			}
		}

		catch (Exception ex) {
			// TODO - write log [O.S]
		}
	}
	
    /**
     * For testing only	
     * @param builder
     */
	public void setProjectBuilder(ProjectBuilder builder)
	{
		projectBuilder = builder;
	}
}

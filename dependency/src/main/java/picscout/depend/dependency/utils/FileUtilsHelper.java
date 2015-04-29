package picscout.depend.dependency.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

public class FileUtilsHelper {
	public static List<String> readFile(String file)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<String> lines = new ArrayList<String>();
		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		return lines;
	}
	
	
	public static Collection<File> listFiles(String[] rootPaths, String[] extensionFilters) {	
	
		Set<File> aggregatedResult = new HashSet<File>();		
		for(String rootPath: rootPaths) {		
	    Collection<File> result = FileUtils.listFiles(new File(rootPath), extensionFilters, true);	   
		aggregatedResult.addAll(result);
		}
		return aggregatedResult;
	}
}

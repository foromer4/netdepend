package com.picscout.depend.dependency.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;

/**
 * Helper for file handeling
 * 
 * @author OSchliefer
 *
 */
public class FileUtilsHelper {
	public static List<String> readFile(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<String> lines = new ArrayList<String>();
		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		return lines;
	}

	/**
	 * List all files in given paths acccording to extension
	 * 
	 * @param rootPaths
	 *            root paths to look at (include sub directories)
	 * @param extensionFilters
	 *            extensions to look for
	 * @return files in directories matching the extesion filters
	 */
	public static Collection<File> listFiles(String[] rootPaths,
			String[] extensionFilters) {

		Set<File> aggregatedResult = new HashSet<File>();
		for (String rootPath : rootPaths) {
			if (!rootPath.endsWith("\\")) {
				rootPath += "\\";
			}
			Collection<File> result = FileUtils.listFiles(new File(rootPath),
					extensionFilters, true);
			aggregatedResult.addAll(result);
		}
		return aggregatedResult;
	}
}

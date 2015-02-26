package picscout.depend.dependency.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

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
	
	
	public static Collection<File> listFiles(String rootPath, String[] extensionFilters) {	
	
		return FileUtils.listFiles(new File(rootPath), extensionFilters, true);
	}
}

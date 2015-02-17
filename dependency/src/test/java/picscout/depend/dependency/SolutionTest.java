package picscout.depend.dependency;

import java.io.File;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;

import java.lang.ClassLoader;
import java.net.URL;

import junit.framework.Assert;
import picscout.depend.dependency.Solution;

public class SolutionTest {

	Solution solution;
	
	@Before
	public void init() {

		//solution = new Solution(
		//		"C:\\git\\imagetracker\\Source\\PicScout.IT.Common\\PicScout.IT.Common.sln");
		
		URL url = this.getClass().getResource("/test.sln");
		solution = new Solution(url.getPath());
	}	

	@Test
	public void testParse() {
		solution.parse();	
		Assert.assertEquals("should have 4 projects",4,solution.getProjects().size());
	}
}

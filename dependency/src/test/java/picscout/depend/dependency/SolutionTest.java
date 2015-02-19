package picscout.depend.dependency;

import org.junit.Test;
import org.junit.Before;
import java.net.URL;

import org.junit.Assert;
import picscout.depend.dependency.Solution;

public class SolutionTest {

	Solution solution;
	
	@Before
	public void init() {		
		URL url = this.getClass().getResource("/test.sln");
		solution = new Solution(url.getPath());
	}	

	@Test
	public void testParse() {
		solution.parse();	
		Assert.assertEquals("should have 4 projects",4,solution.getProjects().size());
	}
}

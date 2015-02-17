package picscout.depend.dependency;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;
import picscout.depend.dependency.Solution;

public class SolutionTest {

	Solution solution;
	
	@Before
	public void init() {

		//solution = new Solution(
		//		"C:\\git\\imagetracker\\Source\\PicScout.IT.Common\\PicScout.IT.Common.sln");
		
		solution = new Solution("C:\\Users\\oschliefer\\Documents\\Visual Studio 2013\\Projects\\ConsoleApplication1\\ConsoleApplication1.sln");
	}	

	@Test
	public void testParse() {
		solution.parse();			
	}
}

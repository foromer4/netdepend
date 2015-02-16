package picscout.depend.dependency;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;
import picscout.depend.dependency.Solution;

public class SolutionTest {

	Solution solution;
	IProject project;
	@Before
	public void init() {

		solution = new Solution(
				"C:\\git\\imagetracker\\Source\\PicScout.IT.Common\\PicScout.IT.Common.sln");
		ProjectBuilder builder = Mockito.mock(ProjectBuilder.class);
		project = Mockito.mock(IProject.class);
		Mockito.when(builder.create(Mockito.anyString())).thenReturn(project);
		solution.setProjectBuilder(builder);
	}	

	@Test
	public void testParse() {
		solution.parse();		
		Mockito.verify(project, Mockito.times(solution.getProjects().size())).parse();		
	}
}

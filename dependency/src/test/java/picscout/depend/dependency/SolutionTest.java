package picscout.depend.dependency;

import org.junit.Test;
import org.junit.Before;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import org.junit.Assert;

import picscout.depend.dependency.classes.ProjectDescriptor;
import picscout.depend.dependency.classes.Solution;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
import picscout.depend.dependency.interfaces.ISolution;

public class SolutionTest {

	ISolution solution;
	
	@Before
	public void init() {		
		URL url = this.getClass().getResource("/test.sln");
		solution = new Solution(url.getPath());
	}	

	@Test
	public void testParse() {
		List<IProjectDescriptor> expectedProjectDescriptors = createProjectDescriptors();
		solution.parse();	
		Assert.assertEquals("Project Descriptors should be as expected",expectedProjectDescriptors,solution.getProjectsDescriptors());
	}

	private List<IProjectDescriptor> createProjectDescriptors() {
		List<IProjectDescriptor> list = new ArrayList<IProjectDescriptor>();
		list.add(new ProjectDescriptor("C:\\git\\netdepend\\dependency\\target\\classes\\picscout.it.priorityq\\picscout.it.priorityq.csproj", "picscout.it.priorityq", "27b0461c-5d5c-4406-9a1d-564717778b8c", null));
		list.add(new ProjectDescriptor("C:\\git\\netdepend\\dependency\\target\\classes\\tests", "tests", "4567d585-5619-4e27-9198-f86e9d1d08bf", null));
		list.add(new ProjectDescriptor("C:\\git\\netdepend\\dependency\\target\\classes\\picscout.it.priorityq.integration\\picscout.it.priorityq.integration.csproj", "picscout.it.priorityq.integration", "13257306-02d0-496e-9b47-eb4d5914606c", null));
		list.add(new ProjectDescriptor("C:\\git\\netdepend\\dependency\\target\\classes\\picscout.it.priorityq.test\\picscout.it.priorityq.test.csproj", "picscout.it.priorityq.test", "2130d535-50cc-497e-b1e9-768e24d81ee9", null));
		return list;
	}
	
}

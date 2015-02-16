package picscout.depend.dependency;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ProjectTest {
	

	Project project;
	
	@Before
	public void init() {

		project = new Project(
				"C:\\git\\imagetracker\\Source\\Reporting\\PicScout.IT.CFW\\PicScout.IT.CFW.Site\\PicScout.IT.CFW.Site.csproj");		
	}	

	@Test
	public void testParse() {
		project.parse();					
	}
}

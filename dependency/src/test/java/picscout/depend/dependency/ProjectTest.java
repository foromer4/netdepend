package picscout.depend.dependency;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ProjectTest {
	

	IProject project;
	
	@Before
	public void init() {

		URL url = this.getClass().getResource("/test.csproj");
	    project = new Project(url.getPath());
				
	}	

	@Test
	public void testParse() {		
		project.parse();		
		//Assert.assertEquals("Assembly name should be correct", "AssemblyName", project.getDescriptor().getAssemblyName());
		project.getDependenciesGuids();
		project.getDepnedenciesAssembliesNames();
	}
}

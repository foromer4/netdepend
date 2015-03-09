package picscout.depend.dependency;


import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import picscout.depend.dependency.classes.Project;
import picscout.depend.dependency.interfaces.IProject;

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
		List<String> expectedDepnedenciesGuids = CreateDepnedenciesGuids();
		List<String> expectedDepnedenciesAssembliesNames = CreateDepnedenciesAssembliesNames();
		Assert.assertEquals("should have 1 Guid dependency",expectedDepnedenciesGuids,project.getDependenciesGuids());
		Assert.assertEquals("should have 8 Guid assembly names",expectedDepnedenciesAssembliesNames,project.getDepnedenciesAssembliesNames());
	}

	private List<String> CreateDepnedenciesGuids() {
		List<String> list =  new ArrayList<String>();
		list.add("8b7db1ba-afc9-42aa-855c-c588876b65e2");
		return list;
	}

	private List<String> CreateDepnedenciesAssembliesNames() {
		List<String> list =  new ArrayList<String>();
		list.add("PicScout.IR.Crawler.Common");
		list.add("System");
		list.add("System.Core");
		list.add("System.Xml.Linq");
		list.add("System.Data.DataSetExtensions");
		list.add("Microsoft.CSharp");
		list.add("System.Data");
		list.add("System.Xml");
		return list;
	}
	
	
}

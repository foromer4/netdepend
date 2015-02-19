package picscout.depend.dependency;

import static org.junit.Assert.*;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ProjectStoreTest {
	
	
	private ProjectStore store;
	private IProject project;
	private IProjectDescriptor descriptor;
	private String assemblyName = "Assembly.Name";
	private String projectGuid = "DFGDF-34RER-DFGDFG-45";
	
	@Before
	public void init()
	{
		store = new ProjectStore();
		project = Mockito.mock(IProject.class);
		descriptor = Mockito.mock(IProjectDescriptor.class);
		Mockito.when(project.getDescriptor()).thenReturn(descriptor);
		Mockito.when(descriptor.getAssemblyName()).thenReturn(assemblyName);
		Mockito.when(descriptor.getGuid()).thenReturn(projectGuid);
	}

	@Test
	public void testGetProjectByAssmblyEmpty() {  
	 assertNull("no project should return", store.getProjectByAssemblyName(assemblyName));    
	}
	
	@Test
	public void testGetProjectByGuidEmpty() {  
	 assertNull("no project should return", store.getProjectByGuid(projectGuid));    
	}
	
	@Test
	public void testGetProjectByAssmblyWrongName() {  
	  store.addProject(project);
	  assertNull("no project should return", store.getProjectByAssemblyName(projectGuid));    
	}
	
	@Test
	public void testGetProjectByGuidWrongName() {  
		  store.addProject(project);
	 assertNull("no project should return", store.getProjectByGuid(assemblyName));    
	}
	
	@Test
	public void testGetProjectByAssmblyCorrectName() {  
	  store.addProject(project);
	  assertEquals("A project should return", store.getProjectByAssemblyName(assemblyName), project);    
	}
	
	@Test
	public void testGetProjectByGuidCorrectName() {  
		  store.addProject(project);
		  assertEquals("A project should return", store.getProjectByGuid(projectGuid), project);    
	}

}

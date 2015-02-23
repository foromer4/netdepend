package picscout.depend.dependency;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectDescriptor;

public class TestProjectsGenerator {
	
	/**
	 * Build a simulated connection chain between projects a-d.
	 * A -> B , D
	 * B -> C ,D
	 * D -> C
	 * C -> D
	 * @return Projects mocks
	 */
	public static List<IProject> generateProjects()	
	{
		
		ArrayList<IProject> projects = new ArrayList<IProject>();
		IProject aProj = createParoject("a", projects);
		IProject bProj = createParoject("b", projects);
		IProject cProj = createParoject("c", projects);
		IProject dProj = createParoject("d", projects);
		
		addDependency(aProj, bProj , true);
		addDependency(aProj, dProj , false);
		addDependency(bProj, cProj , true);
		addDependency(bProj, dProj , false);
		addDependency(dProj, cProj , true);
		addDependency(cProj, dProj , false);
		
		return projects;
	}

	private static void addDependency(IProject project, IProject projectDependency, boolean isGuidDependency) {
		if(isGuidDependency){
			ArrayList<String> result = new ArrayList<String>();
			result.add(projectDependency.getDescriptor().getGuid());
			when(project.getDependenciesGuids()).thenReturn(result);
		}
		else {
			ArrayList<String> result = new ArrayList<String>();
			result.add(projectDependency.getDescriptor().getAssemblyName());
			when(project.getDepnedenciesAssembliesNames()).thenReturn(result);
		}
		
	}

	private static IProject createParoject(String name, ArrayList<IProject> projects) {
		IProject project = mock(IProject.class);
		IProjectDescriptor descriptor = mock(IProjectDescriptor.class);
		when(project.getDescriptor()).thenReturn(descriptor);
		when(descriptor.getAssemblyName()).thenReturn("assembly-"+name);
		when(descriptor.getGuid()).thenReturn("guid-"+name);		
		projects.add(project);
		return project;
	}
}

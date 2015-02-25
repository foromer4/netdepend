package picscout.depend.dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import picscout.depend.dependency.classes.ProjectDependencyMapper;
import picscout.depend.dependency.classes.ProjectStore;
import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectDescriptor;

public class ProjectDependencyMapperTest {

	private IProjectDependencyMapper mapper;
	private List<IProject> projects;

	@Before
	public void init() {
		ProjectStore store = new ProjectStore();
		projects = TestProjectsGenerator.generateProjects();
		for (IProject project : projects) {
			store.addProject(project);
		}
		mapper = new ProjectDependencyMapper(store);
		mapper.init();
	}

	@Test
	public void testChainForDProject() {
		IProject dProj = projects.get(3);
		List<IProjectDescriptor> result = mapper.getProjectsThatDepeantOn(dProj
				.getDescriptor());
		ArrayList<IProjectDescriptor> expected = new ArrayList<IProjectDescriptor>();
		expected.add(projects.get(2).getDescriptor());
		expected.add(projects.get(1).getDescriptor());
		expected.add(projects.get(0).getDescriptor());
		assertArrayEquals("d should have c,b,a projects that depend on it",
				expected.toArray(), result.toArray());
	}

	@Test
	public void testChainForCProject() {
		IProject cProj = projects.get(2);
		List<IProjectDescriptor> result = mapper.getProjectsThatDepeantOn(cProj
				.getDescriptor());
		ArrayList<IProjectDescriptor> expected = new ArrayList<IProjectDescriptor>();
		expected.add(projects.get(3).getDescriptor());
		expected.add(projects.get(1).getDescriptor());
		expected.add(projects.get(0).getDescriptor());
		assertArrayEquals("c should have d,b,a projects depend on it",
				expected.toArray(), result.toArray());
	}

	@Test
	public void testChainForBProject() {
		IProject bProj = projects.get(1);
		List<IProjectDescriptor> result = mapper.getProjectsThatDepeantOn(bProj
				.getDescriptor());
		ArrayList<IProjectDescriptor> expected = new ArrayList<IProjectDescriptor>();
		expected.add(projects.get(0).getDescriptor());
		assertArrayEquals("b should have a project depending on it",
				expected.toArray(), result.toArray());
	}

	@Test
	public void testChainForAProject() {
		IProject aProj = projects.get(0);
		List<IProjectDescriptor> result = mapper.getProjectsThatDepeantOn(aProj
				.getDescriptor());
		assertNull("a should have no projects that depend on it", result);
	}

	@Test
	public void testChainMap() {
		Map<IProjectDescriptor, List<IProjectDescriptor>> map = mapper.getMap();
		for (int i = 0; i < projects.size(); i++) {
			List<IProjectDescriptor> expected = mapper
					.getProjectsThatDepeantOn(projects.get(i).getDescriptor());
			List<IProjectDescriptor> actual = map.get(projects.get(i).getDescriptor());
			if (expected != null) {
				assertArrayEquals("map shold reflect chains",
						expected.toArray(), actual.toArray());
			} else {
				assertNull(
						"If chain is null,map result for that key should be null",
						actual);
			}

		}

	}

}

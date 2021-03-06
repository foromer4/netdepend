package com.picscout.depend.dependency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.picscout.depend.dependency.classes.ProjectDependencyMapper;
import com.picscout.depend.dependency.classes.ProjectStore;
import com.picscout.depend.dependency.interfaces.IProject;
import com.picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import com.picscout.depend.dependency.interfaces.IProjectDescriptor;

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
	public void testChainForABProject() {
		IProject aProj = projects.get(0);
		IProject bProj = projects.get(1);
		List<IProjectDescriptor> result = mapper
				.getProjectsThatDepeantOn(Arrays
						.asList(new IProjectDescriptor[] {
								aProj.getDescriptor(), bProj.getDescriptor() }));

		assertNull("a and b should have no project depending on them", result);
	}

	@Test
	public void testChainForBCProject() {
		IProject bProj = projects.get(1);
		IProject cProj = projects.get(2);
		List<IProjectDescriptor> result = mapper
				.getProjectsThatDepeantOn(Arrays
						.asList(new IProjectDescriptor[] {
								bProj.getDescriptor(), cProj.getDescriptor() }));

		ArrayList<IProjectDescriptor> expected = new ArrayList<IProjectDescriptor>();
		expected.add(projects.get(0).getDescriptor());
		expected.add(projects.get(3).getDescriptor());

		assertArrayEquals(
				"b ,c projects should have a , d project depending on them",
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
	public void testChainForAssemblies() {
		IProject gProj = projects.get(6);
		List<IProjectDescriptor> result = mapper.getProjectsThatDepeantOn(gProj
				.getDescriptor());
		assertEquals("G should have 2 projects that depend on it", 2,
				result.size());
	}

	@Test
	public void testChainMap() {
		Map<IProjectDescriptor, List<IProjectDescriptor>> map = mapper.getMap();
		for (int i = 0; i < projects.size(); i++) {
			List<IProjectDescriptor> expected = mapper
					.getProjectsThatDepeantOn(projects.get(i).getDescriptor());
			List<IProjectDescriptor> actual = map.get(projects.get(i)
					.getDescriptor());
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

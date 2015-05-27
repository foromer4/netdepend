package com.picscout.depend.dependency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.picscout.depend.dependency.classes.ProjectDependencyMapper;
import com.picscout.depend.dependency.classes.SolutionMapper;
import com.picscout.depend.dependency.interfaces.IProject;
import com.picscout.depend.dependency.interfaces.IProjectDescriptor;
import com.picscout.depend.dependency.interfaces.ISolution;

public class SolutionMapperTest {

	private SolutionMapper mapper;
	private ArrayList<IProject> projects = new ArrayList<IProject>();
	private IProject aProj;
	private IProject bProj;
	private IProject cProj;
	private IProject dProj;
	private ISolution aSol;
	private ISolution bSol;
	private ISolution cSol;
	private ISolution dSol;
	private List<ISolution> result;

	@Before
	public void init() {
		ProjectDependencyMapper projectMapper = Mockito
				.mock(ProjectDependencyMapper.class);
		mapper = new SolutionMapper(projectMapper);
		createProjects(projectMapper);
		createSolutions();
	}

	private void createSolutions() {
		aSol = TestProjectsGenerator.createSolution("aSolution.sln",
				Arrays.asList(aProj.getDescriptor(), bProj.getDescriptor()));
		bSol = TestProjectsGenerator.createSolution("bSolution.sln", Arrays.asList(
				bProj.getDescriptor(), cProj.getDescriptor(),
				dProj.getDescriptor()));
		cSol = TestProjectsGenerator.createSolution("cSolution.sln",
				Arrays.asList(aProj.getDescriptor(), dProj.getDescriptor()));
		dSol = TestProjectsGenerator.createSolution("dSolution.sln",
				Arrays.asList(cProj.getDescriptor()));

		mapper.add(aSol);
		mapper.add(bSol);
		mapper.add(cSol);
		mapper.add(dSol);
	}

	private void createProjects(ProjectDependencyMapper projectMapper) {
		aProj = TestProjectsGenerator.createParoject("a", projects);
		bProj = TestProjectsGenerator.createParoject("b", projects);
		cProj = TestProjectsGenerator.createParoject("c", projects);
		dProj = TestProjectsGenerator.createParoject("d", projects);

		Mockito.when(
				projectMapper.getProjectsThatDepeantOn(aProj.getDescriptor()))
				.thenReturn(null);
		ArrayList<IProjectDescriptor> list = new ArrayList<IProjectDescriptor>();
		list.add(aProj.getDescriptor());
		Mockito.when(
				projectMapper.getProjectsThatDepeantOn(bProj.getDescriptor()))
				.thenReturn(list);
		list = new ArrayList<IProjectDescriptor>();
		list.add(dProj.getDescriptor());
		list.add(bProj.getDescriptor());
		list.add(aProj.getDescriptor());
		Mockito.when(
				projectMapper.getProjectsThatDepeantOn(cProj.getDescriptor()))
				.thenReturn(list);
		list = new ArrayList<IProjectDescriptor>();
		list.add(cProj.getDescriptor());
		list.add(bProj.getDescriptor());
		list.add(aProj.getDescriptor());
		Mockito.when(
				projectMapper.getProjectsThatDepeantOn(dProj.getDescriptor()))
				.thenReturn(list);
	}

	@Test
	public void testNotExistingProject() {
		result = mapper.getSolutionsByProjects(getSingleList(Mockito
				.mock(IProjectDescriptor.class)));
		Assert.assertTrue("No solutions depend on bogus project",
				result.isEmpty());

	}

	@Test
	public void testProjectA() {
		result = mapper.getSolutionsByProjects(getSingleList(aProj
				.getDescriptor()));

		ISolution[] expected = new ISolution[] {};
		for (ISolution s : expected) {
			System.out.println(s);
		}

		Assert.assertTrue("For project a we should build soutions: a,c",
				checkSameElements(Arrays.asList(aSol, cSol), result));

	}
	
	@Test
	public void testGetBySolutionDName() {
		List<String> names  = Arrays.asList("dSolution");
		List<ISolution> results = mapper.getSolutionsBySolutionsNames(names);		
		Assert.assertTrue(checkSameElements(results, Arrays.asList(aSol, bSol, cSol)));		
	}
	
	@Test
	public void testGetBySolutionDCName() {
		List<String> names  = Arrays.asList("dSolution.sln" , "cSolution.sln");
		List<ISolution> results = mapper.getSolutionsBySolutionsNames(names);		
		Assert.assertTrue(checkSameElements(results, Arrays.asList(aSol, bSol)));		
	}
	
	@Test
	public void testGetBySolutionDCBName() {
		List<String> names  = Arrays.asList("dSolution" , "cSolution.sln" , "bSolution.sln");
		List<ISolution> results = mapper.getSolutionsBySolutionsNames(names);		
		Assert.assertTrue(checkSameElements(results, Arrays.asList(aSol)));		
	}
	
	@Test
	public void testGetBySolutionDCBAName() {
		List<String> names  = Arrays.asList("dSolution.sln" , "cSolution.sln" , "bSolution.sln" , "aSolution.sln");
		List<ISolution> results = mapper.getSolutionsBySolutionsNames(names);		
		Assert.assertEquals("should get no results", 0, results.size());
	}

	@Test
	public void testProjectB() {

		result = mapper.getSolutionsByProjects(getSingleList(bProj
				.getDescriptor()));

		Assert.assertTrue("For project b we should build soutions: a,b,c",
				checkSameElements(Arrays.asList(aSol, bSol, cSol), result));

		Assert.assertEquals("cSol is last in chain (a,b can change places)",
				cSol, result.get(2));
	}
	
	@Test
	public void testGetBySolutionAName() {
		List<String> names  = Arrays.asList("aSolution.sln");
		List<ISolution> results = mapper.getSolutionsBySolutionsNames(names);		
		Assert.assertTrue(checkSameElements(results, Arrays.asList(cSol, bSol)));		
	}

	@Test
	public void testProjectC() {

		result = mapper.getSolutionsByProjects(getSingleList(cProj
				.getDescriptor()));

		Assert.assertTrue(
				"For project C we should build soutions: a,b,c,d",
				checkSameElements(Arrays.asList(aSol, bSol, cSol, dSol), result));

	}

	@Test
	public void testProjectD() {

		result = mapper.getSolutionsByProjects(getSingleList(dProj
				.getDescriptor()));

		Assert.assertTrue(
				"For project D we should build soutions: a,b,c,d",
				checkSameElements(Arrays.asList(aSol, bSol, cSol, dSol), result));

	}

	public boolean checkSameElements(Collection<ISolution> first,
			Collection<ISolution> second) {
		if (first.size() != second.size()) {
			return false;
		}

		for (ISolution sol : first) {
			if (!second.contains(sol)) {
				return false;
			}
		}

		return true;
	}

	private List<IProjectDescriptor> getSingleList(IProjectDescriptor descriptor) {
		List<IProjectDescriptor> result = new ArrayList<IProjectDescriptor>();
		result.add(descriptor);
		return result;
	}

}

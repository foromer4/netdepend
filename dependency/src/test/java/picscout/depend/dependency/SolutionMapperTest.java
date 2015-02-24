package picscout.depend.dependency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import picscout.depend.dependency.classes.ProjectDependencyMapper;
import picscout.depend.dependency.classes.SolutionMapper;
import picscout.depend.dependency.interfaces.IProject;
import picscout.depend.dependency.interfaces.IProjectDescriptor;
import picscout.depend.dependency.interfaces.ISolution;

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

		aSol = TestProjectsGenerator.createSoution("aSolution",
				Arrays.asList(aProj.getDescriptor(), bProj.getDescriptor()));
		bSol = TestProjectsGenerator.createSoution("bSolution", Arrays.asList(
				bProj.getDescriptor(), cProj.getDescriptor(),
				dProj.getDescriptor()));
		cSol = TestProjectsGenerator.createSoution("cSolution",
				Arrays.asList(aProj.getDescriptor(), dProj.getDescriptor()));
		dSol = TestProjectsGenerator.createSoution("dSolution",
				Arrays.asList(cProj.getDescriptor()));

		mapper.add(aSol);
		mapper.add(bSol);
		mapper.add(cSol);
		mapper.add(dSol);
	}

	@Test
	public void testNotExistingProject() {
		result = mapper.getSolutionsByProject(Mockito
				.mock(IProjectDescriptor.class));
		Assert.assertTrue("No solutions depend on bogus project", result.isEmpty());

	}

}

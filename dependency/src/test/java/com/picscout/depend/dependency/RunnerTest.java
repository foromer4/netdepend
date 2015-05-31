package com.picscout.depend.dependency;

import java.util.List;

import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.picscout.depend.dependency.acceptence.MapBuilderTest;
import com.picscout.depend.dependency.classes.MapBuilder;
import com.picscout.depend.dependency.classes.ProjectBuilder;
import com.picscout.depend.dependency.classes.ProjectDependencyMapper;
import com.picscout.depend.dependency.classes.ProjectStore;
import com.picscout.depend.dependency.classes.SolutionBuilder;
import com.picscout.depend.dependency.classes.SolutionMapper;
import com.picscout.depend.dependency.classes.StatePersist;
import com.picscout.depend.dependency.interfaces.IMapBuilder;
import com.picscout.depend.dependency.interfaces.IProjectBuilder;
import com.picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import com.picscout.depend.dependency.interfaces.IProjectDescriptor;
import com.picscout.depend.dependency.interfaces.IProjectStore;
import com.picscout.depend.dependency.interfaces.ISolutionBuilder;
import com.picscout.depend.dependency.interfaces.ISolutionMapper;
import com.picscout.depend.dependency.interfaces.IStatePersist;
import com.picscout.depend.dependency.main.Runner;
import com.picscout.depend.dependency.utils.ConfigUtils;
import com.picscout.depend.dependency.utils.InjectorFactory;

public class RunnerTest {

	Runner runner;
	IMapBuilder mapBuilder;
	IStatePersist statePersist;
	ISolutionMapper solutionMapper;
    IProjectDependencyMapper projectMapper;
	@Before
	public void init() {
		String configPath = MapBuilderTest.class.getResource("/config.xml")
				.toString();
		ConfigUtils.init(configPath);
		runner = new Runner();
		mapBuilder = Mockito.mock(IMapBuilder.class);
		statePersist = Mockito.mock(IStatePersist.class);
		solutionMapper = Mockito.mock(ISolutionMapper.class);
		projectMapper = Mockito.mock(IProjectDependencyMapper.class);
		Mockito.when(mapBuilder.getSolutionMapper()).thenReturn(solutionMapper);
		Mockito.when(mapBuilder.getProjectMapper()).thenReturn(projectMapper);

		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IMapBuilder.class).toInstance(mapBuilder);
				bind(IStatePersist.class).toInstance(statePersist);
			}
		});

		InjectorFactory.setInjector(injector);

	}

	@Test
	public void testCalcDependencies() {
		runner.calculateDependencies();
		Mockito.verify(mapBuilder).parse(Mockito.any(String[].class));
	}

	@Test
	public void testSolutionsOnSoltutionsDepend() {
		runner.getSolutionsThatDependOnSolutionsByNames(null);
		Mockito.verify(solutionMapper).getSolutionsBySolutionsNames(null);
	}
	
	@Test
	public void testSolutionsOnProjectsDepend() {
		runner.getSolutionsThatDependOnProject(null);
		Mockito.verify(solutionMapper).getSolutionsByProjects(Mockito.anyList());
	}
	
	@Test
	public void testProjectsOnProjectsDepend() {
		runner.getProjectsThatDepeandOnProjects(null);
		Mockito.verify(projectMapper).getProjectsThatDepeantOn(Mockito.anyList());
	}
	
	@Test
	public void testProjectsOnProjectDepend() {
		runner.getProjectsThatDepeandOnProject(null);
		Mockito.verify(projectMapper).getProjectsThatDepeantOn(Mockito.any(IProjectDescriptor.class));
	}
}

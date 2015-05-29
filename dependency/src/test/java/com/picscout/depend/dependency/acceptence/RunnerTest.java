package com.picscout.depend.dependency.acceptence;

import org.junit.Before;
import org.mockito.Mockito;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
import com.picscout.depend.dependency.interfaces.IProjectStore;
import com.picscout.depend.dependency.interfaces.ISolutionBuilder;
import com.picscout.depend.dependency.interfaces.ISolutionMapper;
import com.picscout.depend.dependency.interfaces.IStatePersist;
import com.picscout.depend.dependency.main.Runner;
import com.picscout.depend.dependency.utils.ConfigUtils;

public class RunnerTest {

	Runner runner = new Runner();

	@Before
	public void init() {
		String configPath = MapBuilderTest.class.getResource("/config.xml")
				.toString();
		ConfigUtils.init(configPath);

		Injector injector = Guice.createInjector(new AbstractModule() {

			IMapBuilder mapBuilder = Mockito.mock(IMapBuilder.class);

			@Override
			protected void configure() {
				bind(IMapBuilder.class).to(MapBuilder.class);
				bind(IStatePersist.class).to(StatePersist.class);
			}
		});
	}

	public void test() {

	}
}

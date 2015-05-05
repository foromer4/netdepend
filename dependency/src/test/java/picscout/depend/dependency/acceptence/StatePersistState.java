package picscout.depend.dependency.acceptence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import picscout.depend.dependency.classes.MapBuilder;
import picscout.depend.dependency.classes.ProjectBuilder;
import picscout.depend.dependency.classes.ProjectDependencyMapper;
import picscout.depend.dependency.classes.ProjectStore;
import picscout.depend.dependency.classes.SolutionBuilder;
import picscout.depend.dependency.classes.SolutionMapper;
import picscout.depend.dependency.classes.StatePersist;
import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IProjectBuilder;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectStore;
import picscout.depend.dependency.interfaces.ISolutionBuilder;
import picscout.depend.dependency.interfaces.ISolutionMapper;
import picscout.depend.dependency.interfaces.IStatePersist;
import picscout.depend.dependency.utils.ConfigUtils;

public class StatePersistState {

	String[] roots;
	MapBuilder builder;
	StatePersist persister;

	@Before
	public void init() {
		String configPath = MapBuilderTest.class.getResource("/config.xml")
				.toString();
		ConfigUtils.init(configPath);
		roots = ConfigUtils.readList("rootPath", new String[]{"c:\\temp"}, null);
		
Injector injector = Guice.createInjector(new AbstractModule() {
			
			@Override
			protected void configure() {
				 bind (IProjectBuilder.class).to(ProjectBuilder.class);
				 bind (ISolutionBuilder.class).to(SolutionBuilder.class);
				 bind(IProjectStore.class).to(ProjectStore.class);
				 bind(IProjectDependencyMapper.class).to(ProjectDependencyMapper.class);		
				 bind(ISolutionMapper.class).to(SolutionMapper.class);
				 bind(IMapBuilder.class).to(MapBuilder.class);
				 bind(IStatePersist.class).to(StatePersist.class);    
			}
		});
		
		builder = (MapBuilder) injector.getInstance(IMapBuilder.class);
		persister = new StatePersist();
	}

	@Test
	public void testPersist() {
		builder.parse(roots);
		persister.persist(builder);
	}

	@Test
	public void testLoad() {
		IMapBuilder newBuilder = persister.load();
		Assert.assertNotNull("Map builder should be recreated", newBuilder);
	}

}

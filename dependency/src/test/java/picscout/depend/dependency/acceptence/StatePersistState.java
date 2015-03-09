package picscout.depend.dependency.acceptence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import picscout.depend.dependency.classes.MapBuilder;
import picscout.depend.dependency.classes.StatePersist;
import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.utils.ConfigUtils;

public class StatePersistState {

	String root;
	MapBuilder builder;
	StatePersist persister;

	@Before
	public void init() {
		String configPath = MapBuilderTest.class.getResource("/config.xml")
				.toString();
		ConfigUtils.init(configPath);
		root = ConfigUtils.readString("rootPath", "c:\\temp");
		builder = new MapBuilder(root);
		persister = new StatePersist();
	}

	@Test
	public void testPersist() {
		builder.parse();
		persister.persist(builder);
	}

	@Test
	public void testLoad() {
		IMapBuilder newBuilder = persister.load();
		Assert.assertNotNull("Map builder should be recreated", newBuilder);
	}

}

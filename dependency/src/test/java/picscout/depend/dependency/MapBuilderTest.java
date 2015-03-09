package picscout.depend.dependency;

import org.junit.Before;
import org.junit.Test;


import picscout.depend.dependency.classes.MapBuilder;
import picscout.depend.dependency.utils.ConfigUtils;

public class MapBuilderTest {
	String root;
	MapBuilder builder;

	@Before
	public void init() {
		String configPath = MapBuilderTest.class.getResource("/config.xml")
				.toString();
		ConfigUtils.init(configPath);
		root = ConfigUtils.readString("rootPath", "c:\\temp");
		builder = new MapBuilder(root);
	}

	@Test
	public void testParse() {

		builder.parse();
	}

	
}

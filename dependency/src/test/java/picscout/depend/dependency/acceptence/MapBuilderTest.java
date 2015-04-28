package picscout.depend.dependency.acceptence;

import org.junit.Before;
import org.junit.Test;


import picscout.depend.dependency.classes.MapBuilder;
import picscout.depend.dependency.utils.ConfigUtils;

public class MapBuilderTest {
	String[] roots;
	MapBuilder builder;

	@Before
	public void init() {
		String configPath = MapBuilderTest.class.getResource("/config.xml")
				.toString();
		ConfigUtils.init(configPath);
		roots = ConfigUtils.readList("rootPath", new String[] {"c:\\temp"}, null);
		builder = new MapBuilder(roots);
	}

	@Test
	public void testParse() {

		builder.parse();
	}

	
}

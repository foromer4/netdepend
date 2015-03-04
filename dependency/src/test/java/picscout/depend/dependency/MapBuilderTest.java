package picscout.depend.dependency;

import org.junit.Before;
import org.junit.Test;

import picscout.depend.dependency.classes.MapBuilder;
import picscout.depend.dependency.utils.ConfigHelper;

public class MapBuilderTest {
	String root;
	MapBuilder builder; 
	
	@Before
	public void init() {
		ConfigHelper.init();		
	  root =	ConfigHelper.readString("rootPath", "c:\\temp");
	  builder = new MapBuilder(root);
	}
	
	@Test
	public void test() {
		
		builder.parse();		
	}
}

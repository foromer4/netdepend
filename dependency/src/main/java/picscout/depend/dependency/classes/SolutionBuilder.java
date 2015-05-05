package picscout.depend.dependency.classes;

import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.interfaces.ISolutionBuilder;
import javax.inject.Singleton;;

@Singleton
public class SolutionBuilder implements ISolutionBuilder{

	
	public ISolution build(String fullPath) {
		return new Solution(fullPath);
	}

}

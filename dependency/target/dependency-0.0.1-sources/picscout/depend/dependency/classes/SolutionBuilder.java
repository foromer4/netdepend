package picscout.depend.dependency.classes;

import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.interfaces.ISolutionBuilder;
import javax.inject.Singleton;;

/**
 * Builds a solution
 * @see ISolutionBuilder
 * @author OSchliefer
 *
 */
@Singleton
public class SolutionBuilder implements ISolutionBuilder{

	
	public ISolution build(String fullPath) {
		return new Solution(fullPath);
	}

}

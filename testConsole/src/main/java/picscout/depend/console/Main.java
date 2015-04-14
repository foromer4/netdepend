package picscout.depend.console;

import java.util.Arrays;
import java.util.List;

import picscout.depend.dependency.interfaces.ISolution;
import picscout.depend.dependency.main.Runner;

public class Main {

	public static void main(String[] args) {

		// Test jar run

		Runner runner = new Runner();
		if (args.length == 0) {
			System.out.println("Going to run calc dependencies");
			runner.CalculateDependencies();
		}
		
		else {
			System.out.println("Going to run calc dependent solutions");
			List<ISolution> result = runner.getSolutionsThatDependOnSolutionsByNames(Arrays.asList(args));
			for(ISolution sol : result) {
				System.out.println(sol.getName());				
			}
		}
	}

}

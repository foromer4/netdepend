package com.picscout.depend.console;

import java.util.Arrays;
import java.util.List;

import com.picscout.depend.dependency.interfaces.ISolution;
import com.picscout.depend.dependency.main.Runner;

/**
 * Console used to test usage of netdepend , can be packaged as a runnable jar.
 * @author OSchliefer
 *
 */
public class Main {

	/**
	 * Main entry point	
	 * @param args args are solution names to get dependencies for.
	 */
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

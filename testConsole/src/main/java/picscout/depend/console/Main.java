package picscout.depend.console;

import picscout.depend.dependency.main.Runner;

public class Main {

	public static void main(String[] args) {

		//Test jar run
		 System.out.println("Going to run calc dependencies");
 
		 Runner runner = new Runner();
		 runner.CalculateDependencies();
	}

}

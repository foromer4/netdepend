import com.picscout.depend.dependency.main.Runner;
import com.picscout.depend.dependency.interfaces.ISolution;
public class Wrapper {


	public static void main(String[] args) {
		Runner runner = new Runner(System.getProperty('config_file_path'), System.getProperty('log4j.configuration'));
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
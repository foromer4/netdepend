import picscout.depend.dependency.main.Runner;
import picscout.depend.dependency.interfaces.ISolution;
public class Wrapper {
   
    
    public static void main(String[] args) {
    
        Runner runner = new Runner("c:\\temp\\config.xml", "c:\\temp\\log4jmain.xml");
        if (args.length == 0) {
            System.out.println("Going to run calc dependencies");
            runner.CalculateDependencies();
        }
        
        else {
            System.out.println("Going to run calc dependent solutions for:");
			for(String arg : args) {
				System.out.println(arg);
			}
			
			
            List<ISolution> result = runner.getSolutionsThatDependOnSolutionsByNames(Arrays.asList(args));
            for(ISolution sol : result) {
                System.out.println(sol.getName());                
            }
        }
    }
    }
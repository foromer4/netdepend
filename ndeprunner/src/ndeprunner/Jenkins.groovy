import picscout.depend.dependency.main.Runner;
import picscout.depend.dependency.interfaces.ISolution;

class Jenkins {

def static map = [     
       key_here: 'value_here.sln'       
]
                                    
    static void main(String... args) {      
    
    
        def repo = 'infrastructure' //args[0]
        def inputJobs = 'PicScout_Inf_MessagingFramework;PicScout_Inf_Messaging;' //args[1]
        
        println 'going to calc depenencies' + 'repo is:' + repo + 'jobs to calc for are: ' + inputJobs    
         def  inputsolutionNames  
        inputsolutionNames = parseJobs(inputJobs)  
         def dependentsolutionNames   
    dependentsolutionNames = getDependentSolutionNames(inputsolutionNames)
    
    
}

static parseJobs(inputJobs) {
  		def inputJobsList = inputJobs.split(';')
        def inputsolutionNames = ''
        for(job in inputJobsList) {
        inputsolutionNames = HandleJob(job, inputsolutionNames)
          println inputsolutionNames  
}
}

static geDependentSolutionNames(inputsolutionNames) {
 ArrayList<String>  dependentsolutionNames = new  ArrayList<String>()
 Runner runner = new Runner();
    List<ISolution> result = runner.getSolutionsThatDependOnSolutionsByNames(Arrays.asList(inputsolutionNames));  
            for(ISolution sol : result) {
                System.out.println(sol.getName());
               dependentsolutionNames.add(sol.getName())                
            }
           return dependentsolutionNames
            }



static  HandleJob(job, inputsolutionNames) {

if(map[job] != null) {
          job = map[job]
        }
        else{
        job = job.replace('_', '.')
        }       
       
        inputsolutionNames += job
        inputsolutionNames += ' '
        return inputsolutionNames
        }

}



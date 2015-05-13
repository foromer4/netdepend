import picscout.depend.dependency.main.Runner;
import picscout.depend.dependency.interfaces.ISolution;

class Jenkins {

def static map = [     
       key_here: 'value_here.sln'       
]
                                    
    static void main(String... args) {      
    
    
        def repo = 'infrastructure' //args[0]
        def inputJobs = 'PicScout_Inf_MessagingFramework;PicScout_Inf_Messaging;' //args[1]
        
        println 'going to calc depenencies.' + ' repo is:' + repo + ', jobs to calc for are: ' + inputJobs    
          ArrayList<String>  inputsolutionNames = new  ArrayList<String>()
         parseJobs(inputJobs, inputsolutionNames)  
         def dependentsolutionNames            
    dependentsolutionNames = geDependentSolutionNames(inputsolutionNames)
   
    
    
}

static parseJobs(inputJobs, inputsolutionNames) {
  		def inputJobsList = inputJobs.split(';')       
        for(job in inputJobsList) {
        HandleJob(job, inputsolutionNames)  
} 
return  inputsolutionNames  
}

static geDependentSolutionNames(inputsolutionNames) {
 ArrayList<String>  dependentsolutionNames = new  ArrayList<String>()
 Runner runner = new Runner();    
 println 'solutions to calc for are: ' + inputsolutionNames
    List<ISolution> result = runner.getSolutionsThatDependOnSolutionsByNames(inputsolutionNames); 
    println 'got results: ' + result     
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
       
        inputsolutionNames.add(job)            
        return inputsolutionNames
        }

}



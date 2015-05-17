import picscout.depend.dependency.main.Runner;
import picscout.depend.dependency.interfaces.ISolution;
import hudson.model.*
import hudson.AbortException
import hudson.console.HyperlinkNote
import java.util.concurrent.CancellationException




map = [     
       key_here: 'value_here.sln'       
]


// Get the out variable
def config = new HashMap()
bindings = getBinding()
config.putAll(bindings.getVariables())

out = config['out']

out.println 'running script to find dependencies and run jobs'
go(1)

out.println 'finished script to find dependencies and run jobs'






                                    
    def go(stam) {     
   
    def thr = Thread.currentThread()
	def build = thr?.executable
	

    
    def inputJobs = build.buildVariableResolver.resolve("JobsToRun")
   
    
     
     def envVarsMap = build.parent.builds[0].properties.get("envVars")
     println envVarsMap     
        
        println 'going to calc depenencies , jobs to calc for are: ' + inputJobs    
         ArrayList<String>  inputsolutionNames = new  ArrayList<String>()
         parseJobs(inputJobs, inputsolutionNames)  
         def dependentsolutionNames      
               
    dependentsolutionNames = getDependentSolutionNames(inputsolutionNames)
    runJenkinsJobs(dependentsolutionNames)
    
    
}



 def runJenkinsJobs(dependentsolutionNames) {

for(solutionName in dependentsolutionNames) {
	def jobName
   jobName = getJobName(solutionName)
   println 'suspected job to run is:' + jobName
   runJobInJenkins(jobName)
}
}



def runJobInJenkins(jobName) {

 def thr = Thread.currentThread()
	def build = thr?.executable

// Start another job
def branch = build.buildVariableResolver.resolve("BranchToBuild")
def job = Hudson.instance.getJob(jobName)
if( job == null) {
return
}
def anotherBuild
try {
    def params = [
      new StringParameterValue('IsCalledFromAnotherJob', true),
      new StringParameterValue('BranchToBuild', branch)
    ]
    def future = job.scheduleBuild2(0, new Cause.UpstreamCause(build), new ParametersAction(params))
    println "Waiting for the completion of " + HyperlinkNote.encodeTo('/' + job.url, job.fullDisplayName)
    anotherBuild = future.get()
} catch (CancellationException x) {
    throw new AbortException("${job.fullDisplayName} aborted.")
}
println HyperlinkNote.encodeTo('/' + anotherBuild.url, anotherBuild.fullDisplayName) + " completed. Result was " + anotherBuild.result

// Check that it succeeded
build.result = anotherBuild.result
if (anotherBuild.result != Result.SUCCESS && anotherBuild.result != Result.UNSTABLE) {
    // We abort this build right here and now.
    throw new AbortException("${anotherBuild.fullDisplayName} failed.")
}
}


 def getJobName(solutionName) {
for(s in map) {
    if(map[s] == solutionName) {    
       return s
    }    
}

if(solutionName.endsWith('.sln')) {
            solutionName = solutionName.substring(0, solutionName.length()-4)
        }
solutionName = solutionName.replace('.' , '_')
  return solutionName
}

 def parseJobs(inputJobs, inputsolutionNames) {
  		def inputJobsList = inputJobs.split(';')       
        for(job in inputJobsList) {
        HandleJob(job, inputsolutionNames)  
} 
return  inputsolutionNames  
}

 def getDependentSolutionNames(inputsolutionNames) {
 ArrayList<String>  dependentsolutionNames = new  ArrayList<String>()
 Runner runner = new Runner("C:/Scripts4Jenkins/netdepend/infrastructureconfig.xml");    
 println 'solutions to calc for are: ' + inputsolutionNames
    List<ISolution> result = runner.getSolutionsThatDependOnSolutionsByNames(inputsolutionNames); 
    println 'got ' + result.size() + ' results'     
            for(ISolution sol : result) {
                System.out.println(sol.getName());
               dependentsolutionNames.add(sol.getName())                
            }  
           return dependentsolutionNames
            }



  def HandleJob(job, inputsolutionNames) {

if(map[job] != null) {
          job = map[job]
        }
        else{
        job = job.replace('_', '.')
        }       
       
        inputsolutionNames.add(job)            
        return inputsolutionNames
        }





import com.picscout.depend.dependency.main.Runner;
import com.picscout.depend.dependency.interfaces.ISolution;
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
go()

out.println 'finished script to find dependencies and run jobs'


def go() {

	def thr = Thread.currentThread()
	def build = thr?.executable
	def inputJobs = build.buildVariableResolver.resolve("JobsToRun")
	def envVarsMap = build.parent.builds[0].properties.get("envVars")
	def configPath = envVarsMap['config_file_path']
	def log4jPath = envVarsMap['log4j.configuration']

	println 'config path is: ' + configPath + ', log4Path is: ' +  log4jPath
	println 'going to calc depenencies , jobs to calc for are: ' + inputJobs

	if(inputJobs == null || inputJobs.isEmpty()) {
		println 'no jobs to run'
		return
	}

	ArrayList<String>  inputsolutionNames = new  ArrayList<String>()
	parseJobs(inputJobs, inputsolutionNames)
	def dependentsolutionNames

	dependentsolutionNames = getDependentSolutionNames(inputsolutionNames,configPath, log4jPath )	
	jobs2Run = addInputJobs2JobsToRun(inputsolutionNames, dependentsolutionNames)
	runJenkinsJobs(jobs2Run)
}

def addInputJobs2JobsToRun(inputsolutionNames, dependentsolutionNames) {	
	ArrayList<String>  solutions2Run = new ArrayList<String> (dependentsolutionNames)
	for(inputSolution in inputsolutionNames ) {
		if(!solutions2Run.contains(inputSolution)) {
			println 'adding input solution:' + inputSolution + ' to the list of dependent solutions'
			solutions2Run.add(0, inputSolution)
		}
		else {
			println 'input solution: ' + inputSolution + ' is already in dependent solutions list'
		}
	}
	
	return solutions2Run
	
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
		println 'could not find job: ' + jobName
		return
	}
	def anotherBuild
	try {
		def params = [new StringParameterValue('IsCalledFromAnotherJob', 'true'), new StringParameterValue('BranchToBuild', branch)]
		def future = job.scheduleBuild2(0, new Cause.UpstreamCause(build), new ParametersAction(params))
		println "Waiting for the completion of " + HyperlinkNote.encodeTo('/' + job.url, job.fullDisplayName)
		anotherBuild = future.get()
	} catch (CancellationException x) {
		println 'failed to run job: ' + jobName + ' exception is: ' x
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

	for(s in map.keySet()) {
		if(map[s] != null && map[s].equalsIgnoreCase(solutionName)) {
			println 'solution ' + solutionName + ' is explicilty mapped to: ' + s
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

def getDependentSolutionNames(inputsolutionNames, configPath , log4jPath) {
	
	ArrayList<String>  dependentsolutionNames = new  ArrayList<String>()
	Runner runner = new Runner(configPath, log4jPath);
	println 'calculating dependencies'
	runner.CalculateDependencies()
	
	for(inputSolution in inputsolutionNames) {
		ArrayList<String>  tempNames = new  ArrayList<String>()
		tempNames.add(inputSolution)
		List<ISolution> result = runner.getSolutionsThatDependOnSolutionsByNames(tempNames);
		println 'for input solution: ' + inputSolution + ', got ' + result.size() + ' results ' 
		for(ISolution sol : result) {
			solutionName = sol.getName().toLowerCase()
			if(solutionName.endsWith('.sln')) {
				solutionName = solutionName.substring(0, solutionName.length()-4)
			}
			if(!dependentsolutionNames.contains(solutionName)) {
				println 'adding to dependent solution names :' + solutionName
				
				dependentsolutionNames.add(solutionName)
			}
			else {
				println 'dependent solution name :' + solutionName + ' already exists, will not add it'
			}
		}	
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

	inputsolutionNames.add(job.toLowerCase())
	return inputsolutionNames
}





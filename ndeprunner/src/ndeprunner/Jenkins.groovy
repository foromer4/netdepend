
class Jenkins {

def static map = [     
       key_here: 'value_here.sln'       
]
                                    
    static void main(String... args) {      
    
    
        def repo = 'infrastructure' //args[0]
        def inputJobs = 'PicScout_Inf_MessagingFramework;PicScout_Inf_Messaging;' //args[1]
        
        println 'going to calc depenencies'
        println 'repo is:' + repo
        println 'jobs to calc for are: ' + inputJobs
        def inputJobsList = inputJobs.split(';')
        def inputsolutionNames = ''
        for(job in inputJobsList) {
        inputsolutionNames = HandleJob(job, inputsolutionNames)
    }
    
    println inputsolutionNames
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
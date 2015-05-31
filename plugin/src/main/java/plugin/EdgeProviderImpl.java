package plugin;

import java.util.ArrayList;
import java.util.List;

import com.picscout.depend.dependency.interfaces.ISolution;
import com.picscout.depend.dependency.main.Runner;

import hudson.model.Item;
import hudson.model.AbstractProject;
import hudson.plugins.depgraph_view.model.graph.CopyArtifactEdge;
import hudson.plugins.depgraph_view.model.graph.Edge;
import hudson.plugins.depgraph_view.model.graph.EdgeProvider;
import hudson.plugins.depgraph_view.model.graph.ProjectNode;
import jenkins.model.Jenkins;

public class EdgeProviderImpl implements EdgeProvider {

	public Iterable<Edge> getEdgesIncidentWith(AbstractProject<?, ?> project) {

		List<ISolution> dependentSolutions = getDependentSolutions(project);

		return getEdges(project, dependentSolutions);
	}

	private List<ISolution> getDependentSolutions(AbstractProject<?, ?> project) {
		String projectNAme = project.getName();

		Runner runner = new Runner(
				"c:\\Scripts4Jenkins\\netdepend\\infrastructureconfig.xml",
				"c:\\Scripts4Jenkins\\netdepend\\log4j2.xml");
		runner.calculateDependencies();

		ArrayList<String> inputJobNames = new ArrayList<String>();
		inputJobNames.add(jobName2SolutionName(projectNAme));
		List<ISolution> result = runner
				.getSolutionsThatDependOnSolutionsByNames(inputJobNames);
		return result;
	}
	
	
	

	private Iterable<Edge> getEdges(AbstractProject<?, ?> inputProject, List<ISolution> solutions) {

		ArrayList<Edge> edges = new ArrayList<Edge>();
		AbstractProject<?, ?> sourceProject = inputProject;
		Jenkins jenkins = Jenkins.getInstance();
		for(ISolution solution : solutions) {
			Item item = jenkins.getItemByFullName(solutionName2JobName(solution.getName()));
			if (item instanceof AbstractProject<?, ?>) {				
				AbstractProject<?, ?> targetProject = (AbstractProject<?, ?>)(item);
				Edge edge = createEdge(sourceProject, targetProject);				
				edges.add(edge);
				sourceProject = targetProject;				
			}
		}
		return edges;
	}

	private Edge createEdge(AbstractProject<?, ?> lastProject,
			AbstractProject<?, ?> abstractProject) {
		ProjectNode source = new ProjectNode(lastProject);
		ProjectNode target = new ProjectNode(abstractProject);
		CopyArtifactEdge edge = new CopyArtifactEdge(source, target);
		return edge;
	}

	public String jobName2SolutionName(String jobName) {
		return jobName.replace("_", ".").toLowerCase() + ".sln";
	}

	public String solutionName2JobName(String solutionName) {
		if (solutionName.endsWith(".sln")) {
			solutionName = solutionName.substring(0, solutionName.length() - 4);
		}
		return solutionName.replace(".", "_").toLowerCase();
	}

}

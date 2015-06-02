package com.picscout.netdepend.netdepend_graph;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.picscout.depend.dependency.interfaces.ISolution;
import com.picscout.depend.dependency.main.Runner;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.AbstractProject;
import hudson.plugins.depgraph_view.model.graph.CopyArtifactEdge;
import hudson.plugins.depgraph_view.model.graph.Edge;
import hudson.plugins.depgraph_view.model.graph.EdgeProvider;
import hudson.plugins.depgraph_view.model.graph.ProjectNode;
import jenkins.model.Jenkins;

@Extension
public class EdgeProviderImpl implements EdgeProvider {

	private final static Logger LOG = Logger.getLogger(EdgeProviderImpl.class
			.getName());


	public Iterable<Edge> getEdgesIncidentWith(AbstractProject<?, ?> project) {

		try {

			LOG.info("Going to calc dependencies for job: " + project.getName());
			List<ISolution> dependentSolutions = getDependentSolutions(project);

			return getEdges(project, dependentSolutions);
		} catch (Exception ex) {
			LOG.error("Failed to run dependency plugin", ex);
			return null;
		}
	}

	private static List<ISolution> getDependentSolutions(AbstractProject<?, ?> project) {
		String projectNAme = project.getName();

		Runner runner = new Runner(
				"c:\\Scripts4Jenkins\\netdepend\\infrastructureconfig.xml",
				"c:\\Scripts4Jenkins\\netdepend\\log4j2.xml");

		ArrayList<String> inputJobNames = new ArrayList<String>();
		inputJobNames.add(jobName2SolutionName(projectNAme));
		List<ISolution> result = runner
				.getSolutionsThatDependOnSolutionsByNames(inputJobNames);

		LOG.info("Dependent solutiona are: ");
		for (ISolution solution : result) {
			LOG.info(solution.getName());
		}
		return result;
	}

	private static Iterable<Edge> getEdges(AbstractProject<?, ?> inputProject,
			List<ISolution> solutions) {

		ArrayList<Edge> edges = new ArrayList<Edge>();
		AbstractProject<?, ?> sourceProject = inputProject;
		Jenkins jenkins = Jenkins.getInstance();
		for (ISolution solution : solutions) {
			String jobName = solutionName2JobName(solution.getName());
			Item item = jenkins.getItemByFullName(jobName);
			if (item instanceof AbstractProject<?, ?>) {
				AbstractProject<?, ?> targetProject = (AbstractProject<?, ?>) (item);
				Edge edge = createEdge(sourceProject, targetProject);
				edges.add(edge);
				LOG.info("Adding edge between job: " + sourceProject + " and: "
						+ targetProject);
				sourceProject = targetProject;

			} else {
				LOG.info("Failed to find job by name: " + jobName);
			}
		}
		return edges;
	}

	private  static Edge createEdge(AbstractProject<?, ?> lastProject,
			AbstractProject<?, ?> abstractProject) {
		ProjectNode source = new ProjectNode(lastProject);
		ProjectNode target = new ProjectNode(abstractProject);
		CopyArtifactEdge edge = new CopyArtifactEdge(source, target);
		return edge;
	}

	public static String jobName2SolutionName(String jobName) {
		return jobName.replace("_", ".").toLowerCase() + ".sln";
	}

	public static String solutionName2JobName(String solutionName) {
		if (solutionName.endsWith(".sln")) {
			solutionName = solutionName.substring(0, solutionName.length() - 4);
		}
		return solutionName.replace(".", "_").toLowerCase();
	}

}

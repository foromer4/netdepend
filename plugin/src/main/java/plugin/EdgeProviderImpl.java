package plugin;

import hudson.model.AbstractProject;
import hudson.plugins.depgraph_view.model.graph.Edge;
import hudson.plugins.depgraph_view.model.graph.EdgeProvider;


public class EdgeProviderImpl implements EdgeProvider {

	public Iterable<Edge> getEdgesIncidentWith(AbstractProject<?, ?> project) {
		
		String projectNAme = project.getName();
		
		// TODO Auto-generated method stub
				return null;
	} 

	
	

}

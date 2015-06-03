package com.picscout.netdepend.netdepend_graph;

import hudson.plugins.depgraph_view.model.graph.Edge;
import hudson.plugins.depgraph_view.model.graph.ProjectNode;

public class NetEdge extends Edge {

	public NetEdge(ProjectNode source, ProjectNode target) {
		super(source, target);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "dep";
	}

}

package com.picscout.netdepend.netdepend_graph;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import hudson.Extension;
import hudson.plugins.depgraph_view.model.graph.EdgeProvider;

@Extension
public class NetDependModule extends AbstractModule {

	@Override
	protected void configure() {
		 Multibinder<EdgeProvider> edgeProviderMultibinder = Multibinder.newSetBinder(binder(), EdgeProvider.class);
		 edgeProviderMultibinder.addBinding().to(EdgeProviderImpl.class);
	}

}

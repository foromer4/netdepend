package com.picscout.netdepend.netdepend_graph;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.multibindings.MapBinderBinding;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.MultibinderBinding;
import com.google.inject.multibindings.MultibindingsTargetVisitor;
import com.google.inject.spi.DefaultBindingTargetVisitor;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;

import hudson.Extension;
import hudson.plugins.depgraph_view.model.graph.EdgeProvider;

@Extension
public class NetDependModule extends AbstractModule {

	private final static Logger LOG = Logger.getLogger(NetDependModule.class
			.getName());

	@Override
	protected void configure() {

		LOG.info("NetDependModule configure was called. " + Thread.currentThread().getStackTrace());
		Multibinder<EdgeProvider> edgeProviderMultibinder = Multibinder
				.newSetBinder(binder(), EdgeProvider.class);
		edgeProviderMultibinder.addBinding().to(EdgeProviderImpl.class);
	}

}


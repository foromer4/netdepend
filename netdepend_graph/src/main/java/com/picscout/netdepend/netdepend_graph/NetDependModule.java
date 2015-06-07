package com.picscout.netdepend.netdepend_graph;

import java.security.spec.ECGenParameterSpec;

import org.apache.log4j.Logger;
import org.apache.xerces.dom.ElementDefinitionImpl;
import org.bouncycastle.operator.InputExpanderProvider;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.multibindings.MapBinderBinding;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.MultibinderBinding;
import com.google.inject.multibindings.MultibindingsTargetVisitor;
import com.google.inject.spi.DefaultBindingTargetVisitor;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.util.Modules;
import com.picscout.depend.dependency.classes.ProjectBuilder;
import com.picscout.depend.dependency.interfaces.IProjectBuilder;

import hudson.Extension;
import hudson.plugins.depgraph_view.model.graph.DependencyGraphEdgeProvider;
import hudson.plugins.depgraph_view.model.graph.DependencyGraphModule;
import hudson.plugins.depgraph_view.model.graph.EdgeProvider;

/**
 * Module used to inject new edge provider. still not usable, see: {@link:
 * https://issues.jenkins-ci.org/browse/JENKINS-28773?jql=project%20%3D%20
 * JENKINS
 * %20AND%20status%20in%20(Open%2C%20%22In%20Progress%22%2C%20Reopened)%20
 * AND%20component%20%3D%20%27depgraph-view-plugin%27}
 * 
 * @author OSchliefer
 *
 */
@Extension
public class NetDependModule extends AbstractModule {

	private final static Logger LOG = Logger.getLogger(NetDependModule.class
			.getName());

	public NetDependModule() {
		LOG.info("NetDependModule override was called. "
				+ Thread.currentThread().getStackTrace());

	}

	@Override
	protected void configure() {

		LOG.info("NetDependModule configure was called. "
				+ Thread.currentThread().getStackTrace());
		Multibinder<EdgeProvider> edgeProviderMultibinder = Multibinder
				.newSetBinder(binder(), EdgeProvider.class);
		edgeProviderMultibinder.addBinding().to(EdgeProviderImpl.class);
	}

}

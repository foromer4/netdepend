package com.picscout.depend.dependency.main;
import com.picscout.depend.dependency.classes.MapBuilder;
import com.picscout.depend.dependency.classes.ProjectBuilder;
import com.picscout.depend.dependency.classes.ProjectDependencyMapper;
import com.picscout.depend.dependency.classes.ProjectStore;
import com.picscout.depend.dependency.classes.SolutionBuilder;
import com.picscout.depend.dependency.classes.SolutionMapper;
import com.picscout.depend.dependency.classes.StatePersist;
import com.picscout.depend.dependency.interfaces.IMapBuilder;
import com.picscout.depend.dependency.interfaces.IProjectBuilder;
import com.picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import com.picscout.depend.dependency.interfaces.IProjectStore;
import com.picscout.depend.dependency.interfaces.ISolutionBuilder;
import com.picscout.depend.dependency.interfaces.ISolutionMapper;
import com.picscout.depend.dependency.interfaces.IStatePersist;
import com.google.inject.AbstractModule;


/**
 * This is the guice injector, it itself is injected via reflection.
 * @author OSchliefer
 *
 */
public class AppInjector extends AbstractModule{
	 @Override
	    protected void configure() {	       
		 bind (IProjectBuilder.class).to(ProjectBuilder.class);
		 bind (ISolutionBuilder.class).to(SolutionBuilder.class);
		 bind(IProjectStore.class).to(ProjectStore.class);
		 bind(IProjectDependencyMapper.class).to(ProjectDependencyMapper.class);		
		 bind(ISolutionMapper.class).to(SolutionMapper.class);
		 bind(IMapBuilder.class).to(MapBuilder.class);
		 bind(IStatePersist.class).to(StatePersist.class);    
	    }
}

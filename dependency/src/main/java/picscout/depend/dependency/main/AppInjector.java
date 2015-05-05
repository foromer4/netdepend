package picscout.depend.dependency.main;
import picscout.depend.dependency.classes.MapBuilder;
import picscout.depend.dependency.classes.ProjectBuilder;
import picscout.depend.dependency.classes.ProjectDependencyMapper;
import picscout.depend.dependency.classes.ProjectStore;
import picscout.depend.dependency.classes.SolutionBuilder;
import picscout.depend.dependency.classes.SolutionMapper;
import picscout.depend.dependency.classes.StatePersist;
import picscout.depend.dependency.interfaces.IMapBuilder;
import picscout.depend.dependency.interfaces.IProjectBuilder;
import picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import picscout.depend.dependency.interfaces.IProjectStore;
import picscout.depend.dependency.interfaces.ISolutionBuilder;
import picscout.depend.dependency.interfaces.ISolutionMapper;
import picscout.depend.dependency.interfaces.IStatePersist;

import com.google.inject.AbstractModule;
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

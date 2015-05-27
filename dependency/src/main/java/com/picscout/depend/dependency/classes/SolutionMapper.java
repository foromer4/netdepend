package com.picscout.depend.dependency.classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.picscout.depend.dependency.interfaces.IProjectDependencyMapper;
import com.picscout.depend.dependency.interfaces.IProjectDescriptor;
import com.picscout.depend.dependency.interfaces.ISolution;
import com.picscout.depend.dependency.interfaces.ISolutionMapper;
import javax.inject.Singleton;
import javax.inject.Inject;


/**
 * Map solution to all projects in it. Also knows given a changed project to
 * tell which solutions should be built by order.
 * @see ISolutionMapper
 * @author OSchliefer
 *
 */
@Singleton
public class SolutionMapper implements ISolutionMapper {

	private HashSet<ISolution> map;
	private IProjectDependencyMapper projectMapper;

	@Inject
	public SolutionMapper(IProjectDependencyMapper projectMapper) {
		map = new HashSet<ISolution>();
		this.projectMapper = projectMapper;
	}	

	public void add(ISolution solution) {
		map.add(solution);
	}

	/**
	 * Get all solutions that should be built as a result of project change,
	 * either because this project is in them, or because a dependent project is
	 * in them. The result is by build order.
	 * 
	 * @param projectDescriptors project to get dependent solutions for
	 * @return dependent solutions
	 */
	public List<ISolution> getSolutionsByProjects(
			List<IProjectDescriptor> projectDescriptors) {
		List<ISolution> reuslt = new ArrayList<ISolution>();
		for (IProjectDescriptor projectDescriptor : projectDescriptors) {
			List<IProjectDescriptor> chain = projectMapper
					.getProjectsThatDepeantOn(projectDescriptor);
			fillSolutionByProject(projectDescriptor, reuslt);
			if (chain != null) {
				for (IProjectDescriptor descriptor : chain) {
					fillSolutionByProject(descriptor, reuslt);
				}
			}
		}
		return reuslt;
	}

	public List<ISolution> getSolutionsBySolutionsNames(List<String> names) {
		List<ISolution> changedSolutions = new ArrayList<ISolution>();
		List<IProjectDescriptor> projectDescriptors = new ArrayList<IProjectDescriptor>();
		for (String name : names) {
			if(!name.endsWith(".sln")) {
				name += ".sln";
			}
			for (ISolution solution : map) {
				if (solution.getName().equalsIgnoreCase(name)) {
					changedSolutions.add(solution);
					for (IProjectDescriptor descriptor : solution
							.getProjectsDescriptors()) {
						if (!projectDescriptors.contains(descriptor)) {
							projectDescriptors.add(descriptor);
						}
					}
				}
			}
		}
		List<ISolution> allDependentSolutionos = getSolutionsByProjects(projectDescriptors);
		allDependentSolutionos.removeAll(changedSolutions);
		return allDependentSolutionos;
	}

	private void fillSolutionByProject(IProjectDescriptor projectDescriptor,
			List<ISolution> reuslt) {
		for (ISolution solution : map) {
			List<IProjectDescriptor> projectsInSolution = solution
					.getProjectsDescriptors();
			if (projectsInSolution.contains(projectDescriptor)) {
				if (!reuslt.contains(solution)) {
					reuslt.add(solution);
				}
			}
		}
	}

}

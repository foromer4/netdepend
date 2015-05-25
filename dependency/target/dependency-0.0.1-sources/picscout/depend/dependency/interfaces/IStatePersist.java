package picscout.depend.dependency.interfaces;

/**
 * Persist or load persisted state
 * @author OSchliefer
 *
 */
public interface IStatePersist {
	
	
	/**
	 * Persist state of map builder.
	 * @param builderState state to save
	 */
	public void persist(IMapBuilder builderState);
	
	/**
	 * Load map builder from persisted resource
	 * @return map builder loaded from state 
	 */
	public IMapBuilder load();
	

}

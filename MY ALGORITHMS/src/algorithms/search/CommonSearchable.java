package algorithms.search;

import java.util.ArrayList;
/**
 * defines the searching problem,domain dependent.
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-11-15
 * @param <T> The type of the internal representation of state
 */
public abstract class CommonSearchable<T> implements Searchable<T> {
	/**
	 * {@inheritDoc}
	 */
	public abstract State<T> getStartState();
	/**
	 * {@inheritDoc}
	 */
	public abstract State<T>  getGoalState();
	/**
	 * {@inheritDoc}
	 */
	public abstract ArrayList<State<T>> getAllPossibleStates(State<T> s);
    
	/**
	 * {@inheritDoc}
	 */
	@Override
    public abstract double getPassageCost(State<T> a, State<T> b);
}

package algorithms.search;


/**
 * Defines the heuristics of Astar
* @author Chen Zissu
 * @version 1.0
 * @since 2015-11-15
 * @param T any kind of parameter.
 */
public interface Heuristic<T> 
{
	/**
	 * calculating the estimated cost between two states depends on each heuristics
	 * @param currState the state we calculate from
	 * @param goalState the state we calculate till this one
	 * @return cost
	 */
	public double h(State<T> currState,State<T> goalState);
}

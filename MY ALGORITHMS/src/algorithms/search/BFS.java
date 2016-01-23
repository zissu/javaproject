package algorithms.search;

/**
 * Adding the features of the basic BFS
* @author Chen Zissu
 * @version 1.0
 * @since 2015-11-15
 * @param <T> The type of the internal representation of state
 */

public class BFS<T> extends CommonBFS<T> {
	/**
	 * calculate the cost between two states in BFS algorithm
	 */
	@Override
	public double calculateCost(State<T> currState, State<T> neighbor, Searchable<T> s) {
		
		return g(currState,neighbor,s);
	}

	
	
}

package view;

import java.util.Observable;
/**
 * abstract class that the defines the view functions and data members
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-28
 */
public abstract class CommonView extends Observable implements View {
	/**
	 * {@inheritDoc}
	 */
	public abstract void start();
	
	
}

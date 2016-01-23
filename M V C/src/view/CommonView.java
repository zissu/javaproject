package view;

import controller.Controller;
/**
 * abstract class which has the common methods and data members of all view
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-16
 */
public abstract class CommonView implements View 
{
	Controller c;
	/**
	 * constructor using fields
	 * @param c the controller of this view
	 */
	public CommonView(Controller c) {
		super();
		this.c = c;
	}

}
package model;



import controller.Controller;
/**
 * abstract class which has the common methods and data members of all models
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-16
 */
public abstract class CommonModel implements Model 
{
	Controller c;
	/**
	 * c'tor using fields
	 * @param c the model's controller we use.all the calculation passes to this controller
	 */
	public CommonModel(Controller c) {
		super();
		this.c = c;
	}

}
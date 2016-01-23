package controller;

import java.util.HashMap;

import model.Model;
import view.View;
/**
 * abstract class which has the common methods and data members of all controllers
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-16
 */
public abstract class CommonController implements Controller 
{
	/**
	 * verials
	 */	
	Model m;
	View v;
	HashMap<String, Command> stringToCommand;
	
	/**
	 * C'tor
	 */
	public CommonController() 
	{
		super();
		
		stringToCommand=new HashMap<String, Command>();
		initCommands();
		
		
		stringToCommand.put("exit", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				
				m.handleExitCommand(args);
			}
		});
		
		
	}
	
	
	/**
	 * sets the model of the controller,to whom it passes the command to calculate
	 */
	public void setM(Model m) {
		this.m = m;
	}

	
	/**
	 * sets the view of the controller,to whom it passes the command to be displayed
	 */
	public void setV(View v) {
		this.v = v;
		v.setStringToCommand(stringToCommand);
	}
	
	/**
	 * initialize the commands
	 */
	protected abstract void initCommands();

}
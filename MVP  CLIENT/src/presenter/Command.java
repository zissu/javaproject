package presenter;
/**
 * interface that define the functionality of the commands
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-28
 */
public interface Command 
{
	
	 /***
	  * define what command must do
	  * @param args array of strings,defines the parameters of the command
	  */
	public void doCommand(String[] args);

}

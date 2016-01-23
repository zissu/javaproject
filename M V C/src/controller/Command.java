
package controller;
/**
 * defines the command interface. Each command must implement doCommand
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-16
 */
public interface Command 
{
	/**
	 * defines what each command does.
	 * @param str string array of parameters
	 */
	public void doCommand(String[] str);
}
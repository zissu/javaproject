package model;
/**
 * 
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-28
 */
public interface Model 
{
	
	/**
	 * open the server
	 */
	public void openTheServer();
	
	/**
	 * close the server
	 */
	public void closeServer();
	
	/**
	 * return the code: connect
	 * @return a string with the server is open
	 */
	public String getOpenCode();
	
	/**
	 * return the code: disconnect
	 * @return a string with the server is closed
	 */
	public String getCloseCode();
	
	/**
	 * return the messages that come from the server
	 * @return a string with the message
	 */
	public String getMessageCode();
}

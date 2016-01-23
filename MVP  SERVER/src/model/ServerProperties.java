package model;

import java.io.Serializable;
/**
 * holds the properties of the server
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-28
 */
public class ServerProperties implements Serializable 
{

	/**
	 * serial number for serialization
	 */
	private static final long serialVersionUID = 1722398377892436497L;
	
	//Variables 
	protected int numberOfClients;
	protected int port;
	
	/**
	 * c'tor
	 */
	public ServerProperties() {
		super();
	}
	
	/**
	 * constructor using fields
	 * @param numberOfClients the number Of Clients connected to the server
	 * @param port the port of the server
	 */
	public ServerProperties(int numberOfClients,int port)
	{
		this.numberOfClients=numberOfClients;
		this.port=port;
	}
	
	
	/**
	 * return number of clients
	 * @return number of clients
	 */
	public int getNumberOfClients() {
		return numberOfClients;
	}
	
	/**
	 * set number of clients
	 * @param numberOfClients set number of clients
	 */
	public void setNumberOfClients(int numberOfClients) {
		this.numberOfClients = numberOfClients;
	}
	
	/**
	 * return the port of the server
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * setting port of the server
	 * @param port set the port of the server
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	

}

package model;

import java.io.InputStream;
import java.io.OutputStream;
/**
 * interface that define client handle
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-28
 */
public interface ClientHandler 
{
	/**
	 * handling a one client in a specific way
	 * @param inFromClient input stream from client
	 * @param outToClient output stream to the client
	 */
	void handleClient(InputStream inFromClient, OutputStream outToClient);
	
	/**
	 * ending the connection with the client
	 */
	public void close();
}

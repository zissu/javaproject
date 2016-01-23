package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * calculating the command and return it to the view
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-28
 */


public class MyModel extends CommonModel 
{	
	
	//variables 
	int port; //5400
	ServerSocket server; //the server
	ClientHandler clinetHandler; //clinetHandler object (for the strategy pattern)
	int numOfClients; //num of clients connected to the server
	ExecutorService threadPool; 
	volatile boolean stop; //flag for the while loop
	Thread mainServerThread;
	int clientsHandled; //num of clients we've already handle
	
	
	
	
	String openCode;
	String closeCode;
	String messageCode;
	
	/**
	 * constructor the model
	 */
	public MyModel() 
	{
		//deciding which client handler to work with
		this.clinetHandler=new MazeSolutionClientHandler();
		
		//taking the properties from xml file(number of clients,port number = 5400)
		File f=new File("./resources/serverProperties.xml");
		if(!f.exists())//if we didn't exit the file
		{
			XMLEncoder xmlE;
			try {
				xmlE = new XMLEncoder(new FileOutputStream("./resources/serverProperties.xml"));
				xmlE.writeObject(new ServerProperties(5, 5400));//5 clients and the port is 5400
				xmlE.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		XMLDecoder xmlD;
		ServerProperties serverProperties;//serverProperties object
		try 
		{
			xmlD = new XMLDecoder(new FileInputStream("./resources/serverProperties.xml"));
			serverProperties=(ServerProperties)xmlD.readObject();
			xmlD.close();
			//the num of threads in our threadpool is fix we won't like to use all of the computer's
			//process'
			this.threadPool = Executors.newFixedThreadPool(serverProperties.getNumberOfClients());
			this.port=serverProperties.getPort();//5400
			this.server=new ServerSocket(this.port);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		
		this.clientsHandled=0;//until a client will connect the num of client handled = 0
		this.stop=false;
		
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void openTheServer()
	{
		
		try 
		{

			server.setSoTimeout(10*1000);// Time out of 1 minute makes  the server wait for acception
			//this why we won't wait forever for the client to response
		}
		catch (SocketException e1) 
		{
			e1.printStackTrace();
		}
		
		
		//init thread
		mainServerThread=new Thread(new Runnable() {			
			@Override
			public void run() {
				while(!stop)// stopping when client press stop in main..parallel
				{
					try {
						//Listens for a connection to be made to this socket and accepts it.
						//The method blocks until a connection is made. 
						final Socket someClient=server.accept();
						
						
						//update the observer
						messageCode=someClient.toString();
						setChanged();
						notifyObservers("pass messages");
						
						//Listens for a connection to be made to this socket and accepts it.
						if(someClient!=null)
						{
							//we'll enter it to a new thread in order to listen for some clients
							threadPool.execute(new Runnable() {									
								@Override
								public void run() {
									try{	

										clientsHandled++;//add a new client
										
										messageCode="\thandling client "+clientsHandled;//print what is the corrent client handled
										//update observer
										setChanged();
										notifyObservers("pass messages");
										

										//sending the task to another class,
										//because we want our code to handle different tasks,and be generic
										clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										//closing the socket
										someClient.close();
										
										//print what is the corrent client handled
										messageCode="\tdone handling client "+clientsHandled;
										//update observer
										setChanged();
										notifyObservers("pass messages");
										
										
									}catch(IOException e){
										e.printStackTrace();
									}									
								}
							});								
						}
					}
					catch (SocketTimeoutException e){
						//if minute passed and no client is connected,print:
						
						messageCode="no clinet connected...";
						//update observer
						setChanged();
						notifyObservers("pass messages");
						
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				messageCode="done accepting new clients.";
				//update observer
				setChanged();
				notifyObservers("pass messages");
				
				
			} // end of the mainServerThread task
		});
		
		mainServerThread.start();
		openCode="server is opened";
		//update observer for opening the server
		setChanged();
		notifyObservers("open the server");
		
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void closeServer()
	{
		stop=true;
		
		clinetHandler.close();
		try 
		{
			// do not execute jobs in queue, continue to execute running threads
			messageCode="shutting down";
			//update observer for closing the server
			setChanged();
			notifyObservers("pass messages");
			
			
			//close the threatpool, no new tasks will be accepted
			//we're not waiting for previously submitted tasks to completed
			threadPool.shutdown();
			// wait 10 seconds over and over again until all running jobs have finished
			boolean allTasksCompleted=false;
			while(!(allTasksCompleted=threadPool.awaitTermination(10, TimeUnit.SECONDS)));
			//not busy waiting,because not asking each time again and again
			
			messageCode="all the tasks have finished";
			//update the observer that there are no more tasks in the thread pool
			setChanged();
			notifyObservers("pass messages");
			
			
			//if we stack on the minute of acception, and stop=true, closing to the server
			//will cause a problem,so we need to close the thread
			if(mainServerThread!=null)
			{
				//Waits for this thread to die
				mainServerThread.join();		
				
				messageCode="main server thread is done";
				//update the observer
				setChanged();
				notifyObservers("pass messages");
				
			}
			
			//closing the server
			server.close();
			
			messageCode="server is safely closed";
			//update the observer the server is closed
			setChanged();
			notifyObservers("pass messages");
			
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		closeCode="server shut down has finished";
		//update the observer
		setChanged();
		notifyObservers("close the server");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCloseCode() {
		return closeCode;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOpenCode() 
	{
		return openCode;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getMessageCode() {
		return messageCode;
	}


}

package parsers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import flight_system.Flight;
import flight_system.FlightLeg;

/** 
 * Creates a Putter that puts the XML information to the server.
 * <p>
 * This class uses the Singleton Pattern, thus, only one instance of it is allowed
 * All the information that we need on the server get from here.
 * 
 * @author Kun Huang
 * */
public class XMLPutter {
	private String teamName = "TeamYeYing"; // Team Name
	private int numXML; // Number of XMLs 
	private String urlAddress = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";
	private int ticketsBought;
	
	private static XMLPutter firstInstance = null;
	
	
	/* The private constructor */
	private XMLPutter(){};
	
	/**
	 * Get the only instance of this class
	 * @return the only instance 
	 */
	/* Method to get the only instance of the class */
	public static XMLPutter getInstance(){
		if(firstInstance == null){
			firstInstance = new XMLPutter();
		}

		return firstInstance;
	}
	
	/**
	 * Get the team name of our team	
	 * @return the team name "TeamYeYing"
	 */
	/* Getter Functions*/
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * Get the number of XML file that already got from the server
	 * @return the number of XML file
	 */
	public int getNumXML() {
		return numXML;
	}
	
	public int getTicketsBought() {
		return ticketsBought;
	}	
	
	/**Lock the Database
	 * 
	 * @return true if lock the DB successful else false
	 */
	public boolean lockDB(){
		URL url;
		HttpURLConnection connection;
		
		try{
			/* Open Connection and send POST request */
			url = new URL(urlAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			/* Give the lockDB action to the sever*/
			DataOutputStream writer=new DataOutputStream(connection.getOutputStream());
			writer.writeBytes("team="+teamName+"&action=lockDB");
			writer.flush();
			writer.close();
			
			/* Print POST to show the connection is begin*/
			int responseCode=connection.getResponseCode();
//			System.out.println("\nSending 'POST' to lock database");
//			System.out.println("\nResponse Code:"+ responseCode);
			
			/*if the connection was success*/
			if((responseCode>=200)&&(responseCode<=299)){
//				System.out.println("Locked the Database successfully!");
				BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response=new StringBuffer();
				
				while((line=in.readLine())!=null){
					response.append(line);
				}
				in.close();
				
				System.out.println(response.toString());
				return true;
			}
		}
		/*catch the exception*/
		catch(IOException ex){
			ex.printStackTrace();
			return false;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * Unlock the Database
	 * @return true if  unlock DB successful else false
	 */
	public boolean unlockDB(){
		URL url;
		HttpURLConnection connection;
		
		try{
			/* Open Connection and send POST request */
			url = new URL(urlAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			DataOutputStream writer=new DataOutputStream(connection.getOutputStream());
			writer.writeBytes("team="+teamName+"&action=unlockDB");
			writer.flush();
			writer.close();
			
			/* The response code given by the server*/
			int responseCode=connection.getResponseCode();
//			System.out.println("\nSending 'POST' to unlock database");
//			System.out.println("\nResponse Code:"+ responseCode);
			
			/* if the connection was successful*/
			if((responseCode>=200)&&(responseCode<=299)){
//				System.out.println("Unlocked the Database successfully!");
				BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response=new StringBuffer();
				
				while((line=in.readLine())!=null){
					response.append(line);
				}
				in.close();
				
				System.out.println(response.toString());
				return true;
			}
		}
		
		/*catch the exceptions*/
		catch(IOException ex){
			ex.printStackTrace();
			return false;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * Makes an XML representation of a ticket that is used
	 * to purchase a flight from the database.
	 * @param flight the flight that will be purchased
	 * @param isFirstClass true if you want the 1st Class ticket
	 * 		  for that flight. False if you want the Coach class.
	 * @return the XML String representation of the ticket.
	 */
	/* Make a ticket with the fight number */
	public String makeTicket(Flight flight, boolean isFirstClass){
		
		/* Flight String looks like this. */
		/*		 
		<Flights>
		   <Flight number="1781" seating="FirstClass" />
		   <Flight number="1782" seating="FirstClass" />
		</Flights>
			
		/* The ticket XML */
		String ticket = "<Flights>";
		
		for (FlightLeg fLeg : flight.getFlightList()){
			
			/* holds either First Class or Coach String*/
			String seat; 
			
			/* Set the seat String */
			if (isFirstClass) {
				seat = "FirstClass";
			}
			else{
				seat = "Coach";
			}
			
			ticket = ticket + "<Flight number="+"\""+ fLeg.getFlightNum() +"\""+" seating="+"\""+seat+"\""+" />";
		}
		
		/* Closes the XML tag */
		ticket = ticket + "</Flights>";
		
		return ticket;
	}
	
	/**
	 * Purchases a ticket from the flight database.
	 * @param ticket the ticket for the flight that will be purchased.
	 * @return a map of the response. "Successful, Response Code".
	 * <p>
	 * If the ticket was successfully bought, then you will get "true, 200".
	 * <p>
	 * Else you will get "false, response code".
	 */
	/* Buying a ticket */
	public int buyTicket(String ticket){
		URL url;
		HttpURLConnection connection;
		
		try{
			url = new URL(urlAddress);
			
			/* Open Connection and send POST request */
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes("team="+teamName+"&action=buyTickets&flightData="+ticket);
			writer.flush();
			writer.close();
			
			/* The response code given by the server*/
			int responseCode=connection.getResponseCode();
			//System.out.println("\nResponse Code: "+ responseCode);
			
			/* If the connection was successful*/
			if((responseCode>=200)&&(responseCode<=299)){
//				System.out.println("The ticket was bought.");
				BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response=new StringBuffer();
				
				while((line=in.readLine())!=null){
					response.append(line);
				}
				in.close();
				
				System.out.println(response.toString());
				
				ticketsBought++;
				
				/* Successful */ 
				return responseCode;
			}
			/* Else the response was not valid */
			else if (responseCode == 304){
				//System.out.println("Unsuccessful: Did not update the Database.");
				return responseCode;
			}
			else if (responseCode == 400){
				//System.out.println("Missing or Invalid action");
				return responseCode;
			}
			else if (responseCode == 412){
				//System.out.println("Unsuccessful: Our team did not have the lock.");
				return responseCode;
			}
			else{
				//System.out.println("Unknown connection error");
				return responseCode;
			}
		}
		
		/* Catch the exception*/
		catch(IOException ex){
			ex.printStackTrace();
			return 500;
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			return 500;
		}
		
	}

	/**
	 * String representation of the XMLPutter object.
	 * <p>
	 * @return the string representation of this XMLPutter. 
	 */

	@Override
	public String toString() {
		return "This is an XMLPutter that has the team name: " + teamName + " and it has bought " + ticketsBought + " ticket(s).";
	}
}

/**
 * Originally written by: Jianan 
 * Modified by: Richard Walker
 * Original date: March 15. 2015
 * Modified date: March 15. 2015
 * 
 */

package parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import flight_system.Airport;
import flight_system.Date;
import flight_system.Location;
import flight_system.Month;

/** 
 * Creates a Getter that get the XML information form the server.
 * <p>
 * This class uses the Singleton Pattern, thus, only one instance of it is allowed
 * All the information that we need on the server get from here.
 * 
 * @author Kun Huang
 * */
public class XMLGetter {
	private String teamName = "TeamYeYing"; // Team Name
	private int numXML; // Number of XMLs 
	private String urlAddress = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";
	
	private static XMLGetter firstInstance = null;
	
	/**
	 * Make an object that can get XML data from the server
	
	 */
	/* The private constructor */
	private XMLGetter(){};
	
	/**
	 * Get the only instance of this class
	 * @return the only instance 
	 */
	/* Method to get the only instance of the class */
	public static XMLGetter getInstance(){
		if(firstInstance == null){
			firstInstance = new XMLGetter();
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
	
	/**
	 * An method that can get all the XML information about airport from the server.
	 * @return the XML information about airport on the server.
	 */
	/* Returns the XML for the Airports */
	public String getAirportsXML (){
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
		try{
			url = new URL(urlAddress + "?team="+teamName+"&action=list&list_type=airports");
				
			/* Open Connection and send GET request */
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			/* The response code given by the server*/
			int responseCode = connection.getResponseCode(); 
			//System.out.println("\nThe Response Code is: " + responseCode);
			
			/* If The connection was successful */
			if((responseCode >= 200) && (responseCode <= 299)){
				
				/* Shows successful connection */
				//System.out.println("Getting Airport Info...");
				
				/* Setup the input stream */
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "URF-8" : encoding);
				
				/* This code just copies the String from the Server */
				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null){
					result.append(line);
				}
				reader.close();
				
				/* Increment XML Count by one */
				this.numXML++; 
				
			}
			/* Else the response was not valid */
			else if (responseCode == 403){
				System.out.println("Invalid team name!");
			}
			else if (responseCode == 400){
				System.out.println("Missing or Invalid action");
			}
			else{
				System.out.println("Unknown connection error");
			}
		}	
			
			/* Needs to catch these exceptions */
			catch(IOException e){
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			return result.toString();
	}
	
	/**
	 * An method that can get all the XML information about airplane from the server.
	 * @return the XML information about airplane on the server.
	 */

	public  String getAirplaneXML (){
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
		try{
			url = new URL(urlAddress + "?team="+ teamName + "&action=list&list_type=airplanes");
				
			/* Open Connection and send GET request */
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			/* The response code given by the server*/
			int responseCode = connection.getResponseCode(); 
			//System.out.println("\nThe Response Code is: " + responseCode);
			
			/* If The connection was successful */
			if((responseCode >= 200) && (responseCode <= 299)){
				
				//System.out.println("Getting Airplane Info..."); 
				
				/* Setup the input stream */
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "URF-8" : encoding);
				
				/* This code just copies the String from the Server */
				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null){
					result.append(line);
				}
				reader.close();
				
				/* Increment XML Count by one */
				this.numXML++; 
				
			}
			/* Else the response was not valid */
			else if (responseCode == 403){
				System.out.println("Invalid team name!");
			}
			else if (responseCode == 400){
				System.out.println("Missing or Invalid action");
			}
			else{
				System.out.println("Unknown connection error");
			}
		}	
			
			/* Needs to catch these exceptions */
			catch(IOException e){
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			return result.toString();
	}
	
	/**
	 * An method that can get all the XML information about a flight from the server.
	 * @param depart select whether the departure information or the arrival information is needed
	 * @param airport the airport this flight would depart or arrive
	 * @param date the date this flight would depart or arrive
	 * @return the XML information about flight on the server
	 */

	public  String getFlightsXML (boolean depart, Airport airport, Date date){
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
		/* Variables used in the URL address */
		String code = airport.getCode();
		int year = date.getYear();
		int day = date.getDay();
		Month m = date.getMonth();
		int month = m.ordinal() + 1;
		String type = "departing";
		
		/* If depart is not true, then set type to arriving */
		if (!depart){
			type = "arriving";
		}
		
		try{
			url = new URL(urlAddress + "?team="+ teamName + "&action=list&list_type="+ type +"&airport=" + code + "&day=" + year + "_" + month + "_" + day);
				
			/* Open Connection and send GET request */
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			/* The response code given by the server*/
			int responseCode = connection.getResponseCode(); 
			//System.out.println("\nThe Response Code is: " + responseCode);
			
			/* If The connection was successful */
			if((responseCode >= 200) && (responseCode <= 299)){
				
				//System.out.println("Getting Flight Info..."); 
				
				/* Setup the input stream */
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "URF-8" : encoding);
				
				/* This code just copies the String from the Server */
				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null){
					result.append(line);
				}
				reader.close();
				
				/* Increment XML Count by one */
				this.numXML++; 
				
			}
			/* Else the response was not valid */
			else if (responseCode == 403){
				System.out.println("Invalid team name!");
			}
			else if (responseCode == 400){
				System.out.println("Missing or Invalid action");
			}
			else{
				System.out.println("Unknown connection error");
			}
		}	
			
			/* Needs to catch these exceptions */
			catch(IOException e){
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}

			return result.toString();
	}
	
	/**
	 * An method that can reset database on the server.
	 * @return the status whether the database is successfully reset.
	 */
	
	/* Resets the Database */
	public boolean resetDB(){
		URL url;
		HttpURLConnection connection;
		boolean wasReset = false; // Returns true if successful reset

		try{
			url = new URL(urlAddress + "?team=TeamYeYing&action=resetDB");

			/* Open Connection and send GET request */
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			/* The response code given by the server*/
			int responseCode = connection.getResponseCode(); 
//			System.out.println("\nThe Response Code is: " + responseCode);

			/* If The connection was successful */
			if((responseCode >= 200) && (responseCode <= 299)){
//				System.out.println("Resetting the Database."); // Shows successful connection
				wasReset = true;
			}
			/* Else the response was not valid */
			else if (responseCode == 403){
				System.out.println("Invalid team name!");
			}
			else if (responseCode == 400){
				System.out.println("Missing or Invalid action");
			}
			else{
				System.out.println("Unknown connection error");
			}
		}	

		/* Needs to catch these exceptions */
		catch(IOException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return wasReset;
	}
	
	/**
	 * This method can get the time zone information from google server.
	 * @param location the location of the time zone that need to be got.
	 * @return XML string which include time zone information for input location.
	 */
	public String getTimeZoneXML (Location location){
		URL url;
		
		/* Data needed by the Google API */
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		int timeStamp = 1431043200; //This is the date of May 8th 2015 at 00:00 GMT
		
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
		try{
			
			/* Google's TimeZone API */
			url = new URL("https://maps.googleapis.com/maps/api/timezone/xml?location="+latitude+","+longitude+"&timestamp="+timeStamp);
				
			/* Open Connection and send GET request */
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			/* The response code given by the server*/
			int responseCode = connection.getResponseCode(); 
			//System.out.println("\nThe Response Code is: " + responseCode);
			
			/* If The connection was successful */
			if((responseCode >= 200) && (responseCode <= 299)){
				
				//System.out.println("Getting TimeZone Info..."); 
				
				/* Setup the input stream */
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "URF-8" : encoding);
				
				/* This code just copies the String from the Server */
				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null){
					result.append(line);
				}
				reader.close();
				
				/* Increment XML Count by one */
				this.numXML++; 
				
			}
			/* Else the response was not valid */
			else{
				System.out.println("Some error occured");
			}
		}	
			
			/* Needs to catch these exceptions */
			catch(IOException e){
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			return result.toString();
	}
	
	/**
	 * String representation of the XMLGetter object.
	 * <p>
	 * @return the string representation of this XMLGetter. 
	 */

	public String toString() {
		return "This is an XMLGetter that has the team name: " + teamName + " and it has gotten " + numXML + " XML file(s)";
	}

}


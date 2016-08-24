package parsers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import flight_system.*;

/** 
 * Creates a DOM that parses an Airport XML file from the flight database. 
 * <p>
 * This class uses the Singleton Pattern, thus, only one instance of it is allowed
 * as any point in time. The parser object also allows a client to get a copy of 
 * a list that contains all the airports that were parsed. Also, the parser 
 * object provides a method for getting an airport based on its 3-digit code.  
 * 
 * @author Kun Huang
 * @see parsers.XMLGetter#getAirportsXML() 
 */

public class AirportParser {
	
	/* List to hold the airplanes parsed from the XML */
	private ArrayList<Airport> airportList; 
	
	private static AirportParser firstInstance = null;
	
	/* The private constructor makes a list of the Airplanes */
	private AirportParser(){
		this.airportList = new ArrayList<Airport>();
	};
	
	/** 
	 * Gets the only instance of the class. 			
	 * @return the only instance of the Airport Parser.
	 */
	/* Method to get the only instance of the class */
	public static AirportParser getInstance(){
		if(firstInstance == null){
			firstInstance = new AirportParser();
		}

		return firstInstance;
	}
		
	/** 
	 * Gives a list of the airports that were parsed.			
	 * @return the list of the parsed airports.
	 */
	/* Returns a list of the parsed airplanes */
	public ArrayList<Airport> getAirportList() {
		return airportList;
	}
	
	/** 
	 * Gets the the size of the airport list.			
	 * @return the number of airports that were parsed.
	 */
	/* Returns the number of airplanes in the list */
	public int getNumOfAirports() {
		return this.airportList.size();
	}
	
	/** 
	 * Provides the airport that matches the code that was provided.
	 * Returns a null object if there is no matching airport.			
	 * @param code the 3-digit code for the airplane.
	 * @return the airport object that is associated with the code.
	 */
	/* Return the airport from the list that corresponds with the code */
	public Airport getAirport(String code){

		boolean notFound = true;  // used determine if an airport was found 
		Airport airport = null; // holds the airport that matches the code

		/* Iterator object for the airport list */
		ListIterator<Airport> airportIterator = airportList.listIterator();

		/* Search this list until the airport with the code is found */
		do {

			try {
				airport = airportIterator.next(); // get the next Airport in the list
				/* If the model matches, set the notFound flag to false */
				if (airport.getCode().equalsIgnoreCase(code)) {
					notFound = false; 
				}
			} catch (Exception e) {
				/* No such element (Airport) found */
				airport = null;
				notFound = false; 
			}


		} while (notFound); // end while loop

		return airport; // Return the airport that matches the code
						// Will return null if there's no match

	}
	
	/** 
	 * Prints all the airports that were parsed.
	 */
	/* Prints the airplane list */
	public void printAirportList(){
		System.out.println("Printing the Airport XML data:");
		/* Print each Airplanes in the list */
		for (int i = 0; i < airportList.size(); i++) {
			System.out.println(this.airportList.get(i).toString());
		}

	}
	
	/** 
	 * Used to tell if the parser already parsed the airport XML.			
	 * @return true if the parser already has the list of airports.
	 */
	/* Returns true if the list is populated */
	public boolean hasAirportList(){
		return (this.airportList.size() != 0);
		
	}
	
	/** 
	 * Parses the airport data from the XML that is provided to it. Stores the parsed 
	 * airport data in a list within the object.
	 * 			
	 * @param xmlSource the XML String that contains the airport information.
	 * @see parsers.XMLGetter#getAirportsXML()
	 */
	/* Static Method used to parse the airplane XML */
	public void parseAirportXML(String xmlSource) {

		/* DOM Factory Builder */
		DocumentBuilderFactory dom_fac = DocumentBuilderFactory.newInstance();

		try {
			/* Builds the doc object that contains the 
			 * tree structure of the XML file */
			DocumentBuilder builder = dom_fac.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlSource))); // This is the root node

			/* Contains a list of all the airplanes from the Airplanes XML */
			NodeList airportNodeList = doc.getElementsByTagName("Airport");

			/* Iterate through all the airplanes in the airplane list */
			for (int i = 0; i < airportNodeList.getLength(); i++) {

				Node airportNode = airportNodeList.item(i); // Airport Node

				/* I know this node is an element, so can cast it as such */
				Element airport = (Element) airportNode;

				/* Get the Airplane Manufacturer and Model */
				String code = airport.getAttribute("Code");
				String name = airport.getAttribute("Name");
				
				/* Contains the 1st Class & Coach seat nodes */
				NodeList airportNodeChildren = airportNode.getChildNodes();

				/* 2nd child is the Latitude Node */
				String latitude = airportNodeChildren.item(0).getTextContent();
				
				/* 4th child is the Longitude Node */
				String longitude = airportNodeChildren.item(1).getTextContent();
				
				Location location = new Location(Double.parseDouble(latitude),
												 Double.parseDouble(longitude));
				
				/* Adds the parsed airplane to the airplane list */
				airportList.add(new Airport(code, name, location));
			}

		/* Exceptions required by the Parser */	

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 
	 * Provides a string representation of the Airport parser.			
	 * @return a string representation of the Airport parser object.
	 */
	@Override
	public String toString() {
		return "AirportParser extracted" + this.getNumOfAirports() + "airports from the XML";
	}

	

}


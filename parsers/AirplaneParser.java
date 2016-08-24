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

import flight_system.Airplane;

/** 
 * Creates a DOM that parses an Airplane XML file from the flight database. 
 * <p>
 * This class uses the Singleton Pattern, thus, only one instance of it is allowed
 * as any point in time. The parser object also allows a client to get a copy of 
 * a list that contains of the airplanes that were parsed. Also, the
 * object provides a method for getting an airplane based on its model.  
 *
 * @author Kun Huang
 * @see parsers.XMLGetter#getAirplaneXML() 
 */

public class AirplaneParser {
	
	/* List to hold the airplanes parsed from the XML */
	private ArrayList<Airplane> airplaneList; 
	
	private static AirplaneParser firstInstance = null;
	
	/* The private constructor makes a list of the Airplanes */
	private AirplaneParser(){
		this.airplaneList = new ArrayList<Airplane>();
	};
	
	/** 
	 * Gets the only instance of the class. 			
	 * @return the only instance of the Airplane Parser.
	 */
	/* Method to get the only instance of the class */
	public static AirplaneParser getInstance(){
		if(firstInstance == null){
			firstInstance = new AirplaneParser();
		}

		return firstInstance;
	}
	
	/** 
	 * Gives a list of the airplanes that were parsed.			
	 * @return the list of the parsed airplanes.
	 */
	/* Returns a list of the parsed airplanes */
	public ArrayList<Airplane> getAirplaneList() {
		return airplaneList;
	}
	
	/** 
	 * Gets the the size of the airport list.			
	 * @return the number of airplanes that were parsed.
	 */
	/* Returns the number of airplanes in the list */
	public int getNumOfAirplanes() {
		return this.airplaneList.size();
	}
	
	/** 
	 * Provides the airplane that matches the model that was provided.
	 * Returns a null object if there is no matching airplane.			
	 * @param model the model of the airplane.
	 * @return the airplane object that is associated with the code.
	 */
	/* Return the airplane from the list that corresponds with the model number */
	public Airplane getAirplane(String model){

		boolean notFound = true;  // used determine if an airplane was found 
		Airplane airplane = null; // holds the airplane that matches the code

		/* Iterator object for the airplane list */
		ListIterator<Airplane> airplaneIterator = airplaneList.listIterator();

		/* Search this list until the airplane with the model is found */
		do {

			try {
				airplane = airplaneIterator.next(); // get the next Airport in the list
				/* If the model matches, set the notFound flag to false */
				if (airplane.getModel().equalsIgnoreCase(model)) {
					notFound = false; 
				}
			} catch (Exception e) {
				/* No such element (Airplane) found */
				airplane = null;
				notFound = false; 
			}


		} while (notFound); // end while loop

		return airplane; // Return the airplane that matches the code
						 // Will return null if there's no match

	}
	
	/** 
	 * Prints all the airplanes that were parsed.
	 */
	/* Prints the airplane list */
	public void printAirplaneList(){
		System.out.println("Printing the Airplane XML data:");
		/* Print each Airplanes in the list */
		for (int i = 0; i < airplaneList.size(); i++) {
			System.out.println(this.airplaneList.get(i).toString());
		}

	}
	
	/** 
	 * Used to tell if the parser already parsed the airplane XML.			
	 * @return true if the parser already has the list of airplanes.
	 */
	/* Returns true if the list is populated */
	public boolean hasAirplaneList(){
		return (this.airplaneList.size() != 0);
		
	}
	
	/** 
	 * Parses the airplane data from the XML that is provided to it. Stores the parsed 
	 * airplane data in a list within the object.
	 * 			
	 * @param xmlSource the XML String that contains the airplane information.
	 * @see parsers.XMLGetter#getAirplaneXML()
	 */
	/* Static Method used to parse the airplane XML */
	public void parseAirplaneXML(String xmlSource) {

		/* DOM Factory Builder */
		DocumentBuilderFactory dom_fac = DocumentBuilderFactory.newInstance();

		try {
			/* Builds the doc object that contains the 
			 * tree structure of the XML file */
			DocumentBuilder builder = dom_fac.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlSource))); // This is the root node
			
			/* Contains a list of all the airplanes from the Airplanes XML */
			NodeList airplaneNodeList = doc.getElementsByTagName("Airplane");

			/* Iterate through all the airplanes in the airplane list */
			for (int i = 0; i < airplaneNodeList.getLength(); i++) {

				Node airplaneNode = airplaneNodeList.item(i); // Airplane Node

				/* I know this node is an element, so can cast it as such */
				Element airplane = (Element) airplaneNode;

				/* Get the Airplane Manufacturer and Model */
				String model = airplane.getAttribute("Model");
				String manufacturer = airplane.getAttribute("Manufacturer");
				
				/* Contains the 1st Class & Coach seat nodes */
				NodeList airplaneNodeChildren = airplaneNode.getChildNodes();

				/* 2nd child is the First Class Seat Node */
				String firstClassSeats = airplaneNodeChildren.item(0).getTextContent();
				
				/* 4th child is the Coach Seat Node */
				String coachSeats = airplaneNodeChildren.item(1).getTextContent();
				
				/* Adds the parsed airplane to the airplane list */
				airplaneList.add(new Airplane(model, 
						manufacturer, 
						Integer.parseInt(firstClassSeats), 
						Integer.parseInt(coachSeats)
						));
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
	 * Provides a string representation of the Airplane parser.			
	 * @return a string representation of the Airplane parser object.
	 */
	@Override
	public String toString() {
		return "AirplaneParser extracted" + getNumOfAirplanes() + "airplanes from the XML";
	}

	

}


/*  Date: March 7. 2015
 *  Description: Class responsible for holding the location of an object.
 *  			 Contains the latitude and longitude of a location, in 
 *  			 addition to a method to determine it's time zone.
 *  Updated at April 8. 2015
 *  Description: Implemented the determineTimeZone method, now this class can analyze
 *               the time zone information come from server and return the raw offset
 *               information according to the time zone.
 */

package flight_system;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import parsers.XMLGetter;

/** 
 * Class used to represent location in latitude and longitude.
 * <p>
 * The class can also determine the time zone of the given location.
 * 
 * @author Richard Walker
 *  
 */
public class Location {
	
	/* Fields */
	private double latitude;
	private double longitude;
	private double timeZoneOffset;
	
	/**
	 * Makes an object that represent location
	 * @param latitude the latitude of the location
	 * @param longitude the longitude of the location
	 */
	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;

		/* Have to sleep in order to avoid the Google API Limit */
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		determineTimeZone(); // Get the Time Zone from Google
	}
	
	/**
	 * Makes an object that represent location include its time zone.
	 * @param latitude the latitude of the location
	 * @param longitude the longitude of the location
	 * @param getZone determine whether to get time zone of this location or not
	 */
	
	/* Constructor for making a Location w/o getting the TimeZone info */
	public Location(double latitude, double longitude, boolean getZone) {

		if(getZone){
			this.latitude = latitude;
			this.longitude = longitude;

			/* Have to sleep in order to avoid the Google API Limit */
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			determineTimeZone(); // Get the Time Zone from Google
		}
		else{
			this.latitude = latitude;
			this.longitude = longitude;	
		}
		
	}
	
	/**
	 * Get the latitude of the location
	 * @return the latitude of the location
	 */
	/* Getters */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * get the longitude of the location
	 * @return the longitude of the location
	 */

	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * get the parameter called 'time zone offset' of the location
	 * @return the time zone offset of the location
	 */
	
	public double getTimeZoneOffset() {
		
		return timeZoneOffset;
	}
	
	/**
	 * Make a method to get the time zone information of selected location
	 * @see parsers.XMLGetter#getTimeZoneXML(Location)
	 */
	
	private void determineTimeZone(){
		//Getting information
		XMLGetter getter = XMLGetter.getInstance();
		String xmlSource = getter.getTimeZoneXML(this);
		
		//Parsing info
		DocumentBuilderFactory dom_fac = DocumentBuilderFactory.newInstance();

		try {
			/* Builds the doc object that contains the 
			 * tree structure of the XML file */
			DocumentBuilder builder = dom_fac.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlSource))); // This is the root node
			
			/* Contains a list of all the airplanes from the Airplanes XML */
			NodeList childNodes = doc.getElementsByTagName("raw_offset");
			Node rawOffsetNode =  childNodes.item(0);
			
            double rawOffset = Double.parseDouble(rawOffsetNode.getTextContent());
		    this.timeZoneOffset = rawOffset;
		    
		    
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
	 * String representation of the location object.
	 * <p>
	 * @return the string representation of this location. 
	 */

	@Override
	public String toString() {
		return "This location is at latitude " + latitude + " at longitude " + longitude + " and has of raw_offset " + timeZoneOffset;

	}
	
	
	
}

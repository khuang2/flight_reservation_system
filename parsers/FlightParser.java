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
 * Creates a DOM that parses an Flight XML file from the flight database. 
 * <p>
 * The parser object also allows a client to get a copy of 
 * a list that contains the flights that were parsed. Also, the
 * object provides a method for getting a flight based on its number.  
 * 
 * @author Kun Huang
 * 
 */

public class FlightParser {
	
	/* List to hold the flights parsed from the XML */
	private ArrayList<FlightLeg> flightLegList;
	
	/* Used hold the airplanes that were parsed from the XML */
	private AirplaneParser airplanes;
	
	/* Used hold the airports that were parsed from the XML */
	private AirportParser airports;
	
	/**
	 * Makes a Flight Parser.
	 * @param planeXML the XML string that contains info about airplanes.
	 * @param portXML the XML string that contains info about airports.
	 */
	public FlightParser(String planeXML, String portXML) {
		
		 /* Make an empty list to store the extracted flight legs */
		 this.flightLegList = new ArrayList<FlightLeg>();
		 
		 /* Get the Airplane Parser object then parse then
		  * XML to ensure you have the list of airplanes */
		 this.airplanes = AirplaneParser.getInstance();
		 
		 if (!this.airplanes.hasAirplaneList()){
			 this.airplanes.parseAirplaneXML(planeXML); 
		 }
		 
		 /* Get the Airport Parser object then parse then
		  * XML to ensure you have the list of airplanes */
		 this.airports = AirportParser.getInstance();
		 
		 if (!this.airports.hasAirportList()){
			 this.airports.parseAirportXML(portXML);
		 }
		 
	}
	/**
	 * Makes a Flight Parser with an empty flight leg list.
	 */
	public FlightParser(){
		/* Make an empty list to store the extracted flight legs */
		 this.flightLegList = new ArrayList<FlightLeg>();
	}
	
	/**
	 * Gets the list of the parsed flight legs.
	 * @return Returns a list of the parsed FlightLeg objects.
	 * @see flight_system.FlightLeg
	 */
	/* Returns a list of the parsed airplanes */
	public ArrayList<FlightLeg> getFlightList() {
		return flightLegList;
	}
	
	/**
	 * Clears the internal flight leg list.
	 */
	/* Empties the list */
	public void clearFlightList(){
		this.flightLegList.clear();
	}
	
	/**
	 * Gets the number of flights in the list.
	 * @return the number of flights in the list.
	 */
	/* Returns the number of airplanes in the list */
	public int getNumOfFlights() {
		return this.flightLegList.size();
	}
	
	/**
	 * Gets a flight that matches a provided flight number. Returns null, if it
	 * doesn't exist.
	 * @param flightNum the flight number you are looking for.
	 * @return the flight leg that corresponds with the flight number.
	 */
	/* Return the flight from the list that corresponds with the flight number */
	public FlightLeg getFlight(int flightNum){

		boolean notFound = true;  // used determine if a flight was found 
		FlightLeg flight = null; // holds the flight that matches the number

		/* Iterator object for the airplane list */
		ListIterator<FlightLeg> flightIterator = flightLegList.listIterator();

		/* Search this list until the airplane with the model is found */
		do {

			try {
				flight = flightIterator.next(); // get the next flight in the list
				/* If the flight number matches, set the notFound flag to false */
				if (flight.getFlightNum() == flightNum) {
					notFound = false; 
				}
			} catch (Exception e) {
				/* No such element (Flight) found */
				flight = null;
				notFound = false; 
			}


		} while (notFound); // end while loop

		return flight; // Return the flight that matches the number
						// Will return null if there's no match

	}
	
	/**
	 * Prints the flight list.
	 */
	/* Prints the flight list */
	public void printFlightList(){
		System.out.println("Printing the Flight XML data:");
		/* Print each flight in the list */
		for (int i = 0; i < flightLegList.size(); i++) {
			System.out.println(this.flightLegList.get(i).toString());
		}

	}

	/**
	 * Used to parse a flight XML into Flight Leg objects.
	 * @param xmlSource the XML string that needs to be parsed.
	 * @see flight_system.FlightLeg
	 */
	/* Method used to parse the flight XML */
	public void parseFlightXML(String xmlSource) {

		/* DOM Factory Builder */
		DocumentBuilderFactory dom_fac = DocumentBuilderFactory.newInstance();

		try {
			/* Builds the doc object that contains the 
			 * tree structure of the XML file */
			DocumentBuilder builder = dom_fac.newDocumentBuilder();
			
			/* This is the root node */
			Document doc = builder.parse(new InputSource(new StringReader(xmlSource))); 

			/* Contains a list of all the flights from the Flight XML */
			NodeList flightNodeList = doc.getElementsByTagName("Flight");

			/* Iterate through all the flights in the airplane list */
			for (int i = 0; i < flightNodeList.getLength(); i++) {

				Node flightNode = flightNodeList.item(i); // Flight Node

				/* I know this node is an element, so can cast it as such */
				Element flight = (Element) flightNode;
				
				/* Get the Flight's Airplane*/
				String airplaneModel = flight.getAttribute("Airplane");
				Airplane airplane = airplanes.getAirplane(airplaneModel);
				
				/* Get the Flight's duration */ 
				String flightTime = flight.getAttribute("FlightTime");
				int flightDur = Integer.parseInt(flightTime);
				
				/* Get Flight Number */
				String flightNum = flight.getAttribute("Number");
				int fgtNum = Integer.parseInt(flightNum);
				
				/* Contains the Flight Node children */
				NodeList flightNodeChildren = flightNode.getChildNodes();
				
				/* -- Departure Data -- */
				
				/* Get the children of the departure node */
				NodeList deptNodeChild = flightNodeChildren.item(0).getChildNodes();
				
				/* 1st child is the Departure Airport Text Node */
				String depAirPortCode = deptNodeChild.item(0).getTextContent();
				Airport depAirport = airports.getAirport(depAirPortCode);
				
				/* 2nd child is the Departure Time Text Node */
				String depTimeNode = deptNodeChild.item(1).getTextContent();
				
				/* Array to hold the delimited departure time and date data */
				String[] depTimeNodeData = depTimeNode.split(" ");
				
				/* Variables to hold the year, month, and day */
				int dYear = Integer.parseInt(depTimeNodeData[0]);
				Month dMonth = Month.valueOf(depTimeNodeData[1]);
				int dDay = Integer.parseInt(depTimeNodeData[2]);
				
				/* Make the departure date object */
				Date depDate = new Date(dMonth, dDay, dYear);
				
				/* Array to hold the delimited departure time data */
				String[] timeDep = depTimeNodeData[3].split(":");
				int dHour = Integer.parseInt(timeDep[0]);
				int dMins = Integer.parseInt(timeDep[1]);
				
				/* Make the departure Time object */
				  Time depTime = new Time(dHour, dMins);
				
				/* -------------------- */
				
				/* -- Arrival Data -- */
				
				/* Get the children of the arrival node */
				NodeList arrNodeChild = flightNodeChildren.item(1).getChildNodes();

				/* 2nd child is the Departure Airport Text Node */
				String arrAirPortCode = arrNodeChild.item(0).getTextContent();
				Airport arrAirport = airports.getAirport(arrAirPortCode);

				/* 4th child is the Departure Time Text Node */
				String arrTimeNode = arrNodeChild.item(1).getTextContent();
				
				/* Array to hold the delimited departure time data */
				String[] arrTimeNodeData = arrTimeNode.split(" ");
				
				/* Variables to hold the year, month, and day */
				int arYear = Integer.parseInt(arrTimeNodeData[0]);
				Month arMonth = Month.valueOf(arrTimeNodeData[1]);
				int arDay = Integer.parseInt(arrTimeNodeData[2]);
				
				/* Making the arrival date object */
				Date arrDate = new Date(arMonth, arDay, arYear);
				
				/* Array to hold the delimited departure time data */
				String[] timeArr = arrTimeNodeData[3].split(":");
				int aHour = Integer.parseInt(timeArr[0]);
				int aMins = Integer.parseInt(timeArr[1]);
				
				/* Make the arrival Time object */
				Time arrTime = new Time(aHour, aMins);

				/* ------------------ */ 
	
				/* -- Seating Data -- */
			
				/* Get the children of the seating node */
				NodeList seatNodeChild = flightNodeChildren.item(2).getChildNodes();
				
				/* 1st child is the First Class Node, which is an Element */
				Element firstClassSeat = (Element) seatNodeChild.item(0);
				
				/* Get the Flight's First Class Seat Price */
				String firstPriceString = firstClassSeat.getAttribute("Price");
				firstPriceString = firstPriceString.substring(1); // removes the $ sign
				firstPriceString = firstPriceString.replace(",",""); // removes any ,
				Double firstPrice = Double.parseDouble(firstPriceString);
				
				/* Get the number of seats available in First Class */
				String firstClassSeats = seatNodeChild.item(0).getTextContent();
				int firstClassAvail = Integer.parseInt(firstClassSeats);
				
				/* 2nd child is the Coach Class Node, which is an Element */
				Element coachClassSeat = (Element) seatNodeChild.item(1);
				
				/* Get the Flight's Coach Class Seat Price */
				String coachPriceString = coachClassSeat.getAttribute("Price");
				coachPriceString = coachPriceString.substring(1); // removes the $ sign
				coachPriceString = coachPriceString.replace(",",""); // removes any ,
				Double coachPrice = Double.parseDouble(coachPriceString);
				
				/* Get the number of seats available in First Class */
				String coachClassSeats = seatNodeChild.item(1).getTextContent();
				int coachClassAvail = Integer.parseInt(coachClassSeats);

				/* ------------------- */
				
				/* Makes the FlightLeg Object */
				FlightLeg flightLeg = new FlightLeg(airplane, fgtNum, flightDur, 
													depTime, depDate, depAirport, 
													arrTime, arrDate, arrAirport, 
													firstPrice, firstClassAvail, 
													coachPrice, coachClassAvail);
			
				/* Adds the parsed airplane to the airplane list */
				flightLegList.add(flightLeg);
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
	
	/* Prints the Flight information as String Data */
	@SuppressWarnings("unused")
	private void printFlights(String airplaneModel, String flightTime,
			String flightNum, String depAirPortCode, Date depDate,
			String arrAirPortCode, Date arrDate, String firstPrice,
			String firstClassSeats, String coachPrice, String coachClassSeats) {
		
		System.out.println("FLight Plane: " + airplaneModel);
		System.out.println("FLight Time: " + flightTime);
		System.out.println("FLight Number: " + flightNum);
		System.out.println();

		System.out.println("Departure Info");
		System.out.println("Dept. Airport: " + depAirPortCode);
		System.out.println("Dept. Date: " + depDate);
		System.out.println();

		System.out.println("Arrival Info:");
		System.out.println("Arrival Airport: " + arrAirPortCode);
		System.out.println("Arrv. Date: " + arrDate);
		System.out.println();

		System.out.println("Seating Info:");
		System.out.println("First Class Price: " + firstPrice);
		System.out.println("First Class Seats: " + firstClassSeats);
		System.out.println("Coach Class Price: " + coachPrice);
		System.out.println("Coach Class Seats: " + coachClassSeats);
		System.out.println();
		System.out.println("-------------------");
	}

	@Override
	public String toString() {
		return "FlightParser extracted" + this.getNumOfFlights() + "flights from the XML";
	}



}


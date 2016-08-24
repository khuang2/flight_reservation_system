package graph;

import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import parsers.AirportParser;
import parsers.FlightParser;
import parsers.XMLGetter;
import flight_system.*;

/** 
 * Class used to make flight graph based on flight data from the database.
 * <p>
 * @see parsers.XMLGetter#getAirportsXML()
 * @see parsers.XMLGetter#getFlightsXML(boolean, Airport, Date)
 * 
 * @author Kun Huang
 */
public class GraphMaker {
	
	private Date date;
	private Graph graph;
	
	/**
	 * Makes a flight graph for a specific date. 
	 * <p>
	 * The graph will be populated with all the departing flights from all the airports
	 * in the database.
	 * 
	 * @param date the date for which the graph should be made.
	 * @see flight_system.Date
	 */
	public GraphMaker(Date date) {
		this.date = date;
		
		/* Graph Identifier */
		String graphName = "" +date.getMonth() + "_" + date.getDay() + "_" + date.getYear();
		
		/* Create Graph */
		this.graph = new MultiGraph(graphName);

		/* Allow easy creation of edges */
		graph.setAutoCreate(true);
		
		makeGraph();
		
	}
	
	/**
	 * Get the date for which the graph was made. 
	 * <p>
	 * @return date the date for which the graph was made.
	 * @see flight_system.Date
	 */
	/* Getters */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Get the the flight graph that was made. 
	 * <p>
	 * @return the flight graph that was made. 
	 */
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * Used to display the the flight graph that was made. 
	 * <p> 
	 */
	/* Show the graph */
	public void displayGraph(){
		this.graph.display();
	}
	
	/* Makes the graph by using the XML getters */
	private void makeGraph(){
		
		/* XML Getter Singleton */
		XMLGetter getter = XMLGetter.getInstance();
		
		/* Get the Airport Parser object */
		AirportParser portParser = AirportParser.getInstance();
		
		/* Adds the nodes to the graph */
		addNodes(portParser, getter);
		
		/* Adds the edges to the graph */
		addEdges(getter, portParser);
			
	}

	/* Adds Edges to the graph */
	private void addEdges(XMLGetter getter, AirportParser portParser) {
		/* Flight Parser object used to store the departing flights */
		FlightParser fParser = new FlightParser(getter.getAirplaneXML(), getter.getAirportsXML());
		
		/* Iterator for nodes */
		Iterator<Node> nodeIterator = this.graph.iterator();
		
		/* Get all the departing flights from every airport */
		while (nodeIterator.hasNext()){
			
			String airportCode = nodeIterator.next().getId();
			
			Airport airport = portParser.getAirport(airportCode);
			
			/* Get the flights */
			fParser.parseFlightXML(getter.getFlightsXML(true, airport, this.date));
			
			/* Add all the edges (departing flights) */
			for (FlightLeg flight : fParser.getFlightList()){
				
				/* Add the flight as an edge to the graph */
				graph.addEdge(Integer.toString(flight.getFlightNum()), airportCode, flight.getArrivalAirport().getCode(), true);
				
				/* Get the edge that was just added */
				Edge edge = graph.getEdge(Integer.toString(flight.getFlightNum()));
				
				/* Add the attribute to the edge */
				edge.addAttribute("fltInfo", flight);
			
			}
			
			/* Clean the list */
			fParser.clearFlightList();
			
		}
	}

	/* Adds the Airport Nodes to the graph */
	private void addNodes(AirportParser portParser, XMLGetter getter) {
		
		/* If it doesn't have the airport list already, get the data from the server */
		if (!portParser.hasAirportList()){
			portParser.parseAirportXML(getter.getAirportsXML());
		}
		
		/* Add all the airport nodes to the graph */
		for(Airport airport : portParser.getAirportList() ){
			
			/* Add the Airport Node */
			this.graph.addNode(airport.getCode());
			
		}
		
	}
	
}

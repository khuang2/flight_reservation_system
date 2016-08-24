package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import flight_system.Airplane;
import flight_system.Airport;
import flight_system.Date;
import flight_system.FlightLeg;
import flight_system.Location;
import flight_system.Month;
import flight_system.Time;

public class ExampleGraph {

	private Airplane airplane;
	private Date date;
	private ArrayList<Airport> airports;
	private Graph graph;

	/* Constructor */
	public ExampleGraph() {
		this.airplane = new Airplane("774", "Airbus", 20, 50);
		this.date = new Date(Month.May, 10, 2015);
		this.airports = makeTestAiports();
	};
		
	/* Test small made-up graph */
	public void testGraph() {

		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		
		ArrayList<LinkedList<Edge>> routes = new ArrayList<LinkedList<Edge>>();

		/* Make the graph */
		graph = makeGraph();

		/* Nodes */
		Node bos = graph.getNode("BOS");
		Node jfk = graph.getNode("JFK");
		Node atl = graph.getNode("ATL");
		Node mia = graph.getNode("MIA");
		Node sfo = graph.getNode("SFO");
		Node kgn = graph.getNode("KGN");

		/* Add the edges (departing flights) to the graph */
		edgeList = addTestEdges(graph);

		/* Verifying Graph Data */
		for (Edge edge : edgeList) {
			printEdge(edge);
		}

		 /* Testing hasRoute */
		 System.out.println("\n------- Testing hasRoute --------");
		 testHasRoute(bos, atl);
		 testHasRoute(atl, mia);
		 testHasRoute(bos, mia);

		/* Testing timeHasRoute */
		 System.out.println("\n------- Testing timeHasRoute --------");
		 testTimeRoute(bos, jfk); // True - Direct Flight
		 testTimeRoute(bos, atl); // True - 1 connection
		 testTimeRoute(bos, mia); // True - 2 connections
		 testTimeRoute(atl, sfo); // False - 1 connection, but leaves too early
		 testTimeRoute(bos, kgn); // False - 3 connections
		 testTimeRoute(mia, sfo); // False - MIA -> ATL -> BOS -/> SFO
		 testTimeRoute(kgn, mia); // False - KGN -> BOS -> JFK -> ATL -> MIA too many stops
		 testTimeRoute(kgn, atl); // True - KGN -> BOS -> JFK -> ATL
		 testTimeRoute(jfk, mia); // True - JFK -> ATL -> MIA
		
		/* Testing getRoutes */
		routes = getRoutes(bos, atl, new ArrayList<Node>(), bos, 0);
		routes = getRoutes(atl, bos, new ArrayList<Node>(), atl, 0);
		routes = getRoutes(kgn, atl, new ArrayList<Node>(), atl, 0);
		routes = getRoutes(jfk, mia, new ArrayList<Node>(), jfk, 0);
		routes = getRoutes(mia, sfo, new ArrayList<Node>(), mia, 0);
		routes = getRoutes(atl, sfo, new ArrayList<Node>(), atl, 0);
		routes = getRoutes(bos, mia, new ArrayList<Node>(), bos, 0);
		routes = getRoutes(jfk, sfo, new ArrayList<Node>(), jfk, 0); // need to work on this one

		/* Print all the possible routes */
		System.out.println("---- Testing getRoutes Method: ----");
		for (LinkedList<Edge> route : routes) {
			System.out.println(route);
		}
	
		/* Testing the Flight Filter, (filters by chronological order and if the flight is 1st class) */
		
		/* Flight 2001 has no available coach seats */
		ArrayList<LinkedList<Edge>> filteredRoutes = routeFilter(routes, false);
		
		System.out.println("\n---- Testing filteredRoutes Method: ----");
		for (LinkedList<Edge> route : filteredRoutes) {
			System.out.println(route);
		}

	}

	/* Make the test airports */
	protected static ArrayList<Airport> makeTestAiports() {

		ArrayList<Airport> airports = new ArrayList<Airport>();

		/* Clean the list before you make it */
		airports.clear();

		Airport bosPort = new Airport("BOS", "Boston Logan", new Location(42.365855, -71.009624, false));
		airports.add(bosPort);

		Airport jfkPort = new Airport("JFK", "John F. Kennedy", new Location(40.641519, -73.77816, false));
		airports.add(jfkPort);

		Airport atlPort = new Airport("ATL", "Hartsfield Jackson", new Location(33.641045, -84.427764, false));
		airports.add(atlPort);

		Airport miaPort = new Airport("MIA", "Miami", new Location(25.796131, -80.287014, false));
		airports.add(miaPort);

		Airport sfoPort = new Airport("SFO", "San Francisco", new Location(37.621598, -122.37903, false));
		airports.add(sfoPort);
		
		Airport kgnPort = new Airport("KGN", "Norman Manley", new Location(17.9356, -76.7875, false));
		airports.add(kgnPort);

		return airports;
			
	}

	/* Makes the graph for the small made-up graph */
	private Graph makeGraph() {
		graph = new MultiGraph("Test Flights");

		/* Allow easy creation of edges */
		graph.setAutoCreate(true);

		/* Making Graph */
		graph.addNode("BOS");
		graph.addNode("JFK");
		graph.addNode("ATL");
		graph.addNode("MIA");
		graph.addNode("SFO");
		graph.addNode("KGN");

		graph.addEdge("1000", "BOS", "JFK", true);
		graph.addEdge("1001", "BOS", "SFO", true);

		graph.addEdge("2000", "JFK", "ATL", true);
		graph.addEdge("2001", "JFK", "ATL", true);

		graph.addEdge("3000", "ATL", "BOS", true);
		graph.addEdge("3001", "ATL", "JFK", true);
		graph.addEdge("3002", "ATL", "MIA", true);
		graph.addEdge("3003", "ATL", "MIA", true);

		graph.addEdge("4000", "MIA", "KGN", true);
		graph.addEdge("4001", "MIA", "ATL", true);
		graph.addEdge("4002", "MIA", "SFO", true);
		
		graph.addEdge("5000", "KGN", "BOS", true);

		return graph;

	}
	
	/* Tests the timeHasRoute method */
	private void testTimeRoute(Node dep, Node arr) {
		System.out.println("There's a route between "+ dep + " and " + arr + ": " + timeHasRoute(dep, arr, 0, null, new ArrayList<Node>()));

	}
	
	/* Tests the hasRoute method */
	private void testHasRoute(Node dep, Node arr) {
		System.out.println("There's a route between " + dep + " and " + arr + ": " + simpleHasRoute(dep, arr, 0));
	}
	
	/* Prints the information about an edge */
	private void printEdge(Edge edge) {
		System.out.println("Is this edge directed? " + edge.isDirected());
		System.out.println("The flight number is: " + edge.getId());
		System.out.println("The flight has : " + edge.getAttributeCount() + " attribute");
		System.out.println("The flight time is : " + edge.getAttribute("fltInfo"));
	}

	/* Adds the edges to the test graph */
	private ArrayList<Edge> addTestEdges(Graph graph) {

		/* Holds the list of edges to be returned */
		ArrayList<Edge> edgeList = new ArrayList<Edge>();

		edgeList.clear(); // clear the list, each time make edges

		/* Flight Times */
		// BOS
		Time d1000 = new Time(10, 00);
		Time a1000 = new Time(10, 30);

		Time d1001 = new Time(12, 00);
		Time a1001 = new Time(15, 00);

		// JFK
		Time d2000 = new Time(11, 00);
		Time a2000 = new Time(11, 59);

		Time d2001 = new Time(9, 00);
		Time a2001 = new Time(10, 00);

		// ATL
		Time d3000 = new Time(12, 00);
		Time a3000 = new Time(13, 00);

		Time d3001 = new Time(12, 00);
		Time a3001 = new Time(12, 59);

		Time d3002 = new Time(12, 00);
		Time a3002 = new Time(12, 30);

		Time d3003 = new Time(12, 15);
		Time a3003 = new Time(12, 45);

		// MIA
		Time d4000 = new Time(13, 00);
		Time a4000 = new Time(14, 00);

		Time d4001 = new Time(9, 00);
		Time a4001 = new Time(9, 30);
		
		Time d4002 = new Time(12, 00);
		Time a4002 = new Time(15, 00);

		// KGN
		Time d5000 = new Time(9, 00);
		Time a5000 = new Time(9, 59);

		/* Making the Flight Legs */
		FlightLeg f1000 = new FlightLeg(airplane, 1000, 30, d1000, date,
				airports.get(0), a1000, date, airports.get(1), 50.00, 15,
				25.00, 25);

		FlightLeg f1001 = new FlightLeg(airplane, 1001, 180, d1001, date,
				airports.get(0), a1001, date, airports.get(4), 50.00, 15,
				25.00, 25);

		FlightLeg f2000 = new FlightLeg(airplane, 2000, 59, d2000, date,
				airports.get(1), a2000, date, airports.get(2), 50.00, 15,
				25.00, 25);
		
		/* f2001 now departs on the next day, and has no available coach seats */
		FlightLeg f2001 = new FlightLeg(airplane, 2001, 60, d2001, new Date(Month.May, 11, 2015),
				airports.get(1), a2001, date, airports.get(2), 50.00, 15,
				25.00, 50);

		FlightLeg f3000 = new FlightLeg(airplane, 3000, 60, d3000, date,
				airports.get(2), a3000, date, airports.get(0), 50.00, 15,
				25.00, 25);

		FlightLeg f3001 = new FlightLeg(airplane, 3001, 59, d3001, date,
				airports.get(2), a3001, date, airports.get(1), 50.00, 15,
				25.00, 25);

		FlightLeg f3002 = new FlightLeg(airplane, 3002, 30, d3002, date,
				airports.get(2), a3002, date, airports.get(3), 50.00, 15,
				25.00, 25);

		FlightLeg f3003 = new FlightLeg(airplane, 3003, 30, d3003, date,
				airports.get(2), a3003, date, airports.get(3), 50.00, 15,
				25.00, 25);

		FlightLeg f4000 = new FlightLeg(airplane, 4000, 60, d4000, date,
				airports.get(3), a4000, date, airports.get(5), 50.00, 15,
				25.00, 25);

		FlightLeg f4001 = new FlightLeg(airplane, 4001, 30, d4001, date,
				airports.get(3), a4001, date, airports.get(2), 50.00, 15,
				25.00, 25);
		
		FlightLeg f4002 = new FlightLeg(airplane, 4002, 360, d4002, date,
				airports.get(3), a4002, date, airports.get(4), 50.00, 15,
				25.00, 25);
		
		FlightLeg f5000 = new FlightLeg(airplane, 5001, 30, d5000, date,
				airports.get(5), a5000, date, airports.get(0), 50.00, 15,
				25.00, 25);

		/* Edges */
		Edge flight1000 = graph.getEdge("1000");
		flight1000.addAttribute("fltInfo", f1000);
		edgeList.add(flight1000);

		Edge flight1001 = graph.getEdge("1001");
		flight1001.addAttribute("fltInfo", f1001);
		edgeList.add(flight1001);

		Edge flight2000 = graph.getEdge("2000");
		flight2000.addAttribute("fltInfo", f2000);
		edgeList.add(flight2000);

		Edge flight2001 = graph.getEdge("2001");
		flight2001.addAttribute("fltInfo", f2001);
		edgeList.add(flight2001);

		Edge flight3000 = graph.getEdge("3000");
		flight3000.addAttribute("fltInfo", f3000);
		edgeList.add(flight3000);

		Edge flight3001 = graph.getEdge("3001");
		flight3001.addAttribute("fltInfo", f3001);
		edgeList.add(flight3001);

		Edge flight3002 = graph.getEdge("3002");
		flight3002.addAttribute("fltInfo", f3002);
		edgeList.add(flight3002);

		Edge flight3003 = graph.getEdge("3003");
		flight3003.addAttribute("fltInfo", f3003);
		edgeList.add(flight3003);

		Edge flight4000 = graph.getEdge("4000");
		flight4000.addAttribute("fltInfo", f4000);
		edgeList.add(flight4000);

		Edge flight4001 = graph.getEdge("4001");
		flight4001.addAttribute("fltInfo", f4001);
		edgeList.add(flight4001);
		
		Edge flight4002 = graph.getEdge("4002");
		flight4002.addAttribute("fltInfo", f4002);
		edgeList.add(flight4002);

		Edge flight5000 = graph.getEdge("5000");
		flight5000.addAttribute("fltInfo", f5000);
		edgeList.add(flight5000);

		return edgeList;
	}

	/* Determines if there's a route between two nodes, maximum of 2 connections */
	private boolean simpleHasRoute(Node depNode, Node arrNode, int con) {

		Node nextConNode; // holds the next node for the recursion
		boolean found = false; // holds the result

		/* If less than 2 connections so far */
		if (con < 3) {

			/* Checks if there's a direct Flight from the current node */
			if (depNode.hasEdgeToward(arrNode)) {

				found = true;

			}
			/* No direct connection from the current node */
			else {

				/* Get an iterator for the departing flights */
				Iterator<Edge> depFlights = depNode.getLeavingEdgeIterator();

				/* While there are still flights to search */
				while (depFlights.hasNext()) {
					nextConNode = depFlights.next().getTargetNode();

					/* Recursive call to search for connections */
					if (simpleHasRoute(nextConNode, arrNode, con + 1)) {
						found = true;
						break; // found a route, stop searching
					}
					;

				}

			}

		}
		/* Exceeded two connections, so return false */
		else {
			found = false;
		}

		/* Return the result */
		return found;
	}

	/* Determines if there's a route between two nodes, maximum of 2 connections */
	private boolean timeHasRoute(Node depNode, Node arrNode, int con,
			Edge conEdge, ArrayList<Node> visited) {

		int maxCon = 3; // maximum 2 connections
		boolean found = false; // holds the result
		FlightLeg conEdgeInfo = null;

		/* If we've been to this node already, we no it's not a route */
		if (visited.contains(depNode)) {

			/*
			 * If this is a connection check, add this departing node to visited
			 * list
			 */
			if (!(conEdge == null)) {
				visited.add(depNode);
			}

			return false;
		}

		/* If less than 2 connections so far */
		else if (con < maxCon) {

			/* Get an iterator for the node's departing flights */
			Iterator<Edge> depFlights = depNode.getLeavingEdgeIterator();

			/* Checks if there's a direct Flight from the current node */
			if (depNode.hasEdgeToward(arrNode)) {

				/*
				 * If connecting edge is null, this means that this is the 1st
				 * check
				 */
				if (conEdge == null) {
					found = true;
				}
				/*
				 * Have to check if the connecting edge arrival time is before
				 * the next potential edge depart time
				 */
				else {

					/* There's a connecting edge, get the info */
					if (conEdge != null) {
						conEdgeInfo = conEdge.getAttribute("fltInfo");
					}

					/* Check the next flight leaving this node */
					while (depFlights.hasNext()) {

						/* Get the next flight that leaves node */
						Edge nxtFlt = depFlights.next();
						FlightLeg nxtFltInfo = nxtFlt.getAttribute("fltInfo");

						boolean beenHere = visited.contains(nxtFlt
								.getTargetNode());

						/*
						 * Does this flight leave after the previous one
						 * arrives, and it doesn't land where we have passed
						 * through already
						 */
						if (nxtFltInfo.getDepartureTime().getTimeInMinutes() > conEdgeInfo
								.getArrivalTime().getTimeInMinutes()
								&& !beenHere) {
							found = true;
							break;
						}

					} // end while depFlights

				} // end if has connecting edges

			} // end if has a direct flight

			/* No direct connection from the current node */
			else {

				/* While the departing node still has flights to search */
				while (depFlights.hasNext() && !found) {

					/* The current edge (departing flight) info */
					Edge depFltEdge = depFlights.next();
					FlightLeg depFltsInfo = depFltEdge.getAttribute("fltInfo");

					/* The next potential Node */
					Node nextConNode = depFltEdge.getTargetNode();

					/*
					 * Get an iterator for the potential node's departing
					 * flights
					 */
					Iterator<Edge> nxtDepFlights = nextConNode
							.getLeavingEdgeIterator();

					/*
					 * While the 1st connection has flights to check, and
					 * doesn't land at the airport we're trying to leave from
					 */
					while (nxtDepFlights.hasNext()) {

						Edge conFlt2nd = nxtDepFlights.next(); // the potential
																// 2nd flight

						if (!(conFlt2nd.getTargetNode().equals(depNode))) {
							/* Get the flight info of the next potential flight */
							FlightLeg conFlt2ndInfo = conFlt2nd.getAttribute("fltInfo");

							/*
							 * If the next connecting edge leaves after the
							 * previous edges arrives then it is possible that
							 * the edge will get us to our final destination
							 */
							if (conFlt2ndInfo.getDepartureTime()
									.getTimeInMinutes() > depFltsInfo
									.getArrivalTime().getTimeInMinutes()) {

								con++; // add one to the connections

								/* Where the connecting flight lands */
								Node conFltLand = conFlt2nd.getTargetNode();

								/*
								 * If the potential flight lands at our final
								 * destination, then we have found the route
								 */
								if (conFltLand.equals(arrNode)
										&& (con < (maxCon - 1))) {
									found = true;
									break; // stop checking for routes
								}
								/*
								 * Check to see if where that flight lands, has
								 * a connection to the final destination
								 */
								else {

									/*
									 * Add the port where the 1st flight landed,
									 * to the visited list
									 */
									visited.add(nextConNode);

									if (timeHasRoute(conFltLand, arrNode, con,
											conFlt2nd, visited)) {
										found = true;
										break;
									}

								}

							}

						} // end if the connection doesn't land where we started
							// from

					} // end while for next node's departing flights

					/*
					 * We checked the node where the first flight would have
					 * landed, but it doesn't contain a connection to the final
					 * destination, so add it to the list of nodes we already
					 * visited
					 */
					visited.add(nextConNode);

				} // end while for current node's departing flights

			} // end if for no direct flight

		}
		/* Exceeded two connections, so return false */
		else {
			found = false;
		}

		/* Return the result */
		return found;
	}
	
	/* Returns all the possible routes from one airport to another */
	private ArrayList<LinkedList<Edge>> getRoutes(Node depNode, Node arrNode, ArrayList<Node> visited, Node originDepNode, int depth) {

		/* List to hold all the routes */
		ArrayList<LinkedList<Edge>> routes = new ArrayList<LinkedList<Edge>>();

		/* If > 3, then too many connections,
		 * return an empty route list */
		if (depth > 3){
			return routes;
		}
		/* We haven't exceeded the maximum connections yet, 
		 * keep searching */
		else{
			
			/* Get a list of all the flights leaving the departure airport */
			Iterator<Edge> depNodeFlights = depNode.getEachLeavingEdge().iterator();

			/*
			 * While there are flights left to check from the departing airport
			 */
			while (depNodeFlights.hasNext()) {

				/* Get the next flight that leaves this airport */
				Edge flight = depNodeFlights.next();
				
				/* Making sure that original departure not the starting node */
				if (originDepNode.equals(flight.getSourceNode())) {
					visited.clear();
					visited.add(originDepNode);
				}

				/*
				 * If where you would land is not in the places that were checked
				 * before
				 */
				if (!visited.contains(flight.getTargetNode())) {

					/*
					 * If this flight lands at the our final destination, then we
					 * know it is a route
					 */
					if (flight.getTargetNode().equals(arrNode)) {

						/* Make a linked list to store the route */
						LinkedList<Edge> currentRoute = new LinkedList<Edge>();

						/* Add the flight to the current route */
						currentRoute.add(flight);

						/* Add the current route to the routes list */
						routes.add(currentRoute);

					}
					/*
					 * There are no direct flights, so let's check for connections
					 */
					else {

						/* Increase the number of connections we have
						 * gone to so far by 1. */
						depth++;
						
						/* Add this departure node to the visited list */
						visited.add(depNode);

						/* Store all the routes from the connection airport */
						ArrayList<LinkedList<Edge>> returnedRoutes = getRoutes(flight.getTargetNode(), arrNode, visited, originDepNode, depth);

						for (LinkedList<Edge> route : returnedRoutes) {

							/*
							 * Add the route from the connecting flight to the
							 * original route
							 */
							route.addFirst(flight);

							/* Make a linked list to store the route */
							LinkedList<Edge> addedRoute = new LinkedList<Edge>();

							/* Make the new Linked List */
							addedRoute = route;

							/* Add the concatenated route to the routes list */
							routes.add(addedRoute);

						}

					}
					
					/* Once we find our destination, we have to set
					 * depth back to 0 for the next depth search for flight */
					depth = 0;
					
				}

			}

			return routes;
			
		}
		
	}
	
	/* Returns a new list routes, that contain only routes that have seats and 
	 * are chronologically possible */
	private ArrayList<LinkedList<Edge>> routeFilter(ArrayList<LinkedList<Edge>> routes, boolean isFirstClass){
		
		ArrayList<LinkedList<Edge>> filteredRoutes = new ArrayList<LinkedList<Edge>>();
			
		/* Go through all the routes in the list */
		for (LinkedList<Edge> route : routes){
			
			/* If the route is in chronological order
			 * and the all the flights in the route has available seats */ 
			if ( isRouteTimeValid(route) && isSeatAvail(route, isFirstClass) ){
				filteredRoutes.add(route);
			}
			
		}
		
		/* Return the new filtered list */
		return filteredRoutes;
		
	}
	
	/* Checks if all the flights have available seats */
	private boolean isSeatAvail(LinkedList<Edge> route, boolean isFirstClass){
		
		for (Edge flight : route){
			
			FlightLeg flightInfo = flight.getAttribute("fltInfo");
			
			/* Check the first class seats */
			if(isFirstClass){
				
				/* If all the seats on the plane are taken
				 * this flight can't be booked */
				if(!flightInfo.areFirstSeatsAvail()){
					return false;
				}
				
			}
			/* Check the coach seats */
			else{
				
				/* If all the seats on the plane are taken
				 * this flight can't be booked */
				if(!flightInfo.areCoachSeatsAvail()){
					return false;
				}
				
			}		
		}
		
		/* If it made it through the for loop, 
		 * then it means all the flights are valid */
		return true;	
	}
	
	/* Returns true if the route is less than than maximum allowed flights 
	 * and if in the route are in chronological order */
	private boolean isRouteTimeValid(LinkedList<Edge> route) {

		/* If the route size is 1, i.e. it 
		 * is a direct flight, return true */
		if(route.size() == 1 ){
			return true;
		}
		/* Check if the connections are in chronological order */
		else{
			
			/* Go through the linked list */
			for (int i = 0; i < (route.size() - 1); i++) {
				
				/* Get the flights' info */
				Edge flightLeg = route.get(i);
				FlightLeg fltInfo = flightLeg.getAttribute("fltInfo");
				Date fltInfoDate = fltInfo.getArrivalDate();
				Time fltInfoTime = fltInfo.getArrivalTime();
				
				Edge flightLegNxt = route.get(i+1);
				FlightLeg fltNxtInfo = flightLegNxt.getAttribute("fltInfo");
				Date fltNxtInfoDate = fltNxtInfo.getDepartureDate();
				Time fltNxtInfoTime = fltNxtInfo.getDepartureTime();
				
				/* If both flights land on the same day, 
				 * then check if the next flight leaves after
				 * the current flight lands. */
				if (fltInfoDate.compareTo(fltNxtInfoDate) == 0){
					
					/* If this flight arrives after the next flight leaves */
					if(fltInfoTime.compareTo(fltNxtInfoTime) >= 0){
						return false;
					}
				
				}
				/* If the next flight lands on the day after 
				 * (crosses 23:59 GMT), then it's still valid,
				 * so don't do anything and check the next flight in the chain */
				else if (fltInfoDate.compareTo(fltNxtInfoDate) < 0){
					
					// lands on the next day, so it's valid
					
				}
				/* If the next flight lands on a date that is after the arrival
				 * date, which is impossible */
				else{
					return false; 
				}
			
			}
			
			/* The flights are in chronological order */
			return true;
		}
		
	}
	
}

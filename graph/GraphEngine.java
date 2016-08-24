package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.graphstream.graph.*;

import flight_system.Airport;
import flight_system.Date;
import flight_system.FlightLeg;
import flight_system.Location;
import flight_system.Time;

/** 
 * Class used to get flight and routes information from the flight graph.
 * <p>
 * Provides methods for finding direct fights, finding out if there is
 * a route between airports, and getting all the routes from an airport. 
 * 
 * @author Kun Huang
 */
public class GraphEngine implements IFlightGraph{
	
	Graph flightGraph;
	
	/**
	 * Makes an engine that will operate on a specific graph.
	 * <p>
	 *
	 * @param flightGraph the graph that will be analyzed.
	 * @see GraphMaker
	 */
	/* Constructor */
	public GraphEngine(Graph flightGraph) {
		this.flightGraph = flightGraph;
	} 
		
	/**
	 * Tells you if there is a direct route between two airports.
	 * <p>
	 *
	 * @param depPort the departure airport
	 * @param arrPort the arrival airport
	 * @return true if there is a direct flight
	 * @see flight_system.Airport
	 */
	/* Tells if there's a direct flight */
	public boolean hasDirectFlight(Airport depPort, Airport arrPort){
		
		/* Convert Airports to Nodes */
		Node depNode = this.flightGraph.getNode(depPort.getCode());
		Node arrNode = this.flightGraph.getNode(arrPort.getCode());
		
		return depNode.hasEdgeToward(arrNode);
		
	}
	
	/**
	 * Tells you if there is a route between two airports.
	 * <p>
	 * Note: This method assumes a maximum of 3 flights (2 connections).
	 * 
	 * @param depPort the departure airport
	 * @param arrPort the arrival airport
	 * @param isFirstClass true if it's a first class flight
	 * @return true if there is a route
	 * @see flight_system.Airport
	 */
	/* The interface for the engine to tell if there's a route between two airport */
	public boolean hasRoute(Airport depPort, Airport arrPort, boolean isFirstClass){
				
		/* Assume a maximum of 3 flights */
		int maxFlights = 3;
		
		/* Get all the routes */
		ArrayList<LinkedList<Edge>> routes = getRoutes(depPort, arrPort, maxFlights, isFirstClass);
		
		/* If the route list is not empty, then there are flights */
		return (!routes.isEmpty());
		
	}
	
	/**
	 * Tells you if there is a route in a general direction between two airports.
	 * <p>
	 * Note: This method assumes a maximum of 3 flights (2 connections).
	 * 
	 * @param depPort the departure airport
	 * @param arrPort the arrival airport
	 * @param isFirstClass true if it's a first class flight
	 * @return true if there is a route in a general direction
	 * @see flight_system.Airport
	 */
	/* The interface for the engine to tell if there's a route between two airport */
	public boolean hasRouteDirection(Airport depPort, Airport arrPort, boolean isFirstClass){
				
		/* Assume a maximum of 3 flights */
		int maxFlights = 3;
		
		/* Get all the routes */
		ArrayList<LinkedList<Edge>> routes = getRoutesDir(depPort, arrPort, maxFlights, isFirstClass);
		
		/* If the route list is not empty, then there are flights */
		return (!routes.isEmpty());
		
	}
	
	/**
	 * Gets all the routes between two airports, 
	 * with a provided number of flights.
	 * <p>
	 * You must specify the maximum of flights the route can have. 
	 * 
	 * @param depPort the departure airport
	 * @param arrPort the arrival airport
	 * @param maxFlights the maximum number of flights the route can have
	 * @param isFirstClass true if looking for First Class flights
	 * @return a list of all the possible routes
	 */
	public ArrayList<LinkedList<Edge>> getRoutes(Airport depPort, Airport arrPort, int maxFlights, boolean isFirstClass){
		
		/* Convert Airports to Nodes */
		Node depNode = getNode(depPort);
		Node arrNode = getNode(arrPort);
		
		/* The list of routes to be returned */
		ArrayList<LinkedList<Edge>> routes = new ArrayList<LinkedList<Edge>>();
		
		/* Call the private method */
		routes = getRoutes(depNode, arrNode, new ArrayList<Node>(), depNode, 0);
		
		/* Filter out the flights based on max flights, but not by a general direction*/
		routes = routeFilter(routes, maxFlights, false, isFirstClass);
		
		return routes;
		
	}
	
	/**
	 * Gets all the routes in a general direction between two airports, 
	 * with a provided number of flights. You must specify the maximum of flights the route can have. 
	 * <p>
	 * The routes returned will be heading towards the final destination
	 * in some logical manner. 
	 * <p>
	 * E.g. ORD (Chicago) -> SFO (San Fransico) would 
	 * go in a western direction. 
	 * 
	 * @param depPort the departure airport
	 * @param arrPort the arrival airport
	 * @param maxFlights the maximum number of flights the route can have
	 * @param isFirstClass true if you are searching for First Class flights
	 * @return a list of all the possible routes in that general direction.
	 */
	/* Gets all the routes in a general direction */
	public ArrayList<LinkedList<Edge>> getRoutesDir(Airport depPort, Airport arrPort, int maxFlights, boolean isFirstClass){
		
		/* Convert Airports to Nodes */
		Node depNode = getNode(depPort);
		Node arrNode = getNode(arrPort);
		
		/* The list of routes to be returned */
		ArrayList<LinkedList<Edge>> routes = new ArrayList<LinkedList<Edge>>();
		
		/* Call the private method */
		routes = getRoutes(depNode, arrNode, new ArrayList<Node>(), depNode, 0);
		
		/* Filter out the flights based on max flights, and direction */
		routes = routeFilter(routes, maxFlights, true, isFirstClass);
		
		return routes;
		
	}
	/**
	 * Used to get the node on the graph that is represented by the airport.
	 * <p>
	 * Only really useful within the graph package for testing.
	 * 
	 * @param airport
	 * @return the node on the graph that is associated with the airport
	 */
	/* Get the node corresponding to an airport */
	protected Node getNode(Airport airport){
		return this.flightGraph.getNode(airport.getCode());
	}

	/* Determines if there's a route between two nodes, maximum of 2 connections */
	@SuppressWarnings("unused")
	private boolean timeHasRoute(Node depNode, Node arrNode, int con, Edge conEdge, ArrayList<Node> visited) {
		
		int maxCon = 3; // maximum 2 connections
		boolean found = false; // holds the result
		FlightLeg conEdgeInfo = null;
		
		/* If we've been to this node already, we no it's not a route */
		if (visited.contains(depNode) ){
			
			/* If this is a connection check, add this departing node to visited list */
			if (!(conEdge == null)){
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
				 * If connecting edge is null, this means 
				 * that this is the 1st check
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
						
						boolean beenHere = visited.contains(nxtFlt.getTargetNode());
						
						/*
						 * Does this flight leave after the previous one
						 * arrives, and it doesn't land where we have passed through already
						 */
						if (nxtFltInfo.getDepartureTime().getTimeInMinutes() > conEdgeInfo.getArrivalTime().getTimeInMinutes() && !beenHere){
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
					Iterator<Edge> nxtDepFlights = nextConNode.getLeavingEdgeIterator();
					
					/* While the 1st connection has flights to check, and doesn't
					 * land at the airport we're trying to leave from
					 */
					while (nxtDepFlights.hasNext()) {
						
						Edge conFlt2nd = nxtDepFlights.next(); // the potential 2nd flight
						
						if( !(conFlt2nd.getTargetNode().equals(depNode)) ){
							/* Get the flight info of the next potential flight */
							FlightLeg conFlt2ndInfo = conFlt2nd.getAttribute("fltInfo"); 
																
							/*
							 * If the next connecting edge leaves after the previous
							 * edges arrives then it is possible that the edge will
							 * get us to our final destination
							 */
							if (conFlt2ndInfo.getDepartureTime().getTimeInMinutes() > depFltsInfo.getArrivalTime().getTimeInMinutes()) {
		
								con++; // add one to the connections
								
								/* Where the connecting flight lands */
								Node conFltLand = conFlt2nd.getTargetNode(); 
								
								/*
								 * If the potential flight lands at our final
								 * destination, then we have found the route
								 */
								if (conFltLand.equals(arrNode) && (con < (maxCon - 1))) {
									found = true;
									break; // stop checking for routes
								}
								/*
								 * Check to see if where that flight lands, has a
								 * connection to the final destination
								 */
								else {
									
									/* Add the port where the 1st flight landed, to the visited list */
									visited.add(nextConNode);
									
									if (timeHasRoute(conFltLand, arrNode, con, conFlt2nd, visited)) {
										found = true;
										break;
									}
									
									
								}
		
							}
						
						} // end if the connection doesn't land where we started from
					
					} // end while for next node's departing flights
						
					/* We checked the node where the first flight would
					 * have landed, but it doesn't contain a connection to
					 * the final destination, so add it to the list of nodes 
					 * we already visited */
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
	
	/* Returns a new list routes, that contain only routes that are 
	 * chronologically possible and have available seats */
	private ArrayList<LinkedList<Edge>> routeFilter(ArrayList<LinkedList<Edge>> routes, int maxFlights, boolean filterDir, boolean isFirstClass){
		
		ArrayList<LinkedList<Edge>> filteredRoutes = new ArrayList<LinkedList<Edge>>();
			
		/* Go through all the routes in the list */
		for (LinkedList<Edge> route : routes){
			
			/* If filter by direction is true */
			if (filterDir){
				
				/* If the route is in chronological order and 
				 * all the flights have available seats */
				if ( isRouteTimeValid(route, maxFlights) && isSeatAvail(route,isFirstClass) ){
					
					/* Is the route in a general direction */
					if (isRouteDirValid(route)){
						filteredRoutes.add(route);
					}
					
					
				}
				
			}
			/* If filter by direction is false */
			else{
				
				/* If the route is valid, add it to the filtered list */
				if ( isRouteTimeValid(route, maxFlights)){
					filteredRoutes.add(route);
				}
				
			}
			
		}
		
		/* Return the new filtered list */
		return filteredRoutes;
		
	}
	
	/* Returns true if the route is less than than maximum allowed flights 
	 * and if in the route are in chronological order */
	private boolean isRouteTimeValid(LinkedList<Edge> route, int maxFlights) {
	
		/* If the route size is 1, i.e. it 
		 * is a direct flight, return true */
		if(route.size() == 1 ){
			return true;
		}
		/* If the route has more than maximum number of flights,
		 * i.e more than 2 connections, then it's not valid
		 */
		else if (route.size() > maxFlights){
			return false;
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

	/* Is the route going in a general direction? */
	private boolean isRouteDirValid(LinkedList<Edge> route) {
		
		/* If the route size is 1, i.e. it 
		 * is a direct flight, return true */
		if(route.size() == 1 ){
			return true;
		}
		/* Check if the connections are in geographical order */
		else{
			
			/* Go through the linked list */
			for (int i = 0; i < (route.size() - 1); i++) {
				
				/* Get the departure location */
				FlightLeg firstFltInfo = route.getFirst().getAttribute("fltInfo");
				Location depLocation = firstFltInfo.getDepartureAirport().getLocation();
				
				/* Get the arrival location */
				FlightLeg lastFltInfo = route.getLast().getAttribute("fltInfo");
				Location arrLocation = lastFltInfo.getArrivalAirport().getLocation();
				
				/* Get the flights' info */
				Edge flightLeg = route.get(i);
				FlightLeg fltInfo = flightLeg.getAttribute("fltInfo");
				Location depPortLoc = fltInfo.getDepartureAirport().getLocation();
				
				Edge flightLegNxt = route.get(i+1);
				FlightLeg fltNxtInfo = flightLegNxt.getAttribute("fltInfo");
				Location arrPortLoc = fltNxtInfo.getDepartureAirport().getLocation();
				
				/* Get the distance between the origin and final destination */
				double width = Math.abs(depLocation.getLongitude() - arrLocation.getLongitude());
				double height = Math.abs(depLocation.getLatitude() - arrLocation.getLatitude());
				
				/* If the distance between the origin and destination
				 * is longer longitudinally, then check by latitude 1st. */
				if (width > height){
					
					/* Check by latitude 1st */
					if(!isRightLatDir(depLocation, arrLocation, depPortLoc, arrPortLoc)){
						return false;
					}
					else{
						/* return false if you're not going in the right Longtitude direction */
						if(!(isRightLongDir(depLocation, arrLocation, depPortLoc, arrPortLoc))){
							return false;
						}
					}
				
				}
				/* It is longer latitudinally, check longitude first */
				else{
					
					/* Check by longitude 1st */
					if(!isRightLongDir(depLocation, arrLocation, depPortLoc, arrPortLoc)){
						return false;
					}
					else{
						
						/* return false if you're not going in the right Latitude direction */
						if(!(isRightLatDir(depLocation, arrLocation, depPortLoc, arrPortLoc))){
							return false;
						}
					}
					
				}
									
			}
			
			/* The flights are in the right direction  */
			return true;
			
		}
		

	}
	
	/* Going in the correct Latitude direction? */
	private boolean isRightLatDir(Location depLocation, Location arrLocation,Location depPortLoc, Location arrPortLoc) {
		
		/* Check if we're heading south */
		if (arrLocation.getLatitude() <= depLocation.getLatitude()){

			/* If the arriving airport is more north than departing airport 
			 * then we're not heading in the right direction */
			if(arrPortLoc.getLatitude() > depPortLoc.getLatitude()){
				return false;
			}
			else{
				return true;
			}
			
		}
		/* We're heading north */
		else{
			
			/* If the arriving airport is more south than departing airport 
			 * then we're not heading in the right direction */
			if(arrPortLoc.getLatitude() < depPortLoc.getLatitude()){
				return false;
			}
			else{
				return true; 
			}
			
		}
	}
	
	/* Going in the correct Longitude direction? */
	private boolean isRightLongDir(Location depLocation, Location arrLocation,Location depPortLoc, Location arrPortLoc) {
		
		/* Check if we're heading west */
		if (arrLocation.getLongitude() <= depLocation.getLongitude()){

			/* If the arriving airport is more west than departing airport 
			 * then we're not heading in the right direction */
			if(arrPortLoc.getLongitude() > depPortLoc.getLongitude()){
				return false;
			}
			else{
				return true;
			}
			
		}
		/* We're heading east */
		else{
			
			/* If the arriving airport is more east than departing airport 
			 * then we're not heading in the right direction */
			if(arrPortLoc.getLongitude() < depPortLoc.getLongitude()){
				return false;
			}
			else{
				return true; 
			}
			
		}
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
	
}

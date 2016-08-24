package graph;

import java.util.ArrayList;
import java.util.LinkedList;

import org.graphstream.graph.Edge;

import flight_system.Airport;

/** 
 * Interface used to specify what a Flight Graph engine needs to at least do.
 * <p>
 * The engine should be used to get flight and routes information from a flight graph.
 * <p>
 * The engine should provides methods for finding direct fights, finding out if there is
 * a route between airports, and getting all the routes from an airport. 
 * 
 * @author Kun Huang
 */

public interface IFlightGraph {
	
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
	public boolean hasDirectFlight(Airport depPort, Airport arrPort);
	
	/**
	 * Tells you if there is a route between two airports.
	 * <p>
	 * Note: This method assumes a maximum of 3 flights (2 connections).
	 * 
	 * @param depPort the departure airport
	 * @param arrPort the arrival airport
	 * @param true if it's a first class flight
	 * @return true if there is a route
	 * @see flight_system.Airport
	 */
	/* Tells if there's a route between two airports */
	public boolean hasRoute(Airport depPort, Airport arrPort, boolean isFirstClass);
	
	/**
	 * Gets all the routes between two airports, 
	 * with a provided number of flights.
	 * <p>
	 * You must specify the maximum of flights the route can have. 
	 * 
	 * @param depPort the departure airport
	 * @param arrPort the arrival airport
	 * @param maxFlights the maximum number of flights the route can have
	 * @param isFirstClass true if it's a first class flight
	 * @return a list of all the possible routes
	 */
	/* Gets all the routes between two airports */
	public ArrayList<LinkedList<Edge>> getRoutes(Airport depPort, Airport arrPort, int maxFlights, boolean isFirstClass);
	
}

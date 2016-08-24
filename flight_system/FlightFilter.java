package flight_system;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class used to represent a flight filter based on flights.
 **/

public class FlightFilter {
	private ArrayList<Flight> flightList;
	
	/**
	 * Makes a flight filter without any flights in the list.
	 */
	public FlightFilter(){
		this.flightList = new ArrayList<Flight>();
	}
	
	/**
	 * Creates an flight filter object with flights in the list.
	 */
	public FlightFilter(ArrayList<Flight> flightList){
		this.flightList  = flightList;
	}
	
	
	/**
	 * Adds the given list to this flight list. Cleans the previous
	 * information 1st. 
	 * @param flightList the flight list to be added to the filter.
	 */
	public void addFlightList(ArrayList<Flight> flightList) {
		
		/* Clean this list 1st */
		this.flightList.clear();
		
		this.flightList.addAll(flightList);
		
	}

	/**
	 * Gets the original list of flights that the filter has.
	 * @return the original list of flights that the filter has.
	 */
	public ArrayList<Flight> getFlightList() {
		return flightList;
	}

	/**
	 * Add a flight to flight filter
	 */
	public void addFlight(Flight flight){
		this.flightList.add(flight);
	}
	
	/**
	 * This method returns the cheapest flight from the flight list.
	 * @param isFirstClass true if you want the flight with the cheapest
	 * first class seat. False if you want the flight with the cheapest coach
	 * class seat.
	 * @return the cheapest flight in the list.
	 */
	public Flight cheapestFlight(boolean isFirstClass){
		
		Flight cheapestFlight = flightList.get(0);
		for(Flight flight : flightList){
			if(flight.getTotalCost(isFirstClass)<cheapestFlight.getTotalCost(isFirstClass))
				cheapestFlight=flight;
		}
		return cheapestFlight;
	}
	
	/**
	 * This method returns the shortest flight time from the list
	 * @return The shortest flight in the list
	 */
	public Flight shortestFlightTime(){
		Flight shortestFlight = flightList.get(0);
		for(Flight flight : flightList){
			if(flight.getTotalFlightTime().getTimeInMinutes()<shortestFlight.getTotalFlightTime().getTimeInMinutes())
				shortestFlight=flight;
		}
		return shortestFlight;
	}

	/**
	 * This method returns the minimum lay over time.
	 * @return the minimum lay over time in the list
	 */
	public Flight minLayover(){
		Flight minLayover = flightList.get(0);
		for(Flight flight : flightList){
			if(flight.getTotalLayoverTime().getTimeInMinutes()<minLayover.getTotalLayoverTime().getTimeInMinutes())
				minLayover=flight;
		}
		return minLayover;
	}
	
	/** 
	 * This method returns a flight list that has only flights
	 * that leave after or before a given time (inclusive). 
	 * @param depTime the time for the departure
	 * @param after true if you want flights that leave after a certain time (inclusive)
	 * @return the filtered flight list.
	 */
	public ArrayList<Flight> filterDepTime(Time depTime, boolean after, Airport airport){
		

		/* Make a new list to add the filtered flights to */
		ArrayList<Flight> filteredFlights = new ArrayList<Flight>();
		
		/* Check every flight in the list to see if it matches the 
		 * criteria */
		for (Flight flight : flightList){

			/* Check flights that leave after specified time */
			if(after){

				/* Leaves after */
				if( flight.getDepartureTime().compareTo(depTime) >= 0 ){
					filteredFlights.add(flight);
				}

			}
			/* Check flights that leave before specified time */
			else{

				/* Leaves before */
				if( flight.getDepartureTime().compareTo(depTime) <= 0 ){
					filteredFlights.add(flight);
				}


			}

		}
		
		return filteredFlights;
		
	}
	
	/** 
	 * This method returns a flight list that has only flights
	 * that leave after or before a given time (inclusive). 
	 * @param depTime the local time for the departure
	 * @param after true if you want flights that leave after a certain time (inclusive)
	 * @return the filtered flight list.
	 */
	public ArrayList<Flight> filterLocalDepTime(Time depLocalTime, boolean after, Airport airport){
		

		/* Make a new list to add the filtered flights to */
		ArrayList<Flight> filteredFlights = new ArrayList<Flight>();
		
		/* Check every flight in the list to see if it matches the 
		 * criteria */
		for (Flight flight : flightList){

			/* Check flights that leave after specified time */
			if(after){
				
				/* Leaves after */
				if( flight.getLocalDepartureTime().compareTo(depLocalTime) >= 0 ){
					filteredFlights.add(flight);
				}

			}
			/* Check flights that leave before specified time */
			else{

				/* Leaves before */
				if( flight.getLocalDepartureTime().compareTo(depLocalTime) <= 0 ){
					filteredFlights.add(flight);
				}


			}

		}
		
		return filteredFlights;
		
	}
	
	
	/** 
	 * This method returns a flight list that has only flights
	 * that leave after or before a given date. 
	 * @param depDate the time for the departure
	 * @param after true if you want flights that leave after a certain time (inclusive)
	 * @return the filtered flight list.
	 */
	public ArrayList<Flight> filterDepLocalDate(Date depDate, boolean after){
		
		/* Make a new list to add the filtered flights to */
		ArrayList<Flight> filteredFlights = new ArrayList<Flight>();
		
		/* Check every flight in the list to see if it matches the 
		 * criteria */
		for (Flight flight : flightList){

			/* Check flights that leave after specified time */
			if(after){

				/* Leaves after */
				if( flight.getLocalDepartureDate().compareTo(depDate) >= 0 ){
					filteredFlights.add(flight);
				}

			}
			/* Check flights that leave before specified time */
			else{

				/* Leaves before */
				if( flight.getLocalDepartureDate().compareTo(depDate) <= 0 ){
					filteredFlights.add(flight);
				}


			}

		}
		
		return filteredFlights;
		
	}
	
	
	/** 
	 * This method returns a flight list that has only flights
	 * that arrive after or before a given time (inclusive). 
	 * @param arrLocalTime the local time for the arrival
	 * @param after true if you want flights that arrive after a certain time (inclusive)
	 * @return the filtered flight list.
	 */
	public ArrayList<Flight> filterArrTime(Time arrTime, boolean after, Airport airport){
		
//		/* Convert from Local time to GMT time */
//		Time arrTime = Time.getGMTTime(arrLocalTime, airport.getLocation());

		/* Make a new list to add the filtered flights to */
		ArrayList<Flight> filteredFlights = new ArrayList<Flight>();
		
		/* Check every flight in the list to see if it matches the 
		 * criteria */
		for (Flight flight : flightList){

			/* Check flights that leave after specified time */
			if(after){

				/* Leaves after */
				if( flight.getArrivalTime().compareTo(arrTime) >= 0 ){
					filteredFlights.add(flight);
				}

			}
			/* Check flights that leave before specified time */
			else{

				/* Leaves after */
				if( flight.getArrivalTime().compareTo(arrTime) <= 0 ){
					filteredFlights.add(flight);
				}


			}

		}
		
		return filteredFlights;
		
	}
	
	/** 
	 * This method returns a flight list that has only flights
	 * that arrive after or before a given time (inclusive). 
	 * @param arrLocalTime the local time for the arrival
	 * @param after true if you want flights that arrive after a certain time (inclusive)
	 * @return the filtered flight list.
	 */
	public ArrayList<Flight> filterLocalArrTime(Time arrLocalTime, boolean after, Airport airport){
		
//		/* Convert from Local time to GMT time */
//		Time arrTime = Time.getGMTTime(arrLocalTime, airport.getLocation());

		/* Make a new list to add the filtered flights to */
		ArrayList<Flight> filteredFlights = new ArrayList<Flight>();
		
		/* Check every flight in the list to see if it matches the 
		 * criteria */
		for (Flight flight : flightList){

			/* Check flights that leave after specified time */
			if(after){

				/* Leaves after */
				if( flight.getLocalArrivalTime().compareTo(arrLocalTime) >= 0 ){
					filteredFlights.add(flight);
				}

			}
			/* Check flights that leave before specified time */
			else{

				/* Leaves after */
				if( flight.getLocalArrivalTime().compareTo(arrLocalTime) <= 0 ){
					filteredFlights.add(flight);
				}


			}

		}
		
		return filteredFlights;
		
	}
	
	
	/**
	 * This method sorts the flight list based on the departure time.
	 * @param ascending true if you want the sort price returned in ascending order
	 * false if you want the sort price returned in descending order.
	 * @return the sorted flights in a order decide by you
	 */
	public ArrayList<Flight> sortDepartTime(boolean ascending){

		ArrayList<Flight> sortedFlights = new ArrayList<Flight>();
		sortedFlights.addAll(this.flightList);

		Collections.sort(sortedFlights, Flight.DepartureTimeComparator);

		if (ascending){
			return sortedFlights;
		}
		else{
			Collections.reverse(sortedFlights);
			return sortedFlights;
		}
	}
	
	/**
	 * This method sorts the flight list based on the arrival time.
	 * @param ascending true if you want the sort price returned in ascending order
	 * false if you want the sort price returned in descending order.
	 * @return the sorted flights in a order decide by you
	 */
	public ArrayList<Flight> sortArriveTime(boolean ascending){

		ArrayList<Flight> sortedFlights = new ArrayList<Flight>();
		sortedFlights.addAll(this.flightList);

		Collections.sort(sortedFlights, Flight.ArrivalTimeComparator);

		if (ascending){
			return sortedFlights;
		}
		else{
			Collections.reverse(sortedFlights);
			return sortedFlights;
		}
	}
	
	/**
	 * This method sorts the flight list based on the price
	 * @param ascending true if you want the sort price returned in ascending order
	 * false if you want the sort price returned in descending order
	 * @param isFirstClass true if you want the sort price returned is first class
	 * false if you want the sort price returned is coach class
	 * @return the sorted flights in a order decide by you
	 */
	public ArrayList<Flight> sortPrice(boolean ascending, boolean isFirstClass){

		ArrayList<Flight> sortedFlights = new ArrayList<Flight>();
		sortedFlights.addAll(this.flightList);
		
		if(isFirstClass){
			Collections.sort(sortedFlights, Flight.FirstClassPriceComparator);
		}
		else
			Collections.sort(sortedFlights, Flight.CoachClassPriceComparator);

		if (ascending){
			return sortedFlights;
		}
		else{
			Collections.reverse(sortedFlights);
			return sortedFlights;
		}
	}
	
	/**
	 * This method returns the sorted flight list based on the duration of the flight.
	 * @param ascending true if you want the sorted returned in ascending order of time
	 * false if you want the sorted returned in descending order of time
	 * @return the sorted flights in a order decide by you
	 */
	public ArrayList<Flight> sortTime(boolean ascending){
		ArrayList<Flight> sortedFlights = new ArrayList<Flight>();
		sortedFlights.addAll(this.flightList);
		
		Collections.sort(sortedFlights);
		
		if (ascending){
			return sortedFlights;
		}
		else{
			Collections.reverse(sortedFlights);
			return sortedFlights;
		}
		
	}
	
	/**
	 * This method sorts the flight list based on the layover time
	 * @param ascending true if you want the sorted returned in ascending order of lay over time
	 * false if you want the sorted returned in descending order of lay over time
	 * @return the sorted flights in a order decide by you
	 */
	public ArrayList<Flight> sortLayover(boolean ascending){
		ArrayList<Flight> sortedFlights = new ArrayList<Flight>();
		sortedFlights.addAll(this.flightList);
		
		Collections.sort(sortedFlights, Flight.TotalLayoverComparator);
		
		if (ascending){
			return sortedFlights;
		}
		else{
			Collections.reverse(sortedFlights);
			return sortedFlights;
		}
	}
	
	/**
	 * This method sort the flight list based on the number of connections
	 * @param ascending true if you want the sort price returned in ascending order of number of connections
	 * false if you want the sort price returned in descending order of connections
	 * @return the sorted flights in a order decide by you
	 */
	public ArrayList<Flight> sortConnect(boolean ascending){
		ArrayList<Flight> sortedFlights = new ArrayList<Flight>();
		sortedFlights.addAll(this.flightList);
		
		Collections.sort(sortedFlights, Flight.ConnectionComparator);
		
		if (ascending){
			return sortedFlights;
		}
		else{
			Collections.reverse(sortedFlights);
			return sortedFlights;
		}
	}
	
	@Override
	public String toString() {
		return "the cheapest flight is: "+cheapestFlight(true)+".\n"+
				"the shortest flight time is: "+shortestFlightTime()+".\n"+
				"the minimum Layover is: "+minLayover()+".\n"+
				"FlightFilter [sortcheap()=" + sortPrice(true,true) + ", sortshortest()="
				+ sortTime(true) + ", sortLayover()=" + sortLayover(true)
				+ ", sortConnect()=" + sortConnect(true) + "]";
	}
	
}
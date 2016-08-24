package flight_system;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;

/** 
 * Class used to represent a flight. Contains information about the 
 * Origin Airport and final Airport, flight times, lay over
 * durations, and flight durations. 
 * <p>
 * A flight is a list of flight legs.
 * @see FlightLeg
 */
public class Flight implements Comparable<Flight>
{
	private ArrayList<FlightLeg> flightList;
	
	/**
	 * Makes a Flight object without any Flight Legs
	 * @see FlightLeg 
	 */
	public Flight(){
		this.flightList = new ArrayList<FlightLeg>();
	}
	
	/**
	 * Creates a flight object with one flight leg.
	 * @param flight1 the 1st flight leg in the list
	 */
	public Flight(FlightLeg flight1)
	{
		this.flightList = new ArrayList<FlightLeg>();
		this.flightList.add(flight1);
	}
	
	/**
	 * Creates a flight object with two flight legs.
	 * @param flight1 the 1st flight leg in the list
	 * @param flight2 the 2nd flight leg in the list
	 */
	public Flight(FlightLeg flight1, FlightLeg flight2)
	{
		this.flightList = new ArrayList<FlightLeg>();
		this.flightList.add(flight1);
		this.flightList.add(flight2);
	}
	
	/**
	 * Creates a flight object with three flight legs.
	 * @param flight1 the 1st flight leg in the list
	 * @param flight2 the 2nd flight leg in the list
	 * @param flight3 the 3nd flight leg in the list
	 */
	public Flight(FlightLeg flight1, FlightLeg flight2, FlightLeg flight3)
	{
		this.flightList = new ArrayList<FlightLeg>();
		this.flightList.add(flight1);
		this.flightList.add(flight2);
		this.flightList.add(flight3);
	}
	
	/**
	 * Gets the list of flights.
	 * @return the flight leg list.
	 */
	public ArrayList<FlightLeg> getFlightList() {
		return flightList;
	}

	/**
	 * Adds a FlightLeg object to this flight list.
	 * @see FlightLeg 
	 */
	public void addFlightLeg(FlightLeg flightLeg)
	{
		this.flightList.add(flightLeg);
	}
	
	/**
	 * Gets the original departure airport of the flight.
	 * @return the original departure airport.
	 */
	public Airport getDepartureAirport(){
		return flightList.get(0).getDepartureAirport();
	}
	
	/**
	 * Get a departure airport of specific flight leg
	 * @param indexOfLeg the index of the flight leg
	 * @return a departure airport of specific flight leg
	 */
	public Airport getDepartureAirport(int indexOfLeg)
	{
		return flightList.get(indexOfLeg).getDepartureAirport();
	}
	
	/**
	 * Gets the final arrival airport of the flight.
	 * @return the final arrival airport of the flight.
	 */
	public Airport getArrivalAirport(){
		return flightList.get(flightList.size()-1).getArrivalAirport();
	}
	
	/**
	 * Get a arrival airport of specific flight leg
	 * @param indexOfLeg the index of the flight leg
	 * @return a arrival airport of specific flight leg
	 */

	public Airport getArrivalAirport(int indexOfLeg)
	{
		return flightList.get(indexOfLeg).getArrivalAirport();
	}
	
	
	/** 
	 * Gets the original departure time for the flight.
	 *
	 * @return the original departure time.
	 */
	public Time getDepartureTime()
	{	
		return flightList.get(0).getDepartureTime();
	}
	
	/**
	 * Gets the origin departure local time for the flight.
	 * @return the local time at the origin.
	 */
	public Time getLocalDepartureTime(){
		return flightList.get(0).getLocalDepartureTime();
	}

	/**
	 * Get a departure time of specific flight leg.
	 * <p>
	 * If the leg does not exist, it returns 00:00 GMT.
	 * @param indexOfLeg the index of the flight leg
	 * @return the departure time of specific flight leg
	 */
	public Time getDepartureTime(int indexOfLeg)
	{	
		/* If they ask for a non-existent leg, then return 00:00 GMT */
		if (indexOfLeg >= flightList.size()){
			return new Time(0,0);
		}
		else {
			return flightList.get(indexOfLeg).getDepartureTime();
		}
		
	}
	
	/** 
	 * Gets the arrival time at the final destination.
	 * <p>
	 * Note that the times are in GMT.
	 * @return the arrival time at the final destination.
	 */
	public Time getArrivalTime()
	{	
		return flightList.get(flightList.size()-1).getArrivalTime();
	}
	
	/**
	 * Gets the arrival local time for the flight.
	 * @return the local time at the origin.
	 */
	public Time getLocalArrivalTime(){
		
		return flightList.get(flightList.size()-1).getLocalArrivalTime();
		
	}
	
	/**
	 * Get a arrival time of specific flight leg. 
	 * <p>
	 * If the leg does not exist, it returns 00:00 GMT.
	 * @param indexOfLeg the index of the flight leg
	 * @return a arrival time of specific flight leg
	 */
	public Time getArrivalTime(int indexOfLeg)
	{
		
		/* If they ask for a non-existent leg, then return 00:00 GMT */
		if (indexOfLeg >= flightList.size()){
			return new Time(0,0);
		}
		else {
			return flightList.get(indexOfLeg).getArrivalTime();
		}
	
	}
		
	/**
	 * Get a duration time of a specific flight leg
	 * @param Legindex the index of flight leg
	 * @return a duration time of a specific flight leg
	 */
	public Time getDurationTime(int LegIndex)
	{
			Date DepartureDate = flightList.get(LegIndex).getDepartureDate();
			Time DepartureTime = flightList.get(LegIndex).getDepartureTime();
			Date ArrivalDate = flightList.get(LegIndex).getArrivalDate();
			Time ArrivalTime = flightList.get(LegIndex).getArrivalTime();

		
			int flightDays = ArrivalDate.getDay() - DepartureDate.getDay();
			int flightHours = ArrivalTime.getHours() - DepartureTime.getHours();
		
			int Hours = flightDays * 24 + flightHours;
			int minutes = ArrivalTime.getMinutes() - DepartureTime.getMinutes();
			if (minutes < 0)
			{
				Hours = Hours - 1;
				minutes = 60 + minutes;
			}
			return new Time(Hours, minutes);	
	}
	
	
	/**
	 * get the total time of this flight object
	 * @return the total time of the flight
	 */
	public Time getTotalFlightTime()
	{	//get the days between the beginning and the end of travel
		int dday = flightList.get(0).getDepartureDate().getDay();
		int aday = flightList.get(flightList.size() - 1).getArrivalDate().getDay();
		int days = aday - dday;
		
		//get the hours between the beginning and the end of travel
		Time beginTime = flightList.get(0).getDepartureTime();
		Time endTime = flightList.get(flightList.size() - 1).getArrivalTime();
		int beginHour = beginTime.getHours();
		int endHour = endTime.getHours();
		int Hours = endHour - beginHour + days * 24;
		
		//get the minutes between the beginning and the end of travel	
		int beginMinute = beginTime.getMinutes();
		int endMinute = endTime.getMinutes();
		int minutes = endMinute - beginMinute;
		if(minutes < 0)
		{
			Hours = Hours - 1;
			minutes = 60 + minutes;
		}
		
		
		Time totalTime = new Time(Hours, minutes);	
		return totalTime;
	}

	/**
	 * get the total cost of this flight object
	 * @param isFirstClass true if you want the flight with first class seat, 
	 * false if you want the flight with coach lass seat
	 * @return the cost time of the flight
	 */
	public double getTotalCost(boolean isFirstClass)
	{	
		double totalCost = 0;
		int i;
		for(i = 0; i < this.flightList.size(); i++)
		{	//judge if the ticket is first class or coach lass
			if(isFirstClass)
			{
				totalCost = totalCost + this.flightList.get(i).getFirstClassPrice();
			}
			else{
				totalCost = totalCost + this.flightList.get(i).getCoachClassPrice();
			}
		}
		return totalCost;
	}
		
	/**
	 * get the connections of this flight object
	 * @return the connections of the flight
	 */
	public int getNumOfConnection()
	{
		int numOfConnection = this.flightList.size() - 1;
		return numOfConnection;
	}
	
	/**
	 * get the total lay over time of this flight object
	 * @return the total lay over time of the flight
	 */
	public Time getTotalLayoverTime()
	{
		// Lay over time is 0 by default 
		int totalLayoverMinutes = 0;
		
		if(flightList.isEmpty()){
			return new Time(0,0);
		}
		else if (flightList.size() == 1){
			return new Time(0,0);
		}
		else{
			
			for(int i = 0; i < flightList.size(); i++){
				totalLayoverMinutes = totalLayoverMinutes + this.getLayoverTime(i).getTimeInMinutes();
			}
			
		}
		
		//get the hours and minutes of the total lay over time
		int hours = totalLayoverMinutes / 60;
		int minutes = totalLayoverMinutes % 60;
		Time totalLayoverTime = new Time(hours, minutes);
		
		return totalLayoverTime;
	}

	/**
	 * get the lay over time of a connection
	 * @param layoverIndex the index of the connection
	 * @return the total time of the connection
	 */
	public Time getLayoverTime(int layoverIndex)
	{
		/* If you ask for a lay over time 
		 * that's more than the size of the list,
		 * return 0 minutes */
		if(layoverIndex >= (flightList.size()-1))
		{
			return new Time(0, 0);
		}
		/* Calculate the lay over time */
		else 
		{
			Date transferADate = flightList.get(layoverIndex).getArrivalDate();
			Date transferDDate = flightList.get(layoverIndex + 1).getDepartureDate();
			Time AtransferTime = flightList.get(layoverIndex).getArrivalTime();
			Time transferTimeD = flightList.get(layoverIndex + 1).getDepartureTime();
		
			int transferDays = transferDDate.getDay() - transferADate.getDay();
			int transferHours = transferTimeD.getHours() - AtransferTime.getHours();
		
			int Hours = transferDays * 24 + transferHours;
			int minutes = transferTimeD.getMinutes() - AtransferTime.getMinutes();
			if (minutes < 0)
			{
				Hours = Hours - 1;
				minutes = 60 + minutes;
			}
			return new Time(Hours, minutes);
		}	
	}
	
	/**
	 * Prints the simplified flight information in a
	 * user-friendly format.
	 */
	public void printFlight(boolean isFirstClass, boolean localTime){
		
		if(localTime){
			/* Convert flight time of the original departure and 
			 * final arrival from GMT to Local Time */
			Time originDepTime = Time.getLocalTime(getDepartureTime(), getDepartureAirport().getLocation());
			Time finalArrTime = Time.getLocalTime(getArrivalTime(), getArrivalAirport().getLocation());
			NumberFormat priceFormat = new DecimalFormat("#.00");   
			
			System.out.println(getDepartureAirport().getCode() + " -> " + getArrivalAirport().getCode() + "\tNum of stops: " + getNumOfConnection());
			System.out.println(originDepTime + " -> " + finalArrTime + "\t(Total Flight Time: " + getTotalFlightTime() + ")");
			System.out.println(getLocalDepartureDate() + " -> " + getLocalArrivalDate());
			System.out.println("Price: $" + priceFormat.format(getTotalCost(isFirstClass)) + "\t" + (isFirstClass ? "(First Class)": "(Coach)"));
			
		}
		else{
			
			NumberFormat priceFormat = new DecimalFormat("#.00");   
			
			System.out.println(getDepartureAirport().getCode() + " -> " + getArrivalAirport().getCode() + "\tNum of stops: " + getNumOfConnection());
			System.out.println(getDepartureTime() + " -> " + getArrivalTime() + "\t(Total Flight Time: " + getTotalFlightTime() + ")");
			System.out.println(getDepartureDate() + " -> " + getArrivalDate());
			System.out.println("Price: $" + priceFormat.format(getTotalCost(isFirstClass)) + "\t" + (isFirstClass ? "(First Class)": "(Coach)"));
			
		}
			
	}
	
	/**
	 * The date of departure 
	 * @return the departure date
	 */
	public Date getDepartureDate() {
		return flightList.get(0).getDepartureDate();
	}

	/**
	 * The local date of departure 
	 * @return the local departure date
	 */
	public Date getLocalDepartureDate() {
		return flightList.get(0).getLocalDepartureDate();
	}
	
	/**
	 * The date of arrival.
	 * @return the arrival date.
	 */
	public Date getArrivalDate() {
		return flightList.get(flightList.size()-1).getArrivalDate();
	}
	
	/**
	 * The local date of arrival.
	 * @return the arrival date
	 */
	public Date getLocalArrivalDate() {
		return flightList.get(flightList.size()-1).getLocalArrivalDate();
	}
	
	/**
	 * Prints the detailed flight information in a user-friendly format.
	 * @param isFirstClass 
	 */
	public void printDetailFlight(boolean isFirstClass, boolean localTime)
	{
		
		for (FlightLeg flight : flightList){
			
			if(localTime){
				/* Convert flight leg time from GMT to Local Time */
				Time depTime = Time.getLocalTime(flight.getDepartureTime(),flight.getDepartureAirport().getLocation());
				Time arrTime = Time.getLocalTime(flight.getArrivalTime(), flight.getArrivalAirport().getLocation());
				
				System.out.println("----------------------------------------------------------------------------------\n" +
				"Flight #: " + flight.getFlightNum() + "\n" +
				"Departs " + flight.getDepartureAirport().getCode() + " at " + depTime + 
				"\t\tArrives " + flight.getArrivalAirport().getCode() + " at " + arrTime +
				"\t\tDuration\n" + 
				flight.getLocalDepartureDate() + "\t\t\t" + flight.getLocalArrivalDate() +
				"\t\t\t" + Time.convertMinsToHours(flight.getFlightDuration()) +
				"\n----------------------------------------------------------------------------------");
				
				/* Index for the next flight */
				int nextFltInd = flightList.indexOf(flight) + 1;
				
				/* If there's a flight after this one */
				if(nextFltInd < (flightList.size())){
					
					/* Print the lay over time */
					System.out.println("\t\t\t\tLayover time: " + getLayoverTime(flightList.indexOf(flight)));
					
				}
				
			}
			else{
			
				System.out.println("----------------------------------------------------------------------------------\n" +
				"Flight #: " + flight.getFlightNum() + "\n" +
				"Departs " + flight.getDepartureAirport().getCode() + " at " + flight.getDepartureTime() + 
				"\t\tArrives " + flight.getArrivalAirport().getCode() + " at " + flight.getArrivalTime() +
				"\t\tDuration\n" + 
				flight.getDepartureDate() + "\t\t\t" + flight.getArrivalDate() +
				"\t\t\t" + Time.convertMinsToHours(flight.getFlightDuration()) +
				"\n----------------------------------------------------------------------------------");
				
				/* Index for the next flight */
				int nextFltInd = flightList.indexOf(flight) + 1;
				
				/* If there's a flight after this one */
				if(nextFltInd < (flightList.size())){
					
					/* Print the lay over time */
					System.out.println("\t\t\t\tLayover time: " + getLayoverTime(flightList.indexOf(flight)));
					
				}
				
			}

		}
		
	}

	/** 
	 * This compares the flight total time 
	 * 
	 * @param compareTotalTime give the total time you want to 
	 * compare with the total time you get in the flight class.
	 * 
	 */
	public int compareTo(Flight compareFlight)
	{
		int compareTotalTimeInMins = compareFlight.getTotalFlightTime().getTimeInMinutes();
		return getTotalFlightTime().getTimeInMinutes() - compareTotalTimeInMins;
	}
	
	
	/* This compares the departure time of the flight*/
	public static Comparator<Flight> DepartureTimeComparator = new Comparator<Flight> ()
	{
		public int compare(Flight flight1, Flight flight2)
		{
			Integer layoverTime1 = flight1.getDepartureTime(0).getTimeInMinutes();
			Integer layoverTime2 = flight2.getDepartureTime(0).getTimeInMinutes();
			return layoverTime1.compareTo(layoverTime2);
		}
	};

	/* This compares the arrival time of the flight*/
	public static Comparator<Flight> ArrivalTimeComparator = new Comparator<Flight> ()
	{
		public int compare(Flight flight1, Flight flight2)
		{
			Integer layoverTime1 = flight1.getArrivalTime().getTimeInMinutes();
			Integer layoverTime2 = flight2.getArrivalTime().getTimeInMinutes();
			return layoverTime1.compareTo(layoverTime2);
		}
	};
	
	/* This compares the flight total lay over time */

	public static Comparator<Flight> TotalLayoverComparator = new Comparator<Flight> ()
	{
		public int compare(Flight flight1, Flight flight2)
		{
			Integer layoverTime1 = flight1.getTotalLayoverTime().getTimeInMinutes();
			Integer layoverTime2 = flight2.getTotalLayoverTime().getTimeInMinutes();
			return layoverTime1.compareTo(layoverTime2);
		}
	};
	
	/* This compares the flight total price of first class */
	public static Comparator<Flight> FirstClassPriceComparator = new Comparator<Flight> ()
	{
		public int compare(Flight flight1, Flight flight2)
		{
			Double price1 = flight1.getTotalCost(true);
			Double price2 = flight2.getTotalCost(true);
			return price1.compareTo(price2);
		}
	};
	
	/* This compares the flight total time of coach class*/
	public static Comparator<Flight> CoachClassPriceComparator = new Comparator<Flight> ()
	{
		public int compare(Flight flight1, Flight flight2)
		{
			Double price1 = flight1.getTotalCost(false);
			Double price2 = flight2.getTotalCost(false);
			return price1.compareTo(price2);
		}
	};
	
	/* This compares the flight connections */
	public static Comparator<Flight> ConnectionComparator = new Comparator<Flight> ()
	{
		public int compare(Flight flight1, Flight flight2)
		{
			Integer connection1 = flight1.getNumOfConnection();
			Integer connection2 = flight2.getNumOfConnection();
			return connection1.compareTo(connection2);
		}
	};
	
	@Override
	public String toString(){
		return "This flight departs from " + getDepartureAirport().getName() + " at " + getDepartureTime() + "\n" +
		   "This flight arrives at " + getArrivalAirport().getName() + " at " + getArrivalTime() + "\n" +
		   "The total flight time is: " + getTotalFlightTime().getHours() + ":" + getTotalFlightTime().getMinutes() + "\n" +
		   "The First Class price: " + getTotalCost(true) + "\n" +
		   "The Coach Class price: " + getTotalCost(false) + "\n" +
		   "The number of connections are: " + getNumOfConnection() + "\n" +
		   "The total lay over time is " + getTotalLayoverTime() + "\n" +
		   "The lay over time for 1st connection is: " + getLayoverTime(0) + "\n" +
		   "The lay over time for the 2nd connection is: " + getLayoverTime(1) + "\n";
	}


}

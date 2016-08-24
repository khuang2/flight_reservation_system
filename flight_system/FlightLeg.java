package flight_system;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/** 
 * Class used to represent a flight leg based on the 
 * information provided by the XML files in the flight database system.
 * 
 * @author Kun Huang
 * @see parsers.XMLGetter#getFlightsXML(boolean, Airport, Date) 
 */

public class FlightLeg {
	
	/* The Fields */
	private Airplane airplane;
	private int flightNum;
	private int flightDuration;
	
	/* Departure Info */
	private Time departureTime;
	private Date departureDate;
	private Airport departureAirport;
	
	/* Arrival Info */
	private Time arrivalTime;
	private Date arrivalDate;
	private Airport arrivalAirport;
	
	/* Seating Info */
	private double firstClassPrice;
	private int firstClassSeatsOcc; // Number of seats occupied
	private double coachClassPrice;
	private int coachClassSeatsOcc; // Number of seats occupied
	
	/**
	 * Makes an object that represents a flight leg.
	 * <p>
	 * The flight leg data represents the data that is represented
	 * by the XML data that is on the database.
	 * 
	 * @param airplane the airplane that is used for the flight
	 * @param flightNum the flight number
	 * @param flightDuration the duration of the flight in minutes
	 * @param departureTime the departure time
	 * @param departureDate the departure date
	 * @param departureAirport the departure airport
	 * @param arrivalTime the arrival time
	 * @param arrivalDate the arrival date
	 * @param arrivalAirport the arrival airport
	 * @param firstClassPrice the price per first class seat
	 * @param firstClassSeatsOcc the the number of occupied first class seats
	 * @param coachClassPrice the price per coach class seat
	 * @param coachClassSeatsOcc the number of occupied coach class seats
	 */
	public FlightLeg(Airplane airplane, int flightNum, int flightDuration,
			Time departureTime, Date departureDate, Airport departureAirport,
			Time arrivalTime, Date arrivalDate, Airport arrivalAirport,
			double firstClassPrice, int firstClassSeatsOcc,
			double coachClassPrice, int coachClassSeatsOcc) {
	
		this.airplane = airplane;
		this.flightNum = flightNum;
		this.flightDuration = flightDuration;
		this.departureTime = departureTime;
		this.departureDate = departureDate;
		this.departureAirport = departureAirport;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.arrivalAirport = arrivalAirport;
		this.firstClassPrice = firstClassPrice;
		this.firstClassSeatsOcc = firstClassSeatsOcc;
		this.coachClassPrice = coachClassPrice;
		this.coachClassSeatsOcc = coachClassSeatsOcc;
	}

	/* The getter methods */
	
	/**
	 * Gets the airplane that is used on the flight.
	 * <p>
	 * @return the flight's airplane.
	 */
	public Airplane getAirplane() {
		return airplane;
	}
	
	/**
	 * Gets the flight number.
	 * <p>
	 * @return the flight number.
	 */
	public int getFlightNum() {
		return flightNum;
	}
	
	/**
	 * Gets the flight's duration (in minutes)
	 * <p>
	 * @return the flight's duration
	 */
	public int getFlightDuration() {
		return flightDuration;
	}
	
	/**
	 * Gets the flight's departure time.
	 * <p>
	 * Note: The time is in GMT
	 * @return the flight's departure time.
	 */
	public Time getDepartureTime() {
		return departureTime;
	}

	/**
	 * Gets the flight's local departure time.
	 * <p>
	 * Note: The time is the local time.
	 * @return the flight's departure time.
	 */
	public Time getLocalDepartureTime(){
		return Time.getNegativeLocalTime(getDepartureTime(), getDepartureAirport().getLocation());	
	}
	
	/**
	 * Gets the flight's departure date.
	 * <p>
	 * @return the flight's departure date.
	 */
	public Date getDepartureDate() {
		return departureDate;
	}
	
	/**
	 * Gets the flight's local departure date.
	 * <p>
	 * @return the flight's local departure date.
	 */
	public Date getLocalDepartureDate() {
		
		/* 12:00 AM, Midnight */
		Time zeroHours = new Time(0,0);
		
		/* If this departure time is before 12:00 AM */
		if(getLocalDepartureTime().compareTo(zeroHours) < 0){
			
			return new Date(departureDate.getMonth(), departureDate.getDay() - 1, departureDate.getYear());
			
		}
		else{
			return departureDate;
		}
		
	}

	/**
	 * Gets the flight's departure airport.
	 * <p>
	 * @return the flight's departure airport.
	 */	
	public Airport getDepartureAirport() {
		return departureAirport;
	}
	
	/**
	 * Gets the flight's arrival time.
	 * <p>
	 * Note: The time is in GMT
	 * @return the flight's arrival time.
	 */
	public Time getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Gets the flight's local arrival time.
	 * <p>
	 * Note: The time is the local time.
	 * @return the flight's arrival time.
	 */
	public Time getLocalArrivalTime(){
		return Time.getNegativeLocalTime(getArrivalTime(), getArrivalAirport().getLocation());	
	}
	
	/**
	 * Gets the flight's arrival date.
	 * <p>
	 * Note: The time is in GMT
	 * @return the flight's arrival date.
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}
	
	/**
	 * Gets the flight's local arrival date.
	 * <p>
	 * @return the flight's local arrival date.
	 */
	public Date getLocalArrivalDate() {
		
		/* 12:00 AM, Midnight */
		Time zeroHours = new Time(0,0);
		
		/* If this departure time is before 12:00 AM */
		if(getLocalArrivalTime().compareTo(zeroHours) < 0){
			
			return new Date(arrivalDate.getMonth(), arrivalDate.getDay() - 1, arrivalDate.getYear());
			
		}
		else{
			return arrivalDate;
		}
		
	}
	
	/**
	 * Gets the flight's arrival airport.
	 * <p>
	 * @return the flight's arrival airport.
	 */	
	public Airport getArrivalAirport() {
		return arrivalAirport;
	}
	
	/**
	 * Gets the price for each first class seat.
	 * <p>
	 * @return the price for each first class seat.
	 */	
	public double getFirstClassPrice() {
		return firstClassPrice;
	}
	
	/**
	 * Gets the number of first class seats that are occupied. 
	 * <p>
	 * @return the number of occupied first class seats.
	 */	
	public int getOccFirstClassSeats() {
		return firstClassSeatsOcc;
	}
	
	/**
	 * Gets the number of First Class seats that are on this flight. 
	 * <p> 
	 * @return the number of seats available on the flight. 
	 * <p>
	 * If the flight is full it will return 0.
	 * If the flight is over-packed, it will return the number of seats
	 * it is over packed by as a negative number. E.g. If the plane can
	 * only seat 10, yet the flight info says it has 12 seats booked, then
	 * it will return -2. 
	 */
	public int getFirstClassSeatsAvail(){
		return airplane.getFirstClassSeats() - firstClassSeatsOcc;
	}
	
	/**
	 * Checks if there are available first class seats.
	 * @return true if there are available first class seats. 
	 */
	public boolean areFirstSeatsAvail(){
		return (getFirstClassSeatsAvail() > 0);
	}
	
	/**
	 * Gets the price for each coach class seat.
	 * <p>
	 * @return the price for each coach class seat.
	 */	
	public double getCoachClassPrice() {
		return coachClassPrice;
	}
	
	/**
	 * Gets the number of coach class seats that are occupied. 
	 * <p>
	 * @return the number of occupied coach class seats.
	 */	
	public int getOccCoachClassSeats() {
		return coachClassSeatsOcc;
	}
	
	/**
	 * Gets the number of Coach seats that are on this flight. 
	 * <p> 
	 * @return the number of seats available on the flight. 
	 * <p>
	 * If the flight is full it will return 0.
	 * If the flight is over-packed, it will return the number of seats
	 * it is over packed by as a negative number. E.g. If the plane can
	 * only seat 10, yet the flight info says it has 12 seats booked, then
	 * it will return -2. 
	 */
	public int getCoachClassSeatsAvail(){
		return airplane.getCoachSeats() - coachClassSeatsOcc;
	}
	
	/**
	 * Checks if there are available coach class seats.
	 * @return true if there are available coach class seats. 
	 */
	public boolean areCoachSeatsAvail(){
		return (getCoachClassSeatsAvail() > 0);
	}
	
	/**
	 * Gets the information about the flight in a format thats
	 * more useful for the user.
	 * <p>
	 * The only difference between this and {@link #toString} is that
	 * this method displays how many seats are available on a flight,
	 * as opposed to the number of seats occupied.
	 * 
	 * @return a string that contains the information about the flight, 
	 * in a user-friendly format.
	 */
	public String userToString(){
		
		NumberFormat priceFormat = new DecimalFormat("#.00");     

		return "This flight leg number is: " + flightNum + " and the plane model is " + airplane.getModel() + ".\n" +
		"This flight leaves " + departureAirport.getName() + " at " + departureTime.toString() + ".\n" +
		"It arrives at " + arrivalAirport.getName() + " at " + arrivalTime.toString() + ".\n" + 
		"Available First Class seats: " + getFirstClassSeatsAvail() + " at $" + priceFormat.format(firstClassPrice) + " per seat.\n" +
		"Available Coach Class seats: " + getCoachClassSeatsAvail() + " at $" + priceFormat.format(coachClassPrice) + " per seat.\n";

	}
	
	/**
	 * The String representation of the object contains all the 
	 * information about the flight leg that would be useful for the 
	 * developer. If you want flight info that is more presentable for
	 * the end user, use the {@link #userToString} method.
	 * <p>
	 * @return the string representation of the object.
	 */	
	@Override
	public String toString() {
		
		NumberFormat priceFormat = new DecimalFormat("#.00");     
		
		return "This flight leg number is: " + flightNum + " and the plane model is " + airplane.getModel() + ".\n" +
			   "This flight leaves " + departureAirport.getName() + " at " + departureTime.toString() + ".\n" +
			   "It arrives at " + arrivalAirport.getName() + " at " + arrivalTime.toString() + ".\n" + 
			   "First Class seats: " + firstClassSeatsOcc + " at $" + priceFormat.format(firstClassPrice) + " per seat.\n" +
			   "Coach Class seats: " + coachClassSeatsOcc + " at $" + priceFormat.format(coachClassPrice) + " per seat.\n";
		
	}
	
	
}

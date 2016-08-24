package flight_system;

/** 
 * Class used to represent time in hours and minutes.
 * <p>
 * The class can either represent the time as GMT time or Local Time.
 * 
 * @author Kun Huang
 *  
 */

public class Time implements Comparable<Time>{
	private int hours;
	private int minutes;
	
	/**
	 * Makes an object that represent time
	 * @param hours the hour part in the time
	 * @param minutes the minute part in the time
	 */
	public Time(int hours, int minutes) {
		this.hours = hours;
		this.minutes = minutes;
	}
	
	/**
	 * Makes an object that represent local time
	 * @param time the time in GMT format
	 * @param location the location information where the local time we want to get
	 * @return local time that transformed from GMT time in 24-Hour format.
	 * 
	 * @see flight_system.Location#getTimeZoneOffset()
	 */
	/* Returns a Time Object with the local time that was given */
	public static Time getLocalTime(Time time, Location location) {
		int localHours = (time.getHours() + (int) location.getTimeZoneOffset() / 3600) % 24;
		
		/* Add 24 hours to make it positive if the localHours is less than zero.*/
		if(localHours < 0)
		{
			localHours += 24;
		}
		return new Time(localHours, time.getMinutes());
	}
	
	/**
	 * Returns a new object with the local time at at a given location.
	 * <p>
	 * Note: This time object may have -ive Hours, because this represents that the
	 * time at the location is on the previous day. 
	 * @param time
	 * @param location
	 * @return
	 */
	public static Time getNegativeLocalTime(Time time, Location location){

		int localHours = (time.getHours() + (int) location.getTimeZoneOffset() / 3600) % 24;
		return new Time(localHours, time.getMinutes());

	}
	
	/**
	 * Makes an object that represent GMT time
	 * @param time the time in local format
	 * @param location the location information where the GMT time we want to get
	 * @return local time that transformed from local time in 24-Hour format.
	 * 
	 * @see flight_system.Location#getTimeZoneOffset()
	 */
	/* Returns a Time Object with the GMT time that was given */
	public static Time getGMTTime(Time time, Location location) {
		int gmtHours = (time.getHours() - (int) location.getTimeZoneOffset() / 3600) % 24;
		/* Add 24 hours to make it positive if the gmtHours is less than zero.*/
		if(gmtHours < 0)
		{
			gmtHours += 24;
		}
		return new Time(gmtHours, time.getMinutes());
	}
	
	/**
	 * Returns a given time object as the 12-hour time.
	 * <p>
	 * Note: The time object that is returned doesn't specify whether
	 * the time is AM or PM. It is up to the client to gather that information
	 * from the original Time object.
	 * <p>
	 * @param time the time object to be converted from 24-hour format
	 * 		  to a 12-hour format. 
	 * @return the given time in a 12-hour format.  
	 */
	public static Time get12HourTime(Time time){
		
		return new Time(time.getHoursIn12(), time.getMinutes());
		
	}
	
	/**
	 * Converts from minutes to hours and minutes.
	 * @return a time object with the converted time.
	 */
	public static Time convertMinsToHours(int mins){
		
		return new Time(mins / 60, mins % 60);
		
	}
	
	/**
	 * Returns the hour part in the time format
	 * @return the hour part in given time
	 */
	
	public int getHours() {
		return hours;
	}
	
	/**
	 * Returns the minute part in the time format
	 * @return the minute part in given time
	 */
	
	public int getMinutes() {
		return minutes;
	}
	
	/**
	 * Represent the given time only use minutes 
	 * @return the given time in minute format
	 */
	/* this function returns the time in minutes */
	public int getTimeInMinutes(){
		return hours*60+minutes;
	}
	
	/**
	 * Change the hour part in time form 24-hour format to 12-hour format
	 * @return the hour part in 12-hour format
	 */
	
	/* this function returns the hours in 12 hour format */
	public int getHoursIn12(){
		if(hours>12)
		{
			return hours-12;
		}
		else{
			return hours;
		}
	}
	
	/**
	 * Tells if the standard time would be in AM or PM
	 * @return 'true' for AM, 'false' for PM
	 */
	/* this function returns true if it is AM */
	public boolean isAM(){
		return (hours < 12);
	}
	
	/**
	 * Compares a given time object to this time object. 
	 * <p>
	 * If the this time is after the compared time, 
	 * the result will be a positive integer.
	 * If this time is before the compared one, 
	 * the result will be a negative integer. 
	 * If they are the same, the result will be 0.
	 * <p>
	 * @param time the other time object to be compared to this time object. 
	 * @return a +ive or -ive integer, or 0.  
	 */
	@Override
	public int compareTo(Time compareTime) {
		
		int compareTimeInMins = compareTime.getTimeInMinutes(); 
		 
		/* ascending order, b/c this if this time is > the compared time
		 * the result will be a positive number */
		return getTimeInMinutes() - compareTimeInMins;
 
	}
	
	/**
	 * String representation of the Time object.
	 * <p>
	 * @return the string representation of this Time. 
	 */


	@Override
	public String toString() {
		/* Formats the time in HH:MM */
		return /*"The Time is: "+ */String.format("%02d", hours) + ":" + String.format("%02d", minutes);
	}
}

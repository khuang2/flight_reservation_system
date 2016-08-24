package flight_system;

/** 
 * Class used to store a user's trip and search criteria information.  
 */

public class UserInfo {
	
	private Airport departureAirport;
	private Airport arrivalAirport;
	private Date departureDate;
	private Date returnDate;
	private boolean isFirstClass;
	private boolean isRoundTrip;

	/**
	 * Makes an object that stores the user trip/flight information.
	 * <p>
	 * Used in the flight system, to keep the information about the 
	 * user's choices.
	 *
	 * @param departureAirport the airport the user want's to depart from.
	 * @param arrivalAirport the airport of the user's final destination.
	 * @param departureDate the date of departure.
	 * @param returnDate the date the user wants to return on.
	 * @param isFirstClass true if the user chose a 1st class flight.
	 * @param isRoundTrip true if the user selected a round trip.
	 * 
	 */
	public UserInfo(Airport departureAirport, Airport arrivalAirport,
					Date departureDate, Date returnDate,
					boolean isFirstClass, boolean isRoundTrip) {

		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureDate = departureDate;
		this.returnDate = returnDate;
		this.isFirstClass = isFirstClass;
		this.isRoundTrip = isRoundTrip;
	}

	/* Setter Methods */
	/**
	 * Sets the departure airport.
	 * @param departureAirport the departure airport.
	 */
	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	/**
	 * Sets the airport of the final destination.
	 * @param arrivalAirport the arrival airport at the final destination.
	 */
	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	
	/**
	 * Sets the return date.
	 * @param returnDate the date of return.
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * Sets the departure Date.
	 * @param departureDate the date of departure.
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	
	/**
	 * Set as true if the user selects first class flight option.
	 * @param isFirstClass true if the user wants a 1st class flight.
	 */
	public void setIsFirstClass(boolean isFirstClass) {
		this.isFirstClass = isFirstClass;
	}
	
	/**
	 * Set as true if the user want a round trip.
	 * @param isRoundTrip true if the user selects a round trip
	 */
	public void setIsRoundTrip(boolean isRoundTrip){
		this.isRoundTrip = isRoundTrip;
	}

	/* Getter Methods */
	/**
	 * Gets the departure airport.
	 * @return the departure airport.
	 */
	public Airport getDepartureAirport() {
		return departureAirport;
	}
	
	/**
	 * Gets the arrival airport at the final destination.
	 * @return the final destination airport.
	 */
	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	/**
	 * Get the departure date.
	 * @return the date of departure.
	 */
	public Date getDepartureDate() {
		return departureDate;
	}
	
	/**
	 * Get the return date.
	 * @return the return date.
	 */
	public Date getReturnDate() {
		return returnDate;
	}
	
	/**
	 * Get the user's seating class.
	 * @return true if the user selected 1st class seating.
	 */
	public boolean getIsFirstClass() {
		return isFirstClass;
	}
	
	/**
	 * Determine if the user selected a round trip.
	 * @return true if the user selected a round trip.
	 */
	public boolean getIsRoundTrip() {
		return isRoundTrip;
	}
	
	/**
	 * Prints the user information in a user-friendly format.
	 */
	public void printUserInfo(){
		System.out.println("\nDeparture Date: " + departureDate);
		System.out.println("Departure Airport: " + departureAirport.getCode());
		System.out.println("Arrival Airport: " + arrivalAirport.getCode());
		
		if(isRoundTrip){
			System.out.println("Return Date: " + returnDate);
			System.out.println("Departure Airport: " + arrivalAirport.getCode());
			System.out.println("Arrival Airport: " + departureAirport.getCode());
		}
			
		System.out.println("Class: " + (isFirstClass ? "First Class": "Coach"));
		System.out.println();
	}
	
	@Override
	public String toString() {

		String seatlevel;

		if(isFirstClass){
			seatlevel="first class";
		}
		else{
			seatlevel="coach";
		}

		return "This User will be leaving from "+departureAirport.getName()+", and arrives at " + arrivalAirport.getName() + " on "
		+ departureDate + ". The seat for this user is a " + seatlevel + " seat.";
	}


}

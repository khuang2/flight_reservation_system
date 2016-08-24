package user_interface;

/** 
 * Interface used to specify what a user interface for the flight system 
 * needs to at least do. Used to abstract the model (flight system) from the view
 * (the user interface). 
 * 
 */
public interface IUserInterface {
	
	/**
	 * Displays the welcome message.
	 */
	public void displayWelcomeMsg();
	
	/**
	 * Used to ask the user for the departure airport's 3-digit code.
	 * @return the departure airport as a string from the user.
	 */
	public String getDepartureAirport();
	
	/**
	 * Used to ask the user for the arrival airport's 3-digit code.
	 * @return the arrival airport as a string from the user.
	 */
	public String getArrivalAirport();
	
	/**
	 * Used to ask the user what date they want to leave.
	 * @return the departure date (day) as a string from the user.
	 */
	public String getLeavingDate();
	
	/**
	 * Ask user if they want to book a round trip. 
	 * @return the user's answer. Should be a "yes" or a "no".
	 */
	public String roundTrip();
	
	/**
	 * Used to ask the user what date they want to return.
	 * @return the return date (day) as a string from the user.
	 */
	public String getReturnDate();
	
	/**
	 * Used to ask the user if they want 1st class seating or not.
	 * <p>
	 * The user answer should start with a 'y' for yes
	 * and a 'n' for no.
	 * @return the "yes" or "no" as a string.
	 */
	public String isFirstClassSeat();
	
	/**
	 * Displays a message that tells the user how many flights 
	 * fit their search criteria.
	 * @param numOfFlights the number of available flights
	 */
	public void numOfFlights(int numOfFlights);
	
	/**
	 * Displays a message that tells the user there are no flights 
	 * that fit their search criteria.
	 */
	public void noFlights();
	
	/**
	 * Asks the user which flight they would like to book.
	 */
	public String bookFlight();
	
	/**
	 * Tells the user that their flight was bought.
	 */
	public void confirmFlight();
	
	/**
	 * Asks the user if they would like to book another flight.
	 */
	public String bookAnother();

	/**
	 * Tell the user goodbye and thanks for using the system.
	 */
	public void goodbyeMsg();
	
	/**
	 * Prints the flight index in a user-friendly format.
	 * @param index the index of the flight within the list.
	 */
	public void printFlightOption(int index);
	
	/**
	 * Asks the user if they want more detail about a
	 * specific flight.
	 * @return a "yes" or "no" String.
	 */
	public String wantDetail();
	
	/**
	 * Asks the user for the flight option that they want to 
	 * get detail about.
	 * @return a positive number, because it is an index in the flight list.
	 */
	public String getDetailFlight();
	
	/**
	 * Asks the user if they want to get detail about
	 * another flight.
	 * @return a "yes" or "no" String.
	 */
	public String getAnotherDetail();
	
	/**
	 * Tells the user what flight they selected. 
	 */
	public void userFlightChoice(int index);
	
	/**
	 * Tells the user the flight doesn't exist anymore 
	 */
	public void flightDisappear();
	
	/** 
	 * Tell the user that they can't buy the flight right now
	 * please wait a few minutes, or try back later.
	 */
	public void dataBaseLocked();
	
	/**
	 * Ask the user if they want to filter a flight.
	 * @return "yes" or "no" answer.
	 */
	public String doSort();
	
	/**
	 * Ask the what criteria they user wants to sort by.s
	 * @return what criteria the user wants to sort by.
	 */
	public String sortBy();
	
	/**
	 * Ask the user if they want the list in ascending or descending order.
	 */
	public String sortOrder();
	
	/**
	 * Ask the user if they want to filter by departure time. 
	 */
	public String askDepFilter();
	
	/**
	 * Ask the user if they want to filter by arrival time. 
	 */
	public String askArrFilter();
	
	/**
	 * Asks the user if they want to filter the time as before or after.
	 */
	public String b4OrAfter();
	
	/**
	 * Ask the user what time they want to depart at.
	 * @param depTime 
	 */
	public void askDepTime(boolean depTime);
	
	/**
	 * Gets hour information from the user.
	 * @return the hours the user wants their flight.
	 */
	public String getHours();
	
	/**
	 * Gets minutes information from the user.
	 * @return the minutes the user wants their flight.
	 */
	public String getMinutes();

	/**
	 * Tells the user that you're searching for flights.
	 */
	public void searchFlights();

	/**
	 * Ask's the user if they want to book a round trip.
	 * @return The yes or no answer.
	 */
	public String wantRoundTrip();
	
	/**
	 * Ask the user if they want to with the flight information. 
	 * @return the user's option.
	 */
	public String doWhatWithFlights();
	
	/** 
	 * Ask the user which list they want to filter 
	 * @return the user's answer.
	 */
	public String askOriginOrReturn();
	
	/** 
	 * Ask the user if they want to sort the origin flights,
	 * or the return flights
	 * @return the user's answer.
	 */
	public String sortOriginOrReturn();
	
	/**
	 * Ask the user if they want detail about an origin flight or a return flight.
	 * @return the origin flight or return flight.
	 */
	public String detailOriginOrReturn();
	
	/**
	 * Ask the user if they want to buy the origin ticket or the return ticket.
	 * @return The user's answer.
	 */
	public String buyOrgOrRet();
	
}



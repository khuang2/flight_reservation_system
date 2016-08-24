package user_interface;

import java.util.Scanner;

/**
 * Class that implements a text user interface for the flight system program.
 */

public class TextInterface implements IUserInterface {
	
	/* Scanner object */
	Scanner userInput;
	
	/**
	 * Makes a new text interface object. It initializes a Scanner
	 * object that is used for input from the user.
	 */
	public TextInterface() {
		/* Make the field a new Scanner object */
		userInput = new Scanner(System.in);
			
	}

	@Override
	public void displayWelcomeMsg() {
		System.out.println("\nWelcome to YeYing's Flight Reservation System!");
	}

	@Override
	public String getDepartureAirport() {
		/* Get the Depart Airport from the user */
		System.out.print("\nWhat is the Departure Airport's code? ");
		return userInput.nextLine();
	}

	@Override
	public String getArrivalAirport() {
		/* Get the Arrival Airport from the user */
		System.out.print("\nWhat is the Arrival Airport's code? ");
		return userInput.nextLine();
	}

	@Override
	public String getLeavingDate() {
		/* Get the Departure Day */
		System.out.println("\nPlease note that currently we only have flights between "
				+ "May 8, 2015 and May 18, 2015.");
		System.out.println("\nWhat day do you want to leave? ");
		System.out.print("For example: if you want to leave on May 16, 2015, please enter 16. ");
		return userInput.nextLine();
	}
	
	@Override
	public String roundTrip() {
		System.out.print("\nDo you want to book a round trip? ");
		return userInput.nextLine();
	}
	
	@Override
	public String getReturnDate() {
		/* Get the Return Day */
		System.out.print("\nWhat day do you want to return? ");
		System.out.print("\nFor example: if you want to leave on May 16, 2015, please enter 16. ");
		return userInput.nextLine();
	}

	@Override
	public String isFirstClassSeat() {
		/* Ask if they want First Class */
		System.out.print("\nDo you want first class? (Y/N) ");
		return userInput.nextLine().toUpperCase();
		
	}

	@Override
	public void numOfFlights(int numOfFlights) {
		System.out.println("\nThere are " + numOfFlights + " available flights: \n");
	}

	@Override
	public void noFlights() {
		System.out.println("\nSorry, there are no flights matching flights: \n");
	}

	@Override
	public String bookFlight() {
		/* Get the Flight Number */
		System.out.print("\nPlease confirm this flight option in order to purchase. (type the flight option #): ");
		return userInput.nextLine();
	}

	@Override
	public void confirmFlight() {
		System.out.println("The ticket for your flight was bought sucessfully.");
	}

	@Override
	public String bookAnother() {
		/* Ask if they want continue */
		System.out.print("\nDo you want book another flight? (Y/N) ");
		return userInput.nextLine().toUpperCase();
	}
	
	@Override
	public void goodbyeMsg() {
		System.out.print("\nThank you for using our system!\n" + "Goodbye!");
	}

	@Override
	public void printFlightOption(int index) {
		System.out.println("Flight Option: " + (index + 1) );
	}

	@Override
	public String wantDetail() {
		/* Ask if they want flight detail */
		System.out.println("\nDo you want more detail about a particular flight?\n"
						 +"(Yes or No)");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String getDetailFlight() {
		/* Ask what flight option they want detail about */
		System.out.print("Which flight option do you want more detail about? (Enter the flight option number) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String getAnotherDetail() {
		System.out.print("nDo you want details about another particular flight? (Y/N) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public void userFlightChoice(int index) {
		System.out.println("You selcted flight option: " + index);	
	}

	@Override
	public void flightDisappear() {
		System.out.println("Whoops! This flight has just been bought out.");
	}

	@Override
	public void dataBaseLocked() {
		System.out.println("Sorry! We can't purchase this flight for you right now. "
				+ "Please try again in a few minutes");
		
	}
	
	public String doSort() {
		System.out.print("Would you like to sort the flights? (Y/N) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String sortBy() {
		System.out.println("How would you like to filter the flights?\n");
		System.out.println("1: Departure time\n" +
						   "2: Arrival Time\n" +
						   "3: Total Time\n" +
						   "4: Number of Connections\n" +
						   "5: Layover Time\n" +
						   "6: Cost");	
		System.out.println("\nWhat is your choice? (1-6) ");
		return userInput.nextLine();
	}

	@Override
	public String sortOrder() {
		System.out.print("Do you want the list in ascending order or descending order (A or D). ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String askDepFilter() {
		System.out.print("Would you like to filter by departure times? (Y/N) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String askArrFilter() {
		System.out.print("\nWould you like to filter by arrival times? (Y/N) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String b4OrAfter() {
		System.out.print("Do you want flights before this time, or after this time? (B or A) " );
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public void askDepTime(boolean depTime) {
		System.out.println("What time do you want to " + ((depTime) ? "depart? " : "arrive?\n"));
	}

	@Override
	public String getHours() {
		System.out.print("Hour: ");
		return userInput.nextLine();
	}

	@Override
	public String getMinutes() {
		System.out.print("Minutes: ");
		return userInput.nextLine();
	}

	@Override
	public void searchFlights() {
		System.out.println("Searching for flights...");		
	}

	@Override
	public String wantRoundTrip() {
		
		/* Ask if they want to book a round trip*/
		System.out.print("\nDo you want to book a round trip? (Y/N) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String doWhatWithFlights() {
		
		System.out.println("What would you like to do?\n");
		System.out.println("1: Filter the flights:");
		System.out.println("2: Sort the flights:");
		System.out.println("3: View flight detail:");
		System.out.println("4: Purchase a flight:");
		System.out.println("5: Search for a new trip:");
		System.out.println("6: Exit the program:");
		
		System.out.print("\nPlease select one of the above options: ");
		return userInput.nextLine().toUpperCase();
		
	}

	@Override
	public String askOriginOrReturn() {
		System.out.print("\nWhich list do you want to filter? (Origin/Return) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String sortOriginOrReturn() {
		System.out.print("\nWhich list do you want to sort? (Origin/Return) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String detailOriginOrReturn() {
		System.out.print("Which list do you want detail flight info. from? (Origin/Return) ");
		return userInput.nextLine().toUpperCase();
	}

	@Override
	public String buyOrgOrRet() {
		System.out.print("\nDo you want to buy the origin ticket, or the return ticket? (Origin/Return) ");
		return userInput.nextLine().toUpperCase();
	}
	
	
}

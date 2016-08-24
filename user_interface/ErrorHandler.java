package user_interface;

/**
 * Class used to display the potential error messages, 
 * which may be caused by inappropriate user input. 
 *
 */

public class ErrorHandler {
	
	/* Prevents this class from being instantiated */
	private ErrorHandler() {}
	
	/**
	 * Prints an error message that states that the
	 * user tried to input an invalid airport.
	 */
	public static void invalidPort(){
		System.out.println("I'm sorry, that is not a valid Airport.");	

	}
	
	/**
	 * Prints an error message that states that the
	 * departure airport can't be the same as the arrival.
	 */
	public static void samePort(){
		System.out.println("Arrival Aiport can't be the same as the Departure Airport!");
	}
	
	/**
	 * Prints an error message that states that the
	 * user tried to input an invalid date.
	 */
	public static void invalidDate(){
		System.out.println("\nI'm sorry, that is not a valid day.");
		System.out.println("Please select a day between 8 and 18 (inclusively).");
	}
		
	/**
	 * Prints an error message that states the user tried 
	 * to select an invalid flight.
	 */
	public static void invalidFlight(){
		System.out.println("That is not a valid flight.");
	}
	
	/**
	 * Prints an error message that states that the
	 * user tried to input a non-numeric character 
	 * for the date (day).
	 */
	public static void notANum(){
		System.out.println("I'm sorry, that is not a number.\n");
	}
	
	/**
	 * Prints an error message that states that the
	 * user did not type "Yes" or "No" for their answer.
	 */
	public static void notYesOrNo(){
		System.out.println("Please type \"Y\" or \"N\".");
	}
	
	/**
	 * Prints an error message that states that the
	 * ticket could not be bought at this point in time.
	 */
	public static void invalidBuy(){
		System.out.println("I'm sorry, the ticket could not be bought"
				+ "at this point in time, try in a few minutes later");
	}
	
	/**
	 * Some error involving buying a flight occurred. 
	 */
	public static void whoops(){
		System.out.println("Whoops, having some technical difficulites. "
				+ "Sorry about that!");	
	}
	
	/**
	 * Invalid sorting option.
	 */
	public static void invalidSort(){
		System.out.println("I'm sorry, that is not a valid sort option.");
	}
	
	/**
	 * Invalid sorting order.
	 */
	public static void invalidSortOrder(){
		System.out.println("I'm sorry, that is not a valid sorting order.");
	}
	
	/**
	 * Invalid Time data.
	 */
	public static void invalidTime(){
		System.out.println("That's not a proper time.");
	}

	/**
	 * Invalid input from the user.
	 */
	public static void invalidInput() {
		System.out.println("I'm sorry, that's not a valid answer.");
	}

	public static void invalidReturn() {
		System.out.println("\nI'm sorry, that is not a valid day.");
		System.out.println("Your return date can't be before your departure date.");
	}

	public static void invalidOption() {
		System.out.println("\nI'm sorry, that is not a valid option.");
		System.out.println("Please enter one of the above options.");		
	}
	
	
}

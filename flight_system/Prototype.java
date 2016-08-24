package flight_system;

import java.util.ArrayList;
import java.util.Scanner;

import parsers.*;

public class Prototype {

	/* Holds the states for the Flight System */
	private enum State {
		GetUserInfo, ShowFlights, BuyFlights, FinishState
	};

	private ArrayList<Airport> airportList;
	private ArrayList<Airplane> airplaneList;
	private ArrayList<FlightLeg> flightList, showFlightList;
	private State state;
	private UserInfo userInfo;

	/* The constructor */
	public Prototype() {
		this.airportList = new ArrayList<Airport>();
		this.airplaneList = new ArrayList<Airplane>();
		this.flightList = new ArrayList<FlightLeg>();
		this.showFlightList = new ArrayList<FlightLeg>();
		this.userInfo = new UserInfo(null, null, null, null, false, false);
	};
	
	/* Getter Functions */
	public ArrayList<Airport> getAirportList() {
		return airportList;
	}

	public ArrayList<Airplane> getAirplaneList() {
		return airplaneList;
	}

	/* Method that runs the prototype */
	public void run() {
		
		/* Initialize System */
		initSys();

		/* Go to Get User Info State */
		this.state = State.GetUserInfo;

		/* Determines whether the user wants to continue */
		boolean userContinue = true;

		do {
			switch (state) {
			case GetUserInfo:

				/* Updates the user's info */
				askUserForInfo();
				break;

			case ShowFlights:

				/* Gets the flights from the DB */
				getFlights();

				/* Shows the user the flight(s) if available */
				showFlights();
				
				break;
			
			case BuyFlights:
				
				/* Get the number for the flight the user wants to buy */
				int fltNum = getFltNum(); 
				System.out.println("The Flight number is " + fltNum);
				
				/* Try to purchase the flight */
				if (buyFlt(fltNum)){
					
					/* Show that the flight info has be updated */
					showUpdated(fltNum);
					
					this.state = State.FinishState; // if purchased, go to finish state
					
				}
				else{
					this.state = State.ShowFlights; // if you couldn't purchase
													// show the user the info again
				}
				
				break;
				
			case FinishState:
				
				/* Asks if the user is finished */
				userContinue = finishSys();
				break;

			default:
				break;
			}
			
		} while (userContinue == true);

	}

	private void showUpdated(int fltNum) {
		
		/* Get the flight info again */
		getFlights();
		
		System.out.println("\nThe Updated Flight info for flight " + fltNum +" is.");
		
		/* Search the flights leaving the dept. airport */
		for (FlightLeg flight : flightList) {
			
			/* Print the flight that matches the one the user bought */
			if(flight.getFlightNum() == fltNum){
				System.out.println(flight);
			}
		}
		
	}

	private boolean buyFlt(int fltNum) {
		
		boolean successBuy = false;
		
		/* Get the XML Putter Instance */
		XMLPutter dbPutter = XMLPutter.getInstance();
		
		/* Holds whether the user wants first class or not */
		boolean firstClass = this.userInfo.getIsFirstClass();
		
		/* Make the ticket */
		String ticket = dbPutter.makeTicket(null, firstClass); // DEPRACATED
		
		/* Try to lock the database before we purchase the tickets */
		if (dbPutter.lockDB()){
			
			/* Buy the ticket */
			dbPutter.buyTicket(ticket);
			
			/* Unlock after the purchase */
			dbPutter.unlockDB();
			
			System.out.println("The ticket for your flight: " + fltNum + " was bought.");
			
			successBuy = true;
		}
		/* Another agency is using the DB */
		else {
			System.out.println("I'm sorry, the ticket could not be bought"
					+ "at this point in time, try in a few minutes later");
			
			successBuy = false;
		}
		
		return successBuy;
		
	}

	private int getFltNum() {
		
		/* Scanner object */
		Scanner userInput = new Scanner(System.in);
		
		/* Holds the user flight number */
		int fltNum = 0;

		/* Holds whether User input an available flight */
		boolean validFlight = false;

		do {
			/* Get the Flight Number */
			System.out.print("\nPlease enter the number for the flight you wish to purchase ");
			String fltNumStr = userInput.nextLine();
	
			try {
				fltNum = Integer.parseInt(fltNumStr);
				
				/* Check each to see if the flight number is in the list */
				for (FlightLeg flight : showFlightList) { 

					if(flight.getFlightNum() == fltNum){
						validFlight = true;
						break; // exit for loop
					}
					else{
						validFlight = false;
					}

				}

				/* Not a valid flight, so inform the user */
				if (!validFlight) {
					System.out.println("That is not an available flight.");
				}

			} catch (NumberFormatException e) {
				System.out.println("That is not a number!");
			}

		} while (!validFlight);
		
		return fltNum;
	}

	private boolean finishSys() {
		
		/* Scanner object */
		Scanner userInput = new Scanner(System.in);
		
		boolean userContinue = false;
		
		/* Ask if they want continue */
		System.out.print("Do you want book another flight? ");
		String ans = userInput.nextLine();
		ans = ans.toUpperCase();
		boolean ansValid = false;
		
		do {
			
			/* Go back to user info */
			if (ans.startsWith("Y")) {
				state = State.GetUserInfo;
				ansValid = true;
				
				userContinue = true;
			}
			/* Close Program */
			else if (ans.startsWith("N")) {
				System.out.print("\nThank you for using our system!\n"
						+ "Goodbye!");
				
				/* Reset our DB to the original state */
				
				/* The only DB Getter Object */
				XMLGetter dbGetter = XMLGetter.getInstance();
				dbGetter.resetDB();
				
				ansValid = true;
				userContinue = false;
			
			} else {
				System.out.println("Please type Y or N");
			}

		} while (!ansValid);

		return userContinue;
		
	}

	private void showFlights() {

		String arrAirportCode = userInfo.getArrivalAirport().getCode();
		
		/* Needed to get rid of past results */
		showFlightList.clear();
		
		/* Check all the departure flights */
		for (FlightLeg flight : flightList) {

			/* Arrival airport code */
			String flightArrCode = flight.getArrivalAirport().getCode();

			/* If the code matches, add to list */
			if (arrAirportCode.equalsIgnoreCase(flightArrCode)) {
				showFlightList.add(flight); // Should add flight, but doesn't
			}

		}

		/* If there flights */
		if (showFlightList.size() != 0) {

			System.out.println("\nThere are " + showFlightList.size() + 
					" available flights: \n");

			/* Print the flights */
			for (FlightLeg flight : showFlightList) {
				System.out.println(flight);
			}
			
			this.state = State.BuyFlights; // go to the buy flight state
			
		}
		/* No matching flights */
		else {
			System.out
					.println("\nSorry, there are no flights matching flights: \n");
			this.state = State.FinishState; // go to the finished state
		}

	}

	private void getFlights() {

		/* The only DB Getter Object */
		XMLGetter dbGetter = XMLGetter.getInstance();

		/* Gets the flight XML String */
		String flightInfo = dbGetter.getFlightsXML(true,
				userInfo.getDepartureAirport(), userInfo.getDepartureDate());

		/* Get the XML data from DB again */
		String planeXML = dbGetter.getAirplaneXML();
		String portXML = dbGetter.getAirportsXML();

		/* Parses the Flight Info */
		FlightParser fParse = new FlightParser(planeXML, portXML);
		fParse.parseFlightXML(flightInfo);

		/* Holds the list of flights */
		flightList = fParse.getFlightList();

	}

	private void askUserForInfo() {

		/* User Airports */
		Airport depAirport, arrAirport;
		Date depDate;
		int day = 0; // day of departure
		boolean seat = false; // coach by default

		/* Scanner object */
		Scanner userInput = new Scanner(System.in);

		/* Airport parser object */
		AirportParser portParser = AirportParser.getInstance();

		do {
			/* Get the Depart Airport from the user */
			System.out.print("\nWhat is the code for your Departure Airport? ");
			String depAirportStr = userInput.nextLine();

			/* Create the departure Airport object */
			depAirport = portParser.getAirport(depAirportStr);

			/* Invalid Data */
			if (depAirport == null) {
				System.out.println("That is not a valid Departure Airport ");
			}

		} while (depAirport == null);

		/* Holds whether User input a airport */
		boolean validPort = true;

		do {

			/* Get the Arrival Airport from the user */
			System.out.print("\nWhat is the code for your Arrival Airport? ");
			String arrAirportStr = userInput.nextLine();

			/* Create the departure Airport object */
			arrAirport = portParser.getAirport(arrAirportStr);

			/* Validates Arrival Airport */
			if (arrAirport == null) {
				System.out.println("That is not a valid Arrival Airport");
				validPort = false;
			} else if (arrAirport.getCode().equalsIgnoreCase(
					depAirport.getCode())) {
				System.out.println("Arrival Aiport can't be the same as the Departure Airport!");
				validPort = false;
			} else {
				validPort = true;
			}

		} while (!validPort);

		/* Holds whether User input a good date */
		boolean validDate = false;

		do {
			/* Get the Departure Day */
			System.out.print("\nWhat day do you want to leave? ");
			String dayStr = userInput.nextLine();

			try {
				day = Integer.parseInt(dayStr);

				if (day > 18 || day < 8) {
					System.out.println("That is not a valid Date");
				} else {
					validDate = true;
				}

			} catch (NumberFormatException e) {
				System.out.println("That is not a number!");
			}

		} while (!validDate);

		/* Makes the date object */
		depDate = new Date(Month.May, day, 2015);

		/* Holds whether User inputs a valid seat */
		boolean validSeat = false;

		do {
			/* Ask if they want First Class */
			System.out.print("\nDo you want first class? (Y/N) \n");
			String classType = userInput.nextLine();
			classType = classType.toUpperCase();

			/* 1st Class */
			if (classType.startsWith("Y")) {
				seat = true;
				validSeat = true;
			}
			/* Coach */
			else if (classType.startsWith("N")) {
				seat = false;
				validSeat = true;
			} else {
				System.out.println("Please type Y or N");
			}

		} while (!validSeat);

		/* Set the User info */
		userInfo.setArrivalAirport(arrAirport);
		userInfo.setDepartureAirport(depAirport);
		userInfo.setDepartureDate(depDate);
		userInfo.setIsFirstClass(seat);
		
		/* Go to Show Flights State */
		state = State.ShowFlights;
		
	}

	private void initSys() {

		/* The only DB Getter Object */
		XMLGetter dbGetter = XMLGetter.getInstance();

		/* Get the list of airports from the DB */
		String airportsXMLString = dbGetter.getAirportsXML();

		/* Get the list of airplanes from the DB */
		String airplanesXMLString = dbGetter.getAirplaneXML();

		/* Parsing the Airplane and Airport Info */
		AirportParser portParser = AirportParser.getInstance();
		portParser.parseAirportXML(airportsXMLString);
		airportList = portParser.getAirportList(); // puts in local list

		AirplaneParser planeParser = AirplaneParser.getInstance();
		planeParser.parseAirplaneXML(airplanesXMLString);
		airplaneList = planeParser.getAirplaneList(); // puts in local list
		
		System.out.println("\nWelcome to YeYing's Flight Reservation System!");

	}

}

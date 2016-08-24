package flight_system;

import graph.GraphTester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import parsers.*;
import user_interface.IUserInterface;
import user_interface.TextInterface;

public class Tester {

	public static void main(String[] args) {
		runFlightSys();
	}
	
	private static void runFlightSys(){
		
		IUserInterface userInterface = new TextInterface();
		
		FlightSystem app = new FlightSystem(userInterface);
		
		app.run();
		
	}
	
	@SuppressWarnings("unused")
	private static void flightFilterTester(){
		
		System.out.println("Testing Flight Filter Class\n");
		
		/* Making Flight Legs */
		
		/* Make an airplane, location, and date for the flights */
		Airplane airplane = new Airplane("774", "Airbus", 20, 50);
		Date date = new Date(Month.March, 18, 2015);
		Location loc = new Location(33.641045, -84.427764, false);
		
		/* Airport Info */
		Airport bosPort = new Airport("BOS", "Boston Logan", loc);
		Airport jfkPort = new Airport("JFK", "John F. Kennedy International", loc);
		Airport miaPort = new Airport("MIA", "Miami International", loc);

		/* Time info */
		Time dTime = new Time(20, 00);
		Time aTime = new Time(21, 15);
		
		Time dTime2 = new Time(22, 00);
		Time aTime2 = new Time(23, 00);
		
		Time dTime3 = new Time(11, 00);
		Time aTime3 = new Time(13, 00);
		
		/* Making the legs */
		FlightLeg leg1 = new FlightLeg(airplane, 2000, 
				(aTime.getMinutes() - dTime.getTimeInMinutes()),
				dTime, date, bosPort, aTime, date, jfkPort, 50.00, 15, 25.00, 25);
		
		FlightLeg leg2 = new FlightLeg(airplane, 3000, 
				(aTime2.getMinutes() - dTime2.getTimeInMinutes()),
				dTime2, date, jfkPort, aTime2, date, miaPort, 50.00, 15, 25.00, 25);
		
		FlightLeg leg3 = new FlightLeg(airplane, 4000, 
				(aTime3.getMinutes() - dTime3.getTimeInMinutes()),
				dTime3, date, miaPort, aTime3, date, bosPort, 50.00, 15, 25.00, 25);
		
		/* Make flight classes */
		Flight flight1 = new Flight(leg1); // BOS -> JFK
		Flight flight2 = new Flight(leg3, leg1); // MIA -> BOS -> JFK
		Flight flight3 = new Flight(leg3, leg1, leg2); // MIA -> BOS -> JFK -> MIA
		
		ArrayList<Flight> fList = new ArrayList<Flight>();
		fList.add(flight1);
		fList.add(flight2);
		fList.add(flight3);
		
		/* Make the filter */
		FlightFilter filter = new FlightFilter(fList);
		
		/* Print the tests */
		System.out.println("The cheapest 1st Class flight is: \n" + filter.cheapestFlight(true));
		System.out.println("\nThe shortest flight is: \n" + filter.shortestFlightTime());
		System.out.println("\nThe flight with the least layover time is: \n" + filter.minLayover());
		
		/* Sort the flights */
		System.out.println("\nThe original flight list is:\n");
		
		/* Prints all the flights in the filter filter list */
		for (Flight flight : filter.getFlightList()){
			System.out.println(flight);
			System.out.println();
		}
		
		ArrayList<Flight> sortDep = filter.sortDepartTime(true);
		System.out.println("Sorted by Departure Time: ----");
		System.out.println();
		for (Flight flight : sortDep){
			System.out.println(flight);
			System.out.println();
		}
		
		ArrayList<Flight> sortArr = filter.sortArriveTime(true);
		System.out.println("Sorted by Arrival Time: ----");
		System.out.println();
		for (Flight flight : sortArr){
			System.out.println(flight);
			System.out.println();
		}
		
		ArrayList<Flight> sortConn = filter.sortConnect(true);
		System.out.println("Sorted by Connections: ----");
		System.out.println();
		for (Flight flight : sortConn){
			System.out.println(flight);
			System.out.println();
		}
		
		ArrayList<Flight> sortLay = filter.sortLayover(true);
		System.out.println("Sorted by Layover: ----");
		System.out.println();
		for (Flight flight : sortLay){
			System.out.println(flight);
			System.out.println();
		}
		
		ArrayList<Flight> sort1stPrice = filter.sortPrice(true, true);
		System.out.println("Sorted by 1st Class Price: ----");
		System.out.println();
		for (Flight flight : sort1stPrice){
			System.out.println(flight);
			System.out.println();
		}
		
		ArrayList<Flight> sortCoachPrice = filter.sortPrice(true, false);
		System.out.println("Sorted by Coach Class Price: ----");
		System.out.println();
		for (Flight flight : sortCoachPrice){
			System.out.println(flight);
			System.out.println();
		}
		
		ArrayList<Flight> sortTime = filter.sortTime(true);
		System.out.println("Sorted by Flight Duration: ----");
		System.out.println();
		for (Flight flight : sortTime){
			System.out.println(flight);
			System.out.println();
		}
		
		
	}
	
	@SuppressWarnings("unused")
	private static void graphExampleTester(){
		GraphTester test = new GraphTester();
		test.testExampleGraph();
	}
	
	@SuppressWarnings("unused")
	private static void graphEngineTester(){
		GraphTester test = new GraphTester();
		test.testGraphEngine();
	}
	
	@SuppressWarnings("unused")
	private static void testLocationClass() {

		System.out.println("\nLocation Tester");

		/* Make a test location */
		Location testLoc = new Location(33.641045, -84.427764);	
		Location testLoc1 = new Location(40.000158, -82.887198);
				
		/* Print the object */
		System.out.println(testLoc.toString());
		System.out.println(testLoc.getLatitude());
		System.out.println(testLoc.getLongitude());
		
		System.out.println(testLoc1.toString());
	}

	@SuppressWarnings("unused")
	private static void testAirportParserClass() {

		System.out.println("\nAirportParser Tester");
		
		XMLGetter getter = XMLGetter.getInstance();
		
		/* Make an Airplane Parser */
		AirportParser aParse = AirportParser.getInstance();
		
		System.out.println("Does this have the airports yet? " + aParse.hasAirportList());
		
		aParse.parseAirportXML(getter.getAirportsXML()); // Parses the XML
		
		System.out.println("Does this have the airports yet? " + aParse.hasAirportList());
		
		/* Print the number of Airplanes */
		System.out.println("There are " + aParse.getNumOfAirports()
				+ " airports.\n");

		aParse.printAirportList(); // Prints the list of airports

		System.out.println();
		System.out.println("Looking for Airport with code 'BOS'");
		System.out.println("Found " + aParse.getAirport("BOS").toString());

		System.out.println();
		System.out.println("Looking for Airport with code 'SFO'");
		System.out.println("Found " + aParse.getAirport("SFO").toString());

		System.out.println();
		System.out.println("Looking for Airport with code 'ATL'");
		System.out.println("Found " + aParse.getAirport("ATL").toString());

		System.out.println();
		System.out.println("Looking for Airport with code 'LAX'");
		System.out.println("Found " + aParse.getAirport("LAX").toString());

		System.out.println();
		System.out.println("Looking for Airport with code 'XYZ'");
		if (aParse.getAirport("XYZ") != null) {
			System.out.println("Found " + aParse.getAirport("XYZ").toString());
		} else {
			System.out.println("Airport not Found.");
		}
	}

	@SuppressWarnings("unused")
	private static void testFlightParserClass() {

		System.out.println("\nFlightParser Tester");

		/* Make an Flight Parser */
		FlightParser fParse = new FlightParser("placeHolder", "placeHolder");
		fParse.parseFlightXML(null); // Parses the XML

		/* Print the number of Airplanes */
		System.out.println("There are " + fParse.getNumOfFlights()
				+ " flights.\n");

		fParse.printFlightList(); // Prints the list of airports

		System.out.println();
		System.out.println("Looking for Flight with number '1781'\n");
		System.out.println("Found it!: " + fParse.getFlight(1781).toString());

		System.out.println();
		System.out.println("Looking for Flight with number '1825'\n");
		System.out.println("Found it!: " + fParse.getFlight(1825).toString());

		System.out.println();
		System.out.println("Looking for Flight with number '1817'\n");
		System.out.println("Found it!: " + fParse.getFlight(1817).toString());

		System.out.println();
		System.out.println("Looking for Flight with number '9999'\n");
		if (fParse.getFlight(9999) != null) {
			System.out.println("Found " + fParse.getFlight(9999).toString());
		} else {
			System.out.println("Flight not Found.");
		}

	}

	@SuppressWarnings("unused")
	private static void testAirplaneParserClass() {

		System.out.println("\nAirplaneParser Tester");

		/* Make an Airplane Parser */
		AirplaneParser aParse = AirplaneParser.getInstance();
		aParse.parseAirplaneXML("placeHolder"); // Parses the XML

		/* Print the number of Airplanes */
		System.out.println("There are " + aParse.getNumOfAirplanes()
				+ " airplanes.\n");

		aParse.printAirplaneList(); // Prints the list of airplanes

		System.out.println();
		System.out.println("Looking for Airplane Model 'A310'");
		System.out.println("Found " + aParse.getAirplane("A310").toString());

		System.out.println();
		System.out.println("Looking for Airplane Model '777'");
		System.out.println("Found " + aParse.getAirplane("777").toString());

		System.out.println();
		System.out.println("Looking for Airplane Model 'ABC'");
		if (aParse.getAirplane("ABC") != null) {
			System.out.println("Found " + aParse.getAirplane("ABC").toString());
		} else {
			System.out.println("Airplane not Found.");
		}

	}

	@SuppressWarnings("unused")
	private static void testAirportClass() {
		System.out.println("\nAirport Tester");

		Location bosLoc = new Location(42.365855, -71.009624);
		Location atlLoc = new Location(33.641045, -84.427764);

		/* Testing the Airport Class */
		Airport bosAirport = new Airport("BOS", "Boston Logan", bosLoc);
		Airport atlAirport = new Airport("ATL", "Hartsfield Jackson", atlLoc);

		System.out.println(bosAirport.toString());
		
		/* Test the Airport comparison Method */
		System.out.println("Is " + bosAirport.getName() + " the same as " +
				bosAirport.getName() + "? " + bosAirport.isSameAirport(bosAirport));
		
		System.out.println("Is " + atlAirport.getName() + " the same as " +
				bosAirport.getName() + "? " + atlAirport.isSameAirport(bosAirport));

	}

	@SuppressWarnings("unused")
	private static void testAirPlaneClass() {

		System.out.println("\nAirplane Tester");

		/* Testing the Airplane Class */
		Airplane airplane1 = new Airplane("774", "Airbus", 20, 50);
		Airplane airplane2 = new Airplane("777", "Airbus", 15, 60);
		Airplane airplane3 = new Airplane("123", "Boeing", 10, 100);
		Airplane airplane4 = new Airplane("456", "Boeing", 10, 70);

		System.out.println(airplane1.toString());
		System.out.println();
		System.out.println(airplane2.toString());
		System.out.println();
		System.out.println(airplane3.toString());
		System.out.println();
		System.out.println(airplane4.toString());
		System.out.println();
	}

	@SuppressWarnings("unused")
	private static void testXMLGetter() throws IOException {

		/* Builds the test data */
		Location testLoc = new Location(39.177641, -76.668446);
		Airport depairport = new Airport("BOS", "Logan", testLoc);
		Date date = new Date(Month.May, 10, 2015);
		
		System.out.println("Testing XMLGetter Class");

		XMLGetter test = XMLGetter.getInstance(); // create the test object

		/* The Airports XML */
		System.out.println("\n" + test.getAirportsXML()); 
							
		/* The Airplanes XML */
		System.out.println("\n" + test.getAirplaneXML());// get the XML String
		
		/* The FLights XML for the specific date */
		System.out.println("\n" + test.getFlightsXML(true, depairport, date));
		
		/* The TimeZone XML from Google for a specific location */
		System.out.println("\n" + test.getTimeZoneXML(testLoc));
		
		/* Print how many XMLs have been gotten */																		// Flights
		System.out.println(test.toString()); 
												
		/* Reset the Database */
		test.resetDB();

	}

	@SuppressWarnings("unused")
	private static void testDateClass() {
		System.out.println("Testing Date Class:\n");

		/* Making a Date object called test */
		Date test = new Date(Month.November, 21, 1991);
		Date test2 = new Date(Month.April, 22, 2015);
		Date test3 = new Date(Month.May, 3, 2015);
		Date richardBday = new Date(Month.August, 22, 1990);
		
		ArrayList<Date> testList = new ArrayList<Date>();
		
		testList.add(test);
		testList.add(test2);
		testList.add(test3);
		testList.add(richardBday);
		
		/* Printing the Date */
		System.out.println(test.toString());

		/* Printing the month, day, and year of the object */
		System.out.println("The Month of this Date object is " + test.getMonth());
		System.out.println("The Day of this Date object is " + test.getDay());
		System.out.println("The Year of this Date object is " + test.getYear());

		System.out.println("\nRichard is born in the month of: " + richardBday.getMonth());
		
		/* Testing if if the Date class can be sorted */
		System.out.println(); 
		System.out.println(testList); // before sort
		Collections.sort(testList);   // sort in ascending order
		System.out.println(testList); // after sort
		
	}

	@SuppressWarnings("unused")
	private static void testTimeClass() {
		System.out.println("Testing Time Class\n");
		
		Time test = new Time(21, 00); // 21:00 GMT
		Time test2 = new Time(18, 00); // 18:00 GMT
		Time test3 = new Time(12, 00); // 12:00 GMT
		Time test4 = new Time(15, 00); // 15:00 GMT
		
		/* Make a list of the test times */
		ArrayList<Time> testList =  new ArrayList<Time>();
		testList.add(test);
		testList.add(test2);
		testList.add(test3);
		testList.add(test4);
		
		Location bos = new Location(42.365855, -71.009624); // Boston, MA
		Location yin = new Location(38.4667, 106.2667); // Yinchuan, China
				
		/* Local times for the locations */
		Time localBos = Time.getLocalTime(test, bos);
		Time localYin = Time.getLocalTime(test, yin);
		
		/* Printing the Time */
		System.out.println(test.toString());

		/* Printing the hours and minutes of the object */
		System.out.println("The hour is : " + test.getHours());
		System.out.println("The minutes are : " + test.getMinutes());
		
		System.out.println("The time in minutes is : "
				+ test.getTimeInMinutes());
		
		System.out.println("The hour in 12 hour format is : "
				+ test.getHoursIn12());
		
		System.out.println("The time is in AM : " + test.isAM());
		
		/* Testing the local time methods */
		System.out.println("The local time is in AM: " + localBos.isAM());
		System.out.println("The local time is in AM: " + localYin.isAM());
		
		/* Testing the compare method */
		System.out.println("Is " + test + " after " + test2 +"? " + (test.compareTo(test2) > 0));
		System.out.println("Is " + test2 + " after " + test +"? " + (test2.compareTo(test) > 0));
		System.out.println("Is " + test + " the same " + test +"? " + (test.compareTo(test) == 0));
		
		/* Testing the 24-Hour to 12-Hour converter */
		System.out.println(test + " in 12-hour time is " + Time.get12HourTime(test));
		
		/* Testing if if the Time class can be sorted */
		System.out.println(testList); // before sort
		Collections.sort(testList);   // sort in ascending order
		System.out.println(testList); // after sort
		
		/* Convert from minutes to hours and minutes */
		System.out.println(Time.convertMinsToHours(120)); // 2:00
		System.out.println(Time.convertMinsToHours(90)); // 1:30
		System.out.println(Time.convertMinsToHours(225)); // 3:45
	}

	@SuppressWarnings("unused")
	private static void testFlightLegClass() {
		System.out.println("Testing Flight Leg Class\n");

		/* Make an airplane for the flight */
		Airplane airplane = new Airplane("774", "Airbus", 20, 50);

		/* -- Departure Info -- */
		Time dTime = new Time(20, 00);
		Date dDate = new Date(Month.March, 18, 2015);

		/* Location of the Departing Airport and Airport */
		Location dLoc = new Location(33.641045, -84.427764);
		Airport dAirport = new Airport("BOS", "Boston Logan", dLoc);

		/* -- Arrival Info -- */
		Time aTime = new Time(21, 15);
		Date aDate = new Date(Month.March, 18, 2015);

		/* Location of the Arriving Airport and Airport */
		Location aLoc = new Location(33.641045, -84.427764);
		Airport aAirport = new Airport("JFK", "John F. Kennedy International",
				aLoc);

		FlightLeg test = new FlightLeg(airplane, 2000, 300, dTime, dDate,
				dAirport, aTime, aDate, aAirport, 50.00, 15, 25.00, 25);

		/* Printing the Flight */
		System.out.println(test.toString());

	}

	@SuppressWarnings("unused")
	private static void testXMLPutter() {

		System.out.println("Testing XMLPutter Class");

		/* Reset the DB in before you purchase flight */
		XMLGetter resetter = XMLGetter.getInstance();
		resetter.resetDB();

		XMLPutter test = XMLPutter.getInstance(); // create the test object
		
		/* Making flights to test the ticket maker */
		Date date = new Date(Month.May, 10, 2015);
		
		/* First flight info */
		Airplane plane1 = new Airplane("A340", "Airbus", 32, 268);
		Time dt1 = new Time(11, 00);
		Time at1 = new Time(12, 00);
		Location dl1 = new Location(42.216446, -83.355427);
		Location al1 = new Location(42.365855, -71.009624);
		Airport da1 = new Airport("DTW", "Detroit Metropolitan Wayne County Airport", dl1);
		Airport aa1 = new Airport("BOS", "Logan International", al1);
				
		/* Second flight info */
		Airplane plane2 = new Airplane("A320", "Airbus", 12, 124);
		Time dt2 = new Time(13, 00);
		Time at2 = new Time(14, 30);
		Location dl2 = new Location(42.365855, -71.009624);
		Location al2 = new Location(35.042345, -89.979216);
		Airport da2 = new Airport("BOS", "Logan International", dl2);
		Airport aa2 = new Airport("MEM", "Memphis International", al2);
		
		Airplane plane3 = new Airplane("A320", "Airbus", 12, 124);
		Time dt3 = new Time(15, 00);
		Time at3 = new Time(16, 00);
		Location dl3= new Location(42.365855, -71.009624);
		Location al3= new Location(35.042345, -89.979216);
		Airport da3= new Airport("MEM", "Memphis International", dl3);
		Airport aa3 = new Airport("SFO", "San Fransisco International", al3);

		FlightLeg flight1 = new FlightLeg(plane1, 2000, (at1.getTimeInMinutes() - dt1.getTimeInMinutes()) , dt1, date, da1, at1, date, aa1, 100.00, 20,	33.33, 23);
		FlightLeg flight2 = new FlightLeg(plane2, 3000, (at2.getTimeInMinutes() - dt2.getTimeInMinutes()), dt2, date, da2, at2, date, aa2, 150.00, 20, 50.00, 23);
		FlightLeg flight3 = new FlightLeg(plane3, 4000, (at3.getTimeInMinutes() - dt3.getTimeInMinutes()), dt3, date, da3, at3, date, aa3, 200.00, 20,	66.66, 23);
		
		/* Make the flights */
		Flight f1 = new Flight(flight1);
		Flight f2 = new Flight(flight2, flight3);
		Flight f3 = new Flight(flight1, flight2, flight3);
		
		/* ----------------- */
		
		/* Making tickets for the test case */
		String testTicket1 = test.makeTicket(f1, true); 											
		String testTicket2 = test.makeTicket(f2, false); 
		String testTicket3 = test.makeTicket(f3, false);												
		
		System.out.println("\nTicket 1 info is:");
		System.out.println(testTicket1);

		System.out.println("\nTicket 2 info is:");
		System.out.println(testTicket2);
		
		System.out.println("\nTicket 2 info is:");
		System.out.println(testTicket2);
		
		System.out.println("\nTicket 3 info is:");
		System.out.println(testTicket3);
		
//		/* Lock the database before we purchase the tickets */
//		test.lockDB();
//		test.buyTicket(testTicket1);
//		test.buyTicket(testTicket2);
//
//		/* Unlock after the purchase */
//		test.unlockDB();
//
//		/* Should Print the fact that 2 tickets were bought */
//		System.out.println(test.toString());
//
//		/* Reset the DB to default state */
//		resetter.resetDB();

	}

	@SuppressWarnings("unused")
	private static void testUserInfo() {
		System.out.println("Testing userinfo class");

		Location departureLoc = new Location(33.641045, -84.427764);
		Location arrivalLoc = new Location(61.176033, -149.990079);

		Airport departureAirport = new Airport("BOS", "Boston Logan",
				departureLoc);
		Airport arrivalAirport = new Airport("ANC",
				"Ted Stevens Anchorage International Airport", arrivalLoc);
		Airport departureAirport1 = new Airport("ANC",
				"Ted Stevens Anchorage International Airport", departureLoc);
		Airport arrivalAirport1 = new Airport("BOS", "Boston Logan", arrivalLoc);

		Date departureDate = new Date(Month.April, 26, 2015);

		UserInfo user = new UserInfo(departureAirport, arrivalAirport,
				departureDate, departureDate, true, false);

		System.out.println(user.getDepartureAirport());
		System.out.println(user.getArrivalAirport());
		System.out.println(user.getDepartureDate());
		System.out.println(user.getIsFirstClass());
		System.out.println(user.toString());

		user.setDepartureAirport(departureAirport1);
		user.setArrivalAirport(arrivalAirport1);
		user.setDepartureDate(departureDate);
		user.setIsFirstClass(false);
		System.out.println(user.toString());
	}
	
	@SuppressWarnings("unused")
	private static void testFlightClass()
	{

		System.out.println("Testing Flight class\n");
		
		Date date = new Date(Month.May, 10, 2015);
		
		/* First flight info */
		Airplane plane1 = new Airplane("A340", "Airbus", 32, 268);
		Time dt1 = new Time(11, 00);
		Time at1 = new Time(12, 00);
		Location dl1 = new Location(42.216446, -83.355427);
		Location al1 = new Location(42.365855, -71.009624);
		Airport da1 = new Airport("DTW", "Detroit Metropolitan Wayne County Airport", dl1);
		Airport aa1 = new Airport("BOS", "Logan International", al1);
				
		/* Second flight info */
		Airplane plane2 = new Airplane("A320", "Airbus", 12, 124);
		Time dt2 = new Time(13, 00);
		Time at2 = new Time(14, 30);
		Location dl2 = new Location(42.365855, -71.009624);
		Location al2 = new Location(35.042345, -89.979216);
		Airport da2 = new Airport("BOS", "Logan International", dl2);
		Airport aa2 = new Airport("MEM", "Memphis International", al2);
		
		Airplane plane3 = new Airplane("A320", "Airbus", 12, 124);
		Time dt3 = new Time(15, 00);
		Time at3 = new Time(16, 00);
		Location dl3= new Location(42.365855, -71.009624);
		Location al3= new Location(35.042345, -89.979216);
		Airport da3= new Airport("MEM", "Memphis International", dl3);
		Airport aa3 = new Airport("SFO", "San Fransisco International", al3);

		FlightLeg flight1 = new FlightLeg(plane1, 2000, (at1.getTimeInMinutes() - dt1.getTimeInMinutes()) , dt1, date, da1, at1, date, aa1, 100.00, 20,	33.33, 23);
		FlightLeg flight2 = new FlightLeg(plane2, 3000, (at2.getTimeInMinutes() - dt2.getTimeInMinutes()), dt2, date, da2, at2, date, aa2, 150.00, 20, 50.00, 23);
		FlightLeg flight3 = new FlightLeg(plane3, 4000, (at3.getTimeInMinutes() - dt3.getTimeInMinutes()), dt3, date, da3, at3, date, aa3, 200.00, 20,	66.66, 23);
		
		/* Make the flights */
		Flight f1 = new Flight(flight1);
		Flight f2 = new Flight(flight2, flight3);
		Flight f3 = new Flight(flight1, flight2, flight3);
		
		System.out.println("\nThe flight leg info is:\n");
		System.out.println(flight1);
		System.out.println(flight2);
		System.out.println(flight3);
		
		/* Print the flights */
		System.out.println("======= Flight Option: 1 =======");
		f1.printFlight(false, false);
		f1.printDetailFlight(false, false);
		System.out.println();
		
		System.out.println("======= Flight Option: 2 =======");
		f2.printFlight(true, false);
		f2.printDetailFlight(true, false);
		System.out.println();
		
		System.out.println("======= Flight Option: 3 =======");
		f3.printFlight(false, false);
		f3.printDetailFlight(false, false);
		System.out.println();
		
	}
}

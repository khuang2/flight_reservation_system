package flight_system;


/** 
 * Class used to represent an airport based on the 
 * information provided by the XML files in the flight database system.
 * 
 * @author Kun Huang
 * @see parsers.XMLGetter#getAirportsXML() 
 */

public class Airport implements Comparable<Airport> {
	
	/* The Fields */
	private String code;
	private String name;
	private Location location;
	
	/**
	 * Makes an object that represents an airport.
	 * <p>
	 * The airport data represents the data that is represented
	 * by the XML data that is on the database.
	 *
	 * @param code the 3 symbol airport code of the airplane, i.e. "BOS".
	 * @param name the name of the airport.
	 * @param location the location of the airport, 
	 * 		  given as a latitude and longitude pair. 
	 * 
	 * @see parsers.XMLGetter#getAirportsXML()
	 * @see flight_system.Location
	 */
	
	public Airport(String code, String name, Location location) {
		this.code = code;
		this.name = name;
		this.location = location;
	}
	
	/* The getter methods */
	
	/**
	 * Gets the code of this airport object.
	 * <p>
	 * @return the airport code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Gets the name of this airport object.
	 * <p>
	 * @return the name of the airport.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the location of this airport object.
	 * <p>
	 * @return the airport's location.
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Used to compare if two airport objects are the same airport.
	 * <p>
	 * @param compAirport the airport that is to be compared with this airport.
	 * @return true if the airports are the same.
	 */
	/* If the airport codes are the same, then they're the same airport */
	public boolean isSameAirport(Airport compAirport){
		return (this.code.equalsIgnoreCase(compAirport.code));
	}
	
	@Override
	public int compareTo(Airport compPort) {
		
		return code.toUpperCase().compareTo(compPort.getCode().toUpperCase());
		
	}

	/**
	 * String representation of the airport object.
	 * <p>
	 * @return the string representation of this airport. 
	 */
	@Override
	public String toString() {
		return "Airport [code=" + code + ", name=" + name + ", location="
				+ location + "]";
	}

}

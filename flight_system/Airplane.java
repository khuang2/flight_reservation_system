package flight_system;

/** 
 * Class used to represent an airplane based on the 
 * information provided by the XML files in the flight database system.
 * 
 * @author Kun Huang
 * @see parsers.XMLGetter#getAirplaneXML() 
 */

public class Airplane {
	
	/* The Fields */
	private String model;
	private String manufactor;
	private int firstClassSeats;
	private int coachSeats;
	
	/**
	 * Makes an object that represents an airplane.
	 * <p>
	 * The airplane data represents the data that is represented
	 * by the XML data that is on the database.
	 *
	 * @param model the model of the airplane, i.e. "A310".
	 * @param manufacturer the company that made the airplane, i.e. "Boeing".
	 * @param firstClassSeats the number of first class seats on the airplane.
	 * @param coachSeats the number of coach seats on the airplane.
	 * 
	 * @see parsers.XMLGetter#getAirplaneXML()
	 */
	public Airplane(String model, String manufacturer, int firstClassSeats,
			int coachSeats) {
		
		this.model = model;
		this.manufactor = manufacturer;
		this.firstClassSeats = firstClassSeats;
		this.coachSeats = coachSeats;
	}

	/**
	 * Gets the model of this airplane object.
	 * <p>
	 * @return the model of the airplane.
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * Gets the manufacturer of this airplane object.
	 * <p>
	 * @return the manufacturer of the airplane.
	 */
	public String getManufactor() {
		return manufactor;
	}
	
	/**
	 * Gets the number of 1st class seats of this airplane object.
	 * <p>
	 * @return the number of 1st class seats for this airplane. 
	 */
	public int getFirstClassSeats() {
		return firstClassSeats;
	}

	/**
	 * Gets the number of coach class seats of this airplane object.
	 * <p>
	 * @return the number of coach class seats for this airplane. 
	 */
	public int getCoachSeats() {
		return coachSeats;
	}
	
	/**
	 * String representation of the airplane object.
	 * <p>
	 * @return the string representation of this airplane. 
	 */
	@Override
	public String toString() {
		return "Airplane [model=" + model + ", manufacturer=" + manufactor
				+ ", firstClassSeats=" + firstClassSeats + ", coachSeats="
				+ coachSeats + "]";
	}	
}

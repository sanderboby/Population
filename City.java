/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author Andrew Chen	
 *	@since  1/9/23	
 */
public class City implements Comparable<City> //comparable is the interface
{
	private String cityName;
	private String stateName;
	private String cityType;
	private int population;
	
	//Constructor to intialize instance variables of a city
	public City(String cityName, String stateName, String cityType, int population)
	{
		this.cityName = cityName;
		this.stateName = stateName;
		this.cityType = cityType;
		this.population = population;
	}
	
	/**
	 *	The	compareTo() method	will sort City populations.	
	 *	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	public int compareTo(City other)
	{
		if(this.population != other.population) {
			return this.population - other.population;
		}
		else if(!(this.cityName.equals(other.cityName))) {
			return this.stateName.compareTo(other.stateName);
		}	
		else return this.cityName.compareTo(other.cityName);
	}
	
	
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	public boolean equals(City other)
	{
		return (this.cityName.equals(other.cityName)) || (this.stateName.equals(other.stateName));
	}
	 
	/**	Accessor methods */
	public String getCityName() { return this.cityName; }
	public String getStateName() { return this.stateName; }
	public String getCityType() { return this.cityType; }
	public int getPopulation() { return this.population; }
	
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", stateName, cityName, cityType,
						population);
	}
}

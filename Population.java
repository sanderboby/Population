import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *	Population - <description goes here>
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author Andrew Chen	
 *	@since  1/9/23	
 */
public class Population 
{
	Prompt prompt = new Prompt();
	
	// List of cities
	private List<City> cities; //cannot instantiate
	
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";
	
	public Population()
	{
		this.cities = new ArrayList<City>();
	}
	
	public static void main(String[] args)
	{
		Population p = new Population();
		p.printIntroduction();
		p.printMenu();
		p.loadCities();
		p.askOptions();
	}
	
	/** Populates the cities ArrayList with cities from the ArrayList 
	 * 	Calls the constructor of each line, initializing the instance 
	 *  variables of the City class with its corresponding data
	 * */
	public void loadCities()
	{
		Scanner infile = FileUtils.openToRead(DATA_FILE);
		infile.useDelimiter("[\t\n]");
		
		//Alabama	Abbeville	city	2567 --> 4 elements per line
		
		int i = 0; 
		while(infile.hasNext()) //Collect the coordinates of all cities in cities.txt
		{
			String stateName = infile.next();
			String cityName = infile.next();
			String cityType = infile.next();
			int population = Integer.parseInt(infile.next());
			cities.add(new City(cityName,stateName,cityType,population));
		}
	}
	
	/**	Prints the introduction to Population */
	public void printIntroduction() 
	{
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}
	
	/**	Print out the choices for population sorting */
	public void printMenu() 
	{
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}
	
	/** Prompts the user for options of sorting the cities and/or states and/or populations */
	public void askOptions()
	{
		int choice = prompt.getInt("Enter selection");
		
		while(choice != 9)
		{
			switch(choice)
			{
				case 1: findLeastPeople(); break;
				case 2: findMostPeople(); break;
				case 3: firstFifty(); break;
				case 4: lastFifty(); break;
				case 5: namedStatePop(); break;
				case 6: cityNamePop(); break;
				case 9: System.out.println("\nThanks for using Population!"); System.exit(1); break;
			}
			System.out.println();
			printMenu();
			choice = prompt.getInt("Enter selection");
			if(choice == 9) { System.out.println("\nThanks for using Population!"); System.exit(1); }
		}	
	}
	
	/** Finds the 50 least populous cities*/
	public void findLeastPeople()
	{
		System.out.println("\nFifty least populous cities");
		long start = System.currentTimeMillis();
		selectionSort(cities);
		long end = System.currentTimeMillis();
		printResults(start,end);
	}	
	
	/** Sorts the cities for population -->WORKS FINE! :)
		Option 2: Compare populations for most people using mergesort */
	public void merge(List<City> cities, int beg, int mid, int end,boolean pop)    
	{    
		int i, j, k;  
		int n1 = mid - beg + 1;    
		int n2 = end - mid;    
		  
	   //temp Arrays  
			List<City> LeftArray = new ArrayList<City>(n1);
			for (int z = 0; z < n1; z++) 
				LeftArray.add(cities.get(z));
			List<City> RightArray = new ArrayList<City>(n2);  
			for (int p = 0; p < n2; p++) 
				RightArray.add(cities.get(p));

		//copy to temp arrays  
		for (i = 0; i < n1; i++)    
			LeftArray.set(i,cities.get(beg + i));     
		for (j = 0; j < n2; j++)    
			RightArray.set(j,cities.get(mid + 1 + j));    
		  
		i = 0; //first subarray - initial index  
		j = 0; //second subarray- initial index   
		k = beg;  //merged subarray - initial index  
		  
		while (i < n1 && j < n2)    
		{    
			//change inequality to sort in ascending order
			if(LeftArray.get(i).getPopulation() >= RightArray.get(j).getPopulation())  
			{    
				cities.set(k,LeftArray.get(i));    
				i++;    
			}    
			else    
			{    
				cities.set(k,RightArray.get(j));    
				j++;    
			}    
			k++;    
		}    
		while (i<n1)    
		{    
			cities.set(k,LeftArray.get(i));    
			i++;    
			k++;    
		}    
		  
		while (j<n2)    
		{    
			cities.set(k,RightArray.get(j));    
			j++;    
			k++;    
		}    
	}    


	/** Sorts the cities by name using merge sort */
	public void merge(List<City> cities, int beg, int mid, int end)    
	{    
		int i, j, k;  
		int n1 = mid - beg + 1;    
		int n2 = end - mid;    
		  
	   //temp Arrays  
			List<City> LeftArray = new ArrayList<City>(n1);
			for (int z = 0; z < n1; z++) 
				LeftArray.add(cities.get(z));
			List<City> RightArray = new ArrayList<City>(n2);  
			for (int p = 0; p < n2; p++) 
				RightArray.add(cities.get(p));

		//copy to temp arrays  
		for (i = 0; i < n1; i++)    
			LeftArray.set(i,cities.get(beg + i));     
		for (j = 0; j < n2; j++)    
			RightArray.set(j,cities.get(mid + 1 + j));    
		  
		i = 0; //first subarray - initial index  
		j = 0; //second subarray- initial index   
		k = beg;  //merged subarray - initial index  
		  
		while (i < n1 && j < n2)    
		{    
			//change inequality to sort in ascending order
			if(LeftArray.get(i).getCityName().compareTo(RightArray.get(i).getCityName()) > 0)  
			{    
				cities.set(k,LeftArray.get(i));    
				i++;    
			}    
			else    
			{    
				cities.set(k,RightArray.get(j));    
				j++;    
			}    
			k++;    
		}    
		while (i<n1)    
		{    
			cities.set(k,LeftArray.get(i));    
			i++;    
			k++;    
		}    
		  
		while (j<n2)    
		{    
			cities.set(k,RightArray.get(j));    
			j++;    
			k++;    
		}    
	}    
	
	/** Sorts cities based on name using insertion sort 	*/
	public void insertionSort(List<City> cities)
	{
		int marker = 0;
		int sorted = 0; //Partition 1: Sorted element decrementer for comparision in else statement
		City partwo = cities.get(0); //Partition 2: Unsorted element select storage
		double temp = 0; //Storage variable for sorted elements of the array
		int len = cities.size();
	
		for(int i = 1; i < len; i++)
		{
			//This step is not sorted, so swap algo is as follows:
  			partwo = cities.get(i); //Current unsorted element is stored in partition variable
  			sorted = i - 1; //Sorted partition is one element before the unsorted partition
  		
  			while(sorted >= 0 && (cities.get(sorted).getCityName().compareTo(partwo.getCityName())) > 0) // while the sorted > unsorted // unsorted element is greater than/equal to the most recent element to the left, the partition of sorted elements are moved one space over to the right
  			{
  				cities.set(sorted+1,cities.get(sorted));
  				sorted--; //Loop decrements/continues down the sorted partion until false
  			}
  			cities.set(sorted + 1,partwo);	//Once while loop has been exited, the unsorted element is less than most recent value in the sorted partition, it is now sorted
		}
	}
	 
	/** Overloaded merge sort method used for */
	public void mergeSort(List<City> cities, int beg, int end,boolean pop)  
	{  
		int mid = (beg + end) / 2;  
		if (beg < end) { 
			mergeSort(cities, beg, mid,true);  
			mergeSort(cities, mid + 1, end,true);  
			merge(cities, beg, mid, end,true); 
		}  
	}
	
	/** Overloaded merge sort method used for */
	public void mergeSort(List<City> cities, int beg, int end, String b)    
	{	
		int mid = (beg + end) / 2;  
		if (beg < end) { 
			mergeSort(cities, beg, mid, "b");   
			mergeSort(cities, mid + 1, end,"b");  
			merge(cities, beg, mid, end,"A"); 	
		}
	}  
	
	/** Uses merge sort in order to sort cities by name */
	public void merge(List<City> cities, int beg, int mid, int end, String ayo)
	{
		int i, j, k;  
		int n1 = mid - beg + 1;    
		int n2 = end - mid;    
		  
	   //temp Arrays  
			List<City> LeftArray = new ArrayList<City>(n1);
			for (int z = 0; z < n1; z++) 
				LeftArray.add(cities.get(z));
			List<City> RightArray = new ArrayList<City>(n2);  
			for (int p = 0; p < n2; p++) 
				RightArray.add(cities.get(p));

		//copy to temp arrays  
		for (i = 0; i < n1; i++)    
			LeftArray.set(i,cities.get(beg + i));     
		for (j = 0; j < n2; j++)    
			RightArray.set(j,cities.get(mid + 1 + j));    
		  
		i = 0; //first subarray - initial index  
		j = 0; //second subarray- initial index   
		k = beg;  //merged subarray - initial index  
		  
		while (i < n1 && j < n2)    
		{    
			//change inequality to sort in ascending order
			if(LeftArray.get(i).getCityName().compareTo(RightArray.get(j).getCityName()) > 0)  
			{    
				cities.set(k,LeftArray.get(i));    
				i++;    
			}    
			else    
			{    
				cities.set(k,RightArray.get(j));    
				j++;    
			}    
			k++;    
		}    
		while (i<n1)    
		{    
			cities.set(k,LeftArray.get(i));    
			i++;    
			k++;    
		}    
		  
		while (j<n2)    
		{    
			cities.set(k,RightArray.get(j));    
			j++;    
			k++;    
		}    
	}
	
	/** Given an ArrayList of cities, sorts them with selection sort */
	public void selectionSort(List<City> arr)
    {  
	    for (int i = 0; i < arr.size() - 1; i++) {  
            int index = i;  
            for (int j = i + 1; j < arr.size(); j++) {  
                if (arr.get(j).getPopulation() < arr.get(index).getPopulation()) {  
                    index = j; 
                }  
            }  
			swap(arr,index,i);
        }
    }  
     
    /** Swaps the position of cities in the ArrayList, based on the integer params */
	private void swap(List<City> arr, int x, int y) 
	{
		City temp = arr.get(x);
		arr.set(x,arr.get(y));
		arr.set(y,temp);
	}
	
	/**
	 * Option 2: Finds the most populated US cities with MergeSort
	 */
	public void findMostPeople()
	{
		System.out.println("Fifty most populous cities");
		long start = startTime();
		int n = cities.size();  
		mergeSort(cities, 0, n - 1,true);  
		long end = endTime();
		printResults(start,end);
	}	
	
	/** Prints out last fifty cities based on their name */
	public void firstFifty()
	{
		System.out.println("Fifty cities sorted by name");
		long start = startTime();
		insertionSort(cities);
		long end = endTime();
		printResults(start,end);
	}	
	
	/** Prints out last fifty cities based on their name */
	public void lastFifty()
	{
		// Last fifty cities sorted by name descending (Merge Sort)
		System.out.println("Fifty cities sorted by name descending");
		
		long start = startTime();
		int n = cities.size();  
		mergeSort(cities, 0, n - 1,"B");  
		long end = endTime();
		printResults(start,end);
	}	
	
	//Option 5: Fifty most populous cities in named state
	//Example: find most populous cities in california.
	public void namedStatePop()
	{
		String stateName = prompt.getString("Enter state name (ie. Alabama)");
		while(!isValidState(stateName))
		{
			System.out.println("ERROR: " + stateName + " is not valid");
			stateName = prompt.getString("Enter state name (ie. Alabama)");
		}
		
		long start = startTime();
		sortMostPopulous(cities);
		long end = endTime();
		printResults(start,end,stateName,5);
	}
	
	/** Prints out the most populous cities, based on user input of city*/
	public void cityNamePop()
	{
		String cityName = prompt.getString("Enter city name");
		System.out.println("City " + cityName + "by population");
		
		while(!isValidCity(cityName))
		{
			System.out.println("ERROR: " + cityName + " is not valid");
			cityName = prompt.getString("Enter city name");
		}
		
		long start = startTime();
		sortMostPopulous(cities);
		long end = endTime();
		printResults(start,end,cityName,6);
	}	
	
	/** Use insertion sort to sort the most populous cities */
	public void sortMostPopulous(List<City> cities)
	{
		int marker = 0;
		int sorted = 0; //Partition 1: Sorted element decrementer for comparision in else statement
		City partwo = cities.get(0); //Partition 2: Unsorted element select storage
		double temp = 0; //Storage variable for sorted elements of the array
		int len = cities.size();
		for(int i = 1; i < len; i++)
		{
			//This step is not sorted, so swap algo is as follows:
  			partwo = cities.get(i); //Current unsorted element is stored in partition variable
  			sorted = i - 1; //Sorted partition is one element before the unsorted partition
  		
  			while(sorted >= 0 && (cities.get(sorted).getPopulation() < (partwo.getPopulation()))) // while the sorted > unsorted // unsorted element is greater than/equal to the most recent element to the left, the partition of sorted elements are moved one space over to the right
  			{
  				cities.set(sorted+1,cities.get(sorted));
  				sorted--; //Loop decrements/continues down the sorted partion until false
  			}
  			cities.set(sorted + 1,partwo);	//Once while loop has been exited, the unsorted element is less than most recent value in the sorted partition, it is now sorted
		}	
	}
	
	/**
	 * Prints out the sorted cities using the toString() method in the Cities class
	 * Also prints out the runtime of the sort used
	 */
	public void printResults(long start, long end)
	{
		printHeader();
		for(int k = 0; k < 50; k++) {
			if(k <= 8)
			System.out.println(" " + (k + 1) + ": " + cities.get(k));
			else System.out.println((k + 1) + ": " + cities.get(k));
		}
	
		System.out.println("\nElapsed Time " + (end - start) + " milliseconds");
	}
	
	/**
	 * This is an overloaded method of printResults(long start, long end)
	 * locName is the locationName, which involves either a city or state name 
	 * option is the option number for the last two numbers on the list, 5 and 6
	 */
	public void printResults(long start, long end, String locName, int option)
	{
		printHeader();
		if(option == 5)
		{
			int count = 0;
			for(int k = 0; k < cities.size(); k++) {			
				if(cities.get(k).getStateName().equals(locName)) {
					if(count < 50) {
						if(count <= 8) {
							System.out.println(" " + (count + 1) + ": " + cities.get(k));
						}
						else System.out.println((count + 1) + ": " + cities.get(k));
						count++;
					}
				}
			}
		}
		
		else
		{
			int count = 0; 
			for(int k = 0; k < cities.size(); k++) {			
				if(cities.get(k).getCityName().equals(locName)) {
					if(count <= 8) {
						System.out.println(" " + (count + 1) + ": " + cities.get(k));
					}
					else System.out.println((count + 1) + ": " + cities.get(k));
					count++;
				}
			}
		}
		System.out.println("\nElapsed Time " + (end - start) + " milliseconds");
	}
	
	/**
	 * Returns true if a given state name is valid (within the text file)
	 * Returns false if a given state name is invalid (not in the text file)
	 */
	public boolean isValidState(String name)
	{
		for(int i = 0; i < cities.size(); i++) {
			if(cities.get(i).getStateName().equals(name)) {
				return true;
			}
		}
		return false;
	}	
	
	/** Identical functionality to the above method, but searches for valid cities */
	public boolean isValidCity(String name)
	{
		for(int i = 0; i < cities.size(); i++) {
			if(cities.get(i).getCityName().equals(name)) {
				return true;
			}
		}
		return false;
	}	
	
	
	/** Below three methods are used for monitoring the runtime of the sorting
	 *  algorithms. startTime() and endTime() return the current time, while 
	 *  printElapsed() returns the time elapsed based on these two values
	 */
	public long startTime() { return System.currentTimeMillis(); }
	public long endTime() { return System.currentTimeMillis(); }

	/** Prints out the header for information about a given location */
	public void printHeader()
	{
		System.out.printf("%9s %21s %22s %21s", "State", "City", "Type", "Population\n");
	}
}

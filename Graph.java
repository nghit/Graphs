/***************************************************************
* file: Graph.java
* author: N.Tran
* class: CS 241 – Data Structure and Algorithms 2
*
* assignment: program 4
* date last modified: 11/29/2017
*
* purpose: A graph containing cities and roads
****************************************************************/ 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {

	//list of all cities in the graph. City with id = i will be at location i of this arraylist
	ArrayList<City> mCities;

	//a map of string and roads. key=<shortcode of from city>+<shortcode of to city> and value is the road object
	//ex:TRPM will be the key for road between TR and PM. This map is used for lookup purpose
	HashMap<String,Road> mRoads;

	//a map with key = short code of city and value is City object. Whenever a new city is added to mCities , its also updated in
	//mCitymap . This is useful for easy lookup basd on short code of city
	HashMap<String,City> mCityMap;

	public Graph()

	{
		mCities=new ArrayList<City>();
		mCityMap=new HashMap<String,City>();
		mRoads=new HashMap<String,Road>();
	}

	//add road between 2 cities based on id of cities
	public void addRoad(int id1,int id2,int distance)
	{

		addRoad(mCities.get(id1-1), mCities.get(id2-1), distance);
	}

	//add road between 2 cities based on short codes of cities
	public void addRoad(String source,String destination ,int distance)
	{
		Road road;

		//lookup for the cities in the map
		City city1=mCityMap.get(source),city2=mCityMap.get(destination);

		if(city1==null || city2==null) //one or both cities dont exist in the graph
		{
			System.out.println("WARNING: "+(city1==null?source:destination )+" does not exist ");
			return;
		}

		//lookup roads
		if((road=mRoads.get(source+destination))!=null) // a road already exists between these 2 cities
		{
			System.out.println("WARNING: There is already a road with distance "+road.getDistance()+" between "+source+" and "+destination );
			return;
		}

		addRoad(city1, city2, distance);
		System.out.println("You have inserted a road from "+city1.getName()+" to "+city2.getName()+" with a distance of "+distance);
	}

	public void addRoad(City city1,City city2,int distance)
	{

		Road road=new Road(city1, city2, distance);
		city1.addRoad(road);
		mRoads.put(city1.getShortCode()+city2.getShortCode(),road); //add entry to roads lookup map
		//System.out.println(road);
	}

	public void removeRoad(String source,String destination)
	{
		Road road;
		City city1=mCityMap.get(source),city2=mCityMap.get(destination);
		if(city1==null || city2==null) //one or both cities dont exist in the graph
		{
			System.out.println("WARNING: "+(city1==null?source:destination )+" does not exist ");
			return;
		}

		if((road=mRoads.get(source+destination))==null) // a road already exists between these 2 cities
		{
			System.out.println("WARNING: The road from "+city1.getName()+" to "+city2.getName()+" does not exist");
			return;
		}

		city1.removeRoad(road);
		mRoads.remove(source+destination);//remove from lookup map
		System.out.println("You have removed the road from "+city1.getName()+" to "+city2.getName()+" with a distance of "+road.getDistance());
	}

	//this function loads the cities and roads data from specified files
	public void initialize(String citiesFile,String roadsFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(citiesFile));
		City city;
		String name,str;


		while(scanner.hasNext())
		{
			city=new City(scanner.nextInt(),scanner.next(),scanner.next());
			name=city.getName();
			while(true)
			{
				str=scanner.next(); //some city names have 2 words... so in that case population will come later
				try
				{
					city.setPopulation(Long.parseLong(str));//if its not number , exception occurs, so its part of name
					break;
				}catch(NumberFormatException e)
				{
					name+=" "+str; //gather all parts of the name
				}                 
			}

			city.setName(name); //set the name with al parts
			city.setElevation(scanner.nextInt());

			mCityMap.put(city.getShortCode(), city);
			mCities.add(city);
		}

		scanner.close();

		scanner=new Scanner(new File(roadsFile));

		while(scanner.hasNext())
		{
			addRoad(scanner.nextInt(),scanner.nextInt(), scanner.nextInt());
		}
		scanner.close();
	}

	public City getCity(String shortCode)
	{
		return mCityMap.get(shortCode);
	}


	//function to get shortest distance based on Dijkstra's algorithm
	public int getShortestDistance(String source,String destination)
	{
		City start=mCityMap.get(source),target=mCityMap.get(destination);
		if(start==null || target==null) //one or both cities dont exist in the graph
		{
			System.out.println("WARNING: "+(start==null?source:destination )+" does not exist ");
			return 0;
		}

		HashMap<City, Integer> distance=new HashMap<City,Integer>(); //stores computed distances of City objects
		HashMap<City, City> previous=new HashMap<City,City>(); //stores the previous city for the City in shortestt path
		City u=null,v=null;
		int newDistance;
		Integer min=null,d;
		ArrayList<City> queue=new ArrayList<City>();

		//initially add all cities in queue
		for(int i=0;i<mCities.size();i++)
		{
			v=mCities.get(i);         
			queue.add(v);
		}

		distance.put(start,0); //set distance of start node to 0

		boolean found=false;

		while(!queue.isEmpty() && !found)
		{

			min=null;

			//remove the node with minimum distance and set it in u              
			for(int i=0; i<queue.size();i++)
			{
				d=distance.get(v=queue.get(i));
				if(d!=null)
				{
					if(min==null || d<min)
					{
						min=d;  
						u=v;
					}                  
				}                 
			}

			queue.remove(u); //remove the minimum distance node from queue

			//for each neighbour v of u
			for(Road r:u.mRoads)
			{
				v=r.getToCity();
				newDistance=distance.get(u)+r.getDistance();

				//if new distance of v is less than previous distance , then store this distance and also update previoius  
				if(distance.get(v)==null || newDistance<distance.get(v))
				{
					distance.put(v,newDistance);
					previous.put(v,u);

				}
				//if we reached the target city , stop further processsing
				if(v==target)
				{
					found=true;

					break;
				}

			}


		}

		if(found)
		{          
			//back track the previous and construct the path
			String path=v.getShortCode();

			while(v!=start)
			{
				v=previous.get(v);
				path=v.getShortCode()+" "+path;          
			}

			System.out.println("\nThe minimum distance between "+start.getName()+ " and "+target.getName()+" is "+distance.get(target)+" through the route :\n "+path);
			return distance.get(target);
		}  
		else
		{
			System.out.println("\nCould not find path between "+start.getName() + " and "+target.getName());
			return -1;
		}
	}
}
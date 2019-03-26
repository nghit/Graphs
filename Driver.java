/***************************************************************
* file: Driver.java
* author: N.Tran
* class: CS 241 – Data Structure and Algorithms 2
*
* assignment: program 4
* date last modified: 11/29/2017
*
* purpose: driver class
****************************************************************/ 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Driver
{
    public static void main(String[] args) {

		//set the paths to files containing city and road data
		String citiesFile="/Users/NghiTran/Downloads/city.dat/",roadsFile="/Users/NghiTran/Downloads/road.dat/";

		try {
			Graph graph=new Graph();
			graph.initialize(citiesFile, roadsFile); //initialize the graph with data

			Scanner scanner=new Scanner(System.in);
			String response;
			City city;
			boolean exit=false;
			//get user's input for command and depending on input use the switch case
			do
			{
				System.out.println("Command ? ");
				response=scanner.next();
				response=response.toUpperCase().trim(); //convert user's input to uppercase

				if(response.length()==1)
				{
					switch(response.charAt(0))
					{

					case 'Q': //query for city based on short code
						System.out.println("City code: ");
						response=scanner.next();
						city=graph.getCity(response);
						if(city==null)
						{
							System.out.println("City with code "+response +" does not exist in graph");
						}
						else
							System.out.println(city.toString());                      

						break;
					case 'I': //insert a road between 2 cities
						System.out.println("City codes and distance :");
						graph.addRoad(scanner.next(),scanner.next(),scanner.nextInt());
						break;
					case 'H': //display help
						System.out.println("Q Query the city information by entering the city code.");
						System.out.println("D Find the minimum distance between two cities.");
						System.out.println("I Insert a road by entering two city codes and distance.");
						System.out.println("R Remove an existing road by entering two city codes.");
						System.out.println("H Display this message.");
						System.out.println("E Exit.");
						break;
					case 'D': //find shortest distance between 2 cities
						System.out.println("City codes : ");
						graph.getShortestDistance(scanner.next(), scanner.next());
						break;
					case 'R': //remove road between 2 cities
						System.out.println("City codes : ");
						graph.removeRoad(scanner.next(), scanner.next());

						break;
					case 'E':
						exit=true;
						System.out.println("Program exiting.");
						break;
					default:
						;
					}
					System.out.println("--------------------------------------------------------");
				}

			}while(!exit);

			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
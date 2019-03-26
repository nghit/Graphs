/***************************************************************
* file: Road.java
* author: N.Tran
* class: CS 241 – Data Structure and Algorithms 2
*
* assignment: program 4
* date last modified: 11/29/2017
*
* purpose: A Road between 2 cities along with its distance
****************************************************************/ 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Road
{
   City mFromCity;
   City mToCity;
   int mDistance;
   
   //Constructor
   public Road(City fromCity,City toCity,int distance)
   {
       mFromCity=fromCity;
       mToCity=toCity;
       mDistance=distance;
      
   }
   public City getmFromCity() {
       return mFromCity;
   }
   public void setFromCity(City mFromCity) {
       this.mFromCity = mFromCity;
   }
   public City getToCity() {
       return mToCity;
   }
   public void setToCity(City mToCity) {
       this.mToCity = mToCity;
   }
   public int getDistance() {
       return mDistance;
   }
   public void setDistance(int mDistance) {
       this.mDistance = mDistance;
   }
  
   //useful for displaying
   public String toString()
   {
       return mFromCity.getShortCode()+"->"+mToCity.getShortCode()+" = "+mDistance;
   }
}
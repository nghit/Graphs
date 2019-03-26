/***************************************************************
* file: City.java
* author: N.Tran
* class: CS 241 – Data Structure and Algorithms 2
*
* assignment: program 4
* date last modified: 11/29/2017
*
* purpose: A city class having id, short code, full name, population and elevation. Also a list of roads from this city.
****************************************************************/ 
import java.util.ArrayList;
public class City
{
   int mCityId;
   String mShortCode;
   String mFullName;
   long mPopulation;
   int mElevation;
   ArrayList<Road> mRoads; //that roads that connect this city to other cities
  
   //Constructor
   public City(int id,String code,String name)
   {
       mCityId=id;
       mShortCode=code;
       mFullName=name;
       mRoads=new ArrayList<Road>();
   }

   public long getPopulation() {
       return mPopulation;
   }

   public void setPopulation(long mPopulation) {
       this.mPopulation = mPopulation;
   }

   public int getElevation() {
       return mElevation;
   }

   public void setElevation(int mElevation) {
       this.mElevation = mElevation;
   }

   public int getCityId() {
       return mCityId;
   }

   public String getShortCode() {
       return mShortCode;
   }

   public String getName() {
       return mFullName;
   }
   public void setName(String name)
   {
       mFullName=name;
   }
     
   public void addRoad(Road road)
   {
       mRoads.add(road);
   }
  
   public void removeRoad(Road road)
   {
       mRoads.remove(road);
   }
  
   //useful for display purpose
   public String toString()
   {
       return mCityId+" "+mShortCode+" "+mFullName+" "+mPopulation+" "+mElevation;
   }
}
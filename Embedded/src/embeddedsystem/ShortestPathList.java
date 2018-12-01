/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.util.ArrayList;


public class ShortestPathList {
    
    static ArrayList<Sensor> n_list; // Neighbor list
    static ArrayList<Sensor> s_list; // List with sensors on the shortest path
    
    static double timeToReachFS;
    static boolean timeSet;
    
    // constuctor
    public ShortestPathList(){
    s_list = new ArrayList<Sensor>();
    n_list = new ArrayList<Sensor>();
    timeToReachFS = 0.0;
    timeSet = false;
    }
    // constuctor
  
    
    // given a list of sensors with (x,y) we can figure out if two nodes are 
    // neighbors or not, we do this by checking the distance between those.
    public void findNeighbors(ArrayList<Sensor> sensorList){
        
        double d1X;
        double d1Y;
        double d2X;
        double d2Y;
        double distance;
        
        // find neighbors for each sensor
        for(Sensor s : sensorList){
          
            d1X = s.x;
            d1Y = s.y;
            for(Sensor otherSensor : sensorList){
                d2X = otherSensor.x;
                d2Y = otherSensor.y;
                
                if(!s.typeOfSensor.equals(otherSensor.typeOfSensor) ){
                    distance = Math.sqrt(Math.pow(d1X-d2X,2) + Math.pow(d1Y-d2Y,2));
                    
                   // System.out.print("state:  "+ s.getState()+"   ");
                    if(distance < s.comR/2){
                        
                        //System.out.println(s.typeOfSensor + " and " + otherSensor.typeOfSensor + " are neighbors");
                        s.neighbourSensor.add(otherSensor);
                        s.originNeighbor.add(otherSensor);
                    }
                    
                    
                }
            }
            
         /*   System.out.print("{");
            for(Sensor sensor : s.neighbourSensor){
                System.out.print(sensor.typeOfSensor + " ");
            }
            System.out.println("}");
         */
        }
        
       // return sensorList;
        
    }
    
    // add sensor to the shortestPath list
    static void addSensorToPath(Sensor s){
        s_list.add(s);
    }
    
    // prints the shortestPath list
    public static void printSP(){
        
        for(Sensor s : s_list){
            System.out.print(s.typeOfSensor + " ");
        }
        System.out.println();
    }
    
    public void emptyPath(){
        s_list.clear();
    }

    public void checkNeighbors(ArrayList<Sensor> sensorList){
         for(Sensor s : sensorList){
             for(int i=0;i<s.neighbourSensor.size();i++){
                 if(s.neighbourSensor.get(i).getState()==false){
                     System.out.println("REMOVED");
                     s.neighbourSensor.remove(i);
                 }
             }
         }
     
    }
}

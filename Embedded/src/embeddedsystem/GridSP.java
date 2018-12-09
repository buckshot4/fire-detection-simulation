/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;


import java.util.ArrayList;
import java.util.Arrays;


public class GridSP {
    Sensor firstSensor;
    
    // constructor
    public GridSP(Sensor s){
        firstSensor =s;
    }
    
    // gets the distance between the sensors in a list
    public static void initDistances(ArrayList<Sensor> sensorList){
        
        
        
        for(Sensor s : sensorList){
            ArrayList<Sensor> neighborSensors;
            neighborSensors = s.neighbourSensor;
            
            double d1X = s.x;
            double d1Y = s.y;
            double d2X = 0;
            double d2Y = 0;
            
            for(Sensor ns : neighborSensors){
                d2X = ns.x;
                d2Y = ns.y;
                
                double d = Math.sqrt(Math.pow(d1X-d2X,2) + Math.pow(d1Y-d2Y,2));
                s.neighborDistance.add(d);
            }
            
            
            
        }
        
    }
    
    // prints the distances between the sensors
    public static void printAllDistances(ArrayList<Sensor> sensorList){
        
        
        for(Sensor s : sensorList){
            for(Sensor ns : s.neighbourSensor){
                s.distanceBetweenSensors(ns);
            }
        }
        
    }
    
    // calculates the hops for all the sensors from the firestation
    public static void hopsCalculation(Sensor s){
        ArrayList<Sensor> neighbors = new ArrayList<>();
        neighbors = s.neighbourSensor;
        
        // Remove all the failed sensors from list
       ArrayList<Sensor> activeNeighborsOnlyList = new ArrayList<>();
       for(Sensor ss : s.neighbourSensor){
           if(ss.getState()){
            activeNeighborsOnlyList.add(ss);
           }
        }
        
        for(Sensor os : activeNeighborsOnlyList){
            
            if(s.hops < os.hops){
                os.hops = s.hops + 1;
                //System.out.println(os.typeOfSensor + " became " + os.hops);
            }
        }
    }
    
 // calculates the ShorstestPath based on the hops from the firestation
 public static void newSP(Sensor sensor, ShortestPathList spl){
        
        // make sensor true = transmitiong message
        sensor.forwardMsg = true;
        
        //choose the neighbor with the closest path to the FS, with less hops.
        Sensor closestNeighbor = null;
        int bestHop = 1000;
        
        ArrayList<Sensor> activeNeighbors = new ArrayList<>();
        for(Sensor s : sensor.neighbourSensor){
            if(s.getState()){
                activeNeighbors.add(s);
            }
        }
        System.out.println("ACTIVE "+activeNeighbors.size());
         
        // checks closest neighbor's hop with active state
        for(Sensor s : activeNeighbors){
            if(s.hops < bestHop && s.getState()&& (s.getForwardMsg()!=true)){
                bestHop = s.hops;
                closestNeighbor = s;
            
            }
        }
        if(closestNeighbor==null){
            
        }else{
        // print test
        System.out.println("The closest neightbor to " + sensor.typeOfSensor + 
                " is " + closestNeighbor.typeOfSensor + " with " + closestNeighbor.hops + " hops");
        
        // add the closest neighbor to the Shortest path
        ShortestPathList.addSensorToPath(closestNeighbor);
        
        // Recursion - continue with the neighbors of the closest sensor and so on
        if(!closestNeighbor.typeOfSensor.equals("fs")){
            newSP(closestNeighbor, spl);
        }else{
          
        }
        }
        
        
    }
    
    
  // tread implementation of ShortestPath
  public  void newSPThread(Sensor sensor, ArrayList<Sensor> list){
        
        // make sensor true = transmitiong message
        sensor.forwardMsg = true;
        
        //choose the neighbor with the closest path to the FS, with less hops.
        Sensor closestNeighbor = null;
        int bestHop = 1000;
        
        ArrayList<Sensor> activeNeighbors = new ArrayList<>();
        for(Sensor s : sensor.neighbourSensor){
            if(s.getState()){
                activeNeighbors.add(s);
            }
        }
         System.out.println("ACTIVE "+activeNeighbors.size());
        
        for(Sensor s : activeNeighbors){
            if(s.hops < bestHop && s.getState()){
                bestHop = s.hops;
                closestNeighbor = s;
            
            }
        }
        if(closestNeighbor==null){
            
        }else{
        // print test
        System.out.println("The closest neightbor to " + sensor.typeOfSensor + 
                " is " + closestNeighbor.typeOfSensor + " with " + closestNeighbor.hops + " hops");
        
        // add the closest neighbor to the Shortest path
        list.add(closestNeighbor);
        
        // Recirsion - continue with the neighbors of the closest sensor and so on
        if(!closestNeighbor.typeOfSensor.equals("fs")){
            newSPThread(closestNeighbor, list);
        }else{
              EmbeddedSystem.ADDToDraw(new SP(firstSensor,list));
              MyCanvas.drawThickLine(firstSensor, list);
        }
        }
        
        
    }
  
    // print hops for all sensors in the list
    public static void printHops(ArrayList<Sensor> list){
        
        for(Sensor s : list){
            System.out.println(s.typeOfSensor + " is " + s.hops + " away from fs.");
        }
        
        
    }
    
    //print neighbors for a given sensor
    public static void printN(Sensor s){
        for(Sensor n : s.neighbourSensor){
            System.out.print(n.typeOfSensor + " ");
        }
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.util.ArrayList;
import java.util.Arrays;


public class GridSP {
    
    
    
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
    
    public static void printAllDistances(ArrayList<Sensor> sensorList){
        
        
        for(Sensor s : sensorList){
            for(Sensor ns : s.neighbourSensor){
                s.distanceBetweenSensors(ns);
            }
        }
        
    }
    

    public static void hopsCalculation(Sensor s){
        ArrayList<Sensor> neighbors = new ArrayList<>();
        neighbors = s.neighbourSensor;
        
        for(Sensor os : neighbors){
            if(s.hops < os.hops){
                os.hops = s.hops + 1;
                //System.out.println(os.typeOfSensor + " became " + os.hops);
            }
        }
    }
    
 public static void newSP(Sensor sensor, ShortestPathList spl){
        
        // make sensor true = transmitiong message
        sensor.forwardMsg = true;
        
        //choose the neighbor with the closest path to the FS, with less hops.
        Sensor closestNeighbor = null;
        int bestHop = 1000;
        
        for(Sensor s : sensor.neighbourSensor){
            if(s.hops < bestHop){
                bestHop = s.hops;
                closestNeighbor = s;
            }
        }
        
        // print test
        System.out.println("The closest neightbor to " + sensor.typeOfSensor + 
                " is " + closestNeighbor.typeOfSensor + " with " + closestNeighbor.hops + " hops");
        
        // add the closest neighbor to the Shortest path
        ShortestPathList.addSensorToPath(closestNeighbor);
        
        // Recirsion - continue with the neighbors of the closest sensor and so on
        if(!closestNeighbor.typeOfSensor.equals("fs")){
            newSP(closestNeighbor, spl);
        }
        
        
        
    }
    
    
   
 
    public static void printHops(ArrayList<Sensor> list){
        
        for(Sensor s : list){
            System.out.println(s.typeOfSensor + " is " + s.hops + " away from fs.");
        }
        
        
    }
    
    public static void printN(Sensor s){
        for(Sensor n : s.neighbourSensor){
            System.out.print(n.typeOfSensor + " ");
        }
    }
    
    
    
}

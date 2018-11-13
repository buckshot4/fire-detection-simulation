/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import static embeddedsystem.MyCanvas.sensorList;
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
    
    public static void main(String[] args){
        //Sensors Definition
        Sensor s0 = new Sensor(10, 20, "s0",22);
        Sensor s1 = new Sensor(20, 30, "s1",22);
        Sensor s2 = new Sensor(40, 30, "s2",22);
        Sensor s3 = new Sensor(20, 10, "s3",22);
        Sensor fs = new Sensor(40, 10, "fs",22);
       
      
        //Neighbors Definition
        Sensor[] s0N = new Sensor[] {s1, s3};
        Sensor[] s1N = new Sensor[] {s0, s2,s3};
        Sensor[] s2N = new Sensor[] {s1,fs};
        Sensor[] s3N = new Sensor[] {s0,s1,fs};
        Sensor[] fsN = new Sensor[] {s2,s3};
        s0.neighbourSensor.addAll(Arrays.asList(s0N));
        s1.neighbourSensor.addAll(Arrays.asList(s1N));
        s2.neighbourSensor.addAll(Arrays.asList(s2N));
        s3.neighbourSensor.addAll(Arrays.asList(s3N));
        fs.neighbourSensor.addAll(Arrays.asList(fsN));

        
        //Distance between sensors - Neighborhood
       /* ArrayList<Sensor> sensorList = new ArrayList();
        sensorList.addAll(Arrays.asList(s0, s1, s2, s3, fs));
        */
                
        ShortestPathList spl = new ShortestPathList();
        spl.findNeighbors(sensorList);
        //Automatically init the distances between all sensors for a given Sensor List
        initDistances(sensorList);
        printAllDistances(sensorList);
        
        
        
        s0.findSP(fs,spl);
        s0.printPathToFS();
        
        //ShortestPathList.printSP();
    }
    
    
    
    
    
}

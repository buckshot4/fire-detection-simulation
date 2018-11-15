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
    
    public static void main(String[] args){
        //Sensors Definition
        //Sensor s0 = new Sensor(10, 20, "s0",22);
        //Sensor s1 = new Sensor(20, 30, "s1",22);
        //Sensor s2 = new Sensor(40, 30, "s2",22);
        //Sensor s3 = new Sensor(20, 10, "s3",22);
        //Sensor fs = new Sensor(40, 10, "fs",22);
       
        /*
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
*/
        
        //Distance between sensors - Neighborhood
        ArrayList<Sensor> sensorList = new ArrayList();
        //sensorList.addAll(Arrays.asList(s0, s1, s2, s3, fs));
        sensorList = MyGrid.createSensorList(300, 200, 53);
        
        ShortestPathList spl = new ShortestPathList();
        sensorList = spl.findNeighbors(sensorList);
        
        
        for(Sensor s : sensorList){
            if(s.typeOfSensor.equals("fs")){
                s.hops = 0;
                System.out.println(s.typeOfSensor + " is " + s.hops + " away from fs");
            }
        }
        
        for(int i = 0; i < 99 ; i++){
            for(Sensor s : sensorList){
                hopsCalculation(s);
            }
        }
        
        
        Sensor s0 = null;
        for(Sensor s : sensorList){
            if(s.typeOfSensor.equals("s0")){
                s0 = s;
            }
        }
        newSP(s0, spl);
        ShortestPathList.printSP();
        
        
        
        
        // calculate hops from SF
        //calculateHops(sensorList);
        printHops(sensorList);
        
        
        //Automatically init the distances between all sensors for a given Sensor List
        initDistances(sensorList);
        printAllDistances(sensorList);
        
        
        
        Sensor sfs = null;
        for(Sensor s : sensorList){
        
            if(s.typeOfSensor.equals("fs")){
                sfs = s;
            }
        }
        
        
        
        Sensor ss = sensorList.get(0);
        //ss.findSP(sfs, spl);
        //ss.sp_list = ShortestPathList.s_list;
        //ss.printPathToFS();
        
        /*
        s0.findSP(fs,spl);
        s0.sp_list = ShortestPathList.s_list;
        s0.printPathToFS();
        spl.emptyPath();
        System.out.println();
        s1.findSP(fs,spl);
        s1.printPathToFS();
        */
        
        //ShortestPathList.printSP();
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

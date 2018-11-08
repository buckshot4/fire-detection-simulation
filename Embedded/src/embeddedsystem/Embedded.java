/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Superkey
 */
public class Embedded {

    
    
            
    public static void main(String[] args) {
        
        int numOfSensors = 12;
        ArrayList<Sensor> neighbors = new ArrayList<>();
        Map<Sensor, ArrayList> neighborhood = new HashMap<>();
        
        Sensor s1 = new Sensor(20, 190, "s1",40);
        Sensor s2 = new Sensor(30, 160, "s2",40);
        Sensor s3 = new Sensor(20, 125, "s3",40);
        Sensor s4 = new Sensor(95, 140, "s4",40);
        Sensor s5 = new Sensor(60, 135, "s5",40);
        Sensor s6 = new Sensor(125, 120, "s6",40);
        Sensor s7 = new Sensor(90, 110, "s7",40);
        Sensor s8 = new Sensor(90, 70, "s8",40);
        Sensor s9 = new Sensor(130, 70, "s9",40);
        Sensor s10 = new Sensor(20, 85, "s10",40);
        Sensor s11 = new Sensor(55, 70, "s11",40);
        Sensor fs = new Sensor(150, 100, "fs",40);
        
        //Define Neighbors
        Sensor[] s1N = new Sensor[] {s2};
        Sensor[] s2N = new Sensor[] {s1, s3, s5};
        Sensor[] s3N = new Sensor[] {s2, s10};
        Sensor[] s4N = new Sensor[] {s5, s6};
        Sensor[] s5N = new Sensor[] {s4};
        Sensor[] s6N = new Sensor[] {s4, s7, fs};
        Sensor[] s7N = new Sensor[] {s6, s8};
        Sensor[] s8N = new Sensor[] {s7, s9, s11};
        Sensor[] s9N = new Sensor[] {s8, fs};
        Sensor[] s10N = new Sensor[] {s3, s11};
        Sensor[] s11N = new Sensor[] {s8, s10};
        Sensor[] fsN = new Sensor[] {s6, s9};
        
        s1.neighbourSensor.addAll(Arrays.asList(s1N));
        s2.neighbourSensor.addAll(Arrays.asList(s2N));
        s3.neighbourSensor.addAll(Arrays.asList(s3N));
        s4.neighbourSensor.addAll(Arrays.asList(s4N));
        s5.neighbourSensor.addAll(Arrays.asList(s5N));
        s6.neighbourSensor.addAll(Arrays.asList(s6N));
        s7.neighbourSensor.addAll(Arrays.asList(s7N));
        s8.neighbourSensor.addAll(Arrays.asList(s8N));
        s9.neighbourSensor.addAll(Arrays.asList(s9N));
        s10.neighbourSensor.addAll(Arrays.asList(s10N));
        s11.neighbourSensor.addAll(Arrays.asList(s11N));
        fs.neighbourSensor.addAll(Arrays.asList(fsN));
        
        // Define Distances
        Double[] s1ND = new Double[] {31.622};
        Double[] s2ND = new Double[] {31.622,36.4,39.051};
        Double[] s3ND = new Double[] {36.4,40.0};
        Double[] s4ND = new Double[] {35.355,36.055};
        Double[] s5ND = new Double[] {35.355,};
        Double[] s6ND = new Double[] {36.055,36.4,32.015};
        Double[] s7ND = new Double[] {36.4,40.0};
        Double[] s8ND = new Double[] {40.0,40.0,35.0};
        Double[] s9ND = new Double[] {40.0,36.055};
        Double[] s10ND = new Double[] {40.0,38.078};
        Double[] s11ND = new Double[] {35.0,38.078};
        Double[] fsND = new Double[] {32.015, 36.055};
        
        s1.neighborDistance.addAll(Arrays.asList(s1ND));
        s2.neighborDistance.addAll(Arrays.asList(s2ND));
        s3.neighborDistance.addAll(Arrays.asList(s3ND));
        s4.neighborDistance.addAll(Arrays.asList(s4ND));
        s5.neighborDistance.addAll(Arrays.asList(s5ND));
        s6.neighborDistance.addAll(Arrays.asList(s6ND));
        s7.neighborDistance.addAll(Arrays.asList(s7ND));
        s8.neighborDistance.addAll(Arrays.asList(s8ND));
        s9.neighborDistance.addAll(Arrays.asList(s9ND));
        s10.neighborDistance.addAll(Arrays.asList(s10ND));
        s11.neighborDistance.addAll(Arrays.asList(s11ND));
        fs.neighborDistance.addAll(Arrays.asList(fsND));
        
        neighborhood.put(s1, s1.neighbourSensor);
        neighborhood.put(s2, s2.neighbourSensor);
        neighborhood.put(s3, s3.neighbourSensor);
        neighborhood.put(s4, s4.neighbourSensor);
        neighborhood.put(s5, s5.neighbourSensor);
        neighborhood.put(s6, s6.neighbourSensor);
        neighborhood.put(s7, s7.neighbourSensor);
        neighborhood.put(s8, s8.neighbourSensor);
        neighborhood.put(s9, s9.neighbourSensor);
        neighborhood.put(s10, s10.neighbourSensor);
        neighborhood.put(s11, s11.neighbourSensor);
        neighborhood.put(fs, fs.neighbourSensor);
        
        System.out.println(neighborhood.size());
        
        //Print
        printNeighbors(s3,neighborhood);
        
        
        //test distance between two sensors - Remove it
        s1.distanceBetweenSensors(s2);
        
    }
    
    static void activateNeighbors(Sensor currentSensor){
        //System.out.println(currentSensor.);
    }
    
    static void printNeighbors(Sensor s, Map<Sensor, ArrayList> neighborhood){
        
        
        int numOfNeighbors = s.getNeighborNumber();
        System.out.println("Number of neighbors: " + numOfNeighbors);
        
        ArrayList al = neighborhood.get(s);
        
        Sensor print = null;
        
        for(int i = 0; i < numOfNeighbors; ++i){
           print  = (Sensor) al.get(i);
           System.out.println(print.typeOfSensor);
        }
        
        

    }
    
}

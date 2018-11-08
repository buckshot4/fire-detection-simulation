/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author eleonora
 */
public class Sensor {
    int x;
    int y;
    String typeOfSensor;
    int ratio;
    boolean active;
    ArrayList<Sensor> neighbourSensor;
    ArrayList<Double> neighborDistance;
    Double[] nd;
    
    public Sensor(int xP,int YP,String tS, int ratioN){
        x= xP;
        y=YP;
        typeOfSensor=tS;
        ratio=ratioN;
        neighbourSensor= new ArrayList();
        neighborDistance = new ArrayList();
    } 
    public int getPositionX(){
        return x;
    }
    public int getPositionY(){
        return y;
    }
    public void setPX(int xN){
        x= xN;
    }
    public void setPY(int YN){
        y=YN;
    }
    
    public void setActive(boolean state){
        active=state;
    }
    public boolean getState(){
        return active;
    }
    public void setRatio(int newRatio){
        ratio= newRatio;
    }
    public int getRatio(){
        return ratio;
    }
    
    public void setNeighbourSeonsor(ArrayList<Sensor> newNeighbours){
        neighbourSensor=new ArrayList(newNeighbours);
    }
    
    public int getNeighborNumber(){
        return neighbourSensor.size();
    }
    
    public double distanceBetweenSensors(Sensor otherSensor){
        
        double d1X = this.x;
        double d1Y = this.y;
        double d2X = otherSensor.x;
        double d2Y = otherSensor.y;
        
        
        double distance = Math.sqrt(Math.pow(d1X-d2X,2) + Math.pow(d1Y-d2Y,2));
        
        
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Distance is :" + df.format(distance));
        
        return distance;
    }
    
    //Routing - SP
    public void shortestPath(Sensor target, ArrayList<Double> neighborDistanceMatrix){
        
        
        
        System.out.println("Shortest path ...");
        
        double maxDist = 0;
        
        int rep = 0;
        int index = 0;
        
        for(double d : neighborDistanceMatrix){
            if(d > maxDist){
                maxDist = d;
                index = rep;
                
            }
            rep ++;
            
        }
        
        int j = 0;
        if(neighbourSensor.contains(target)){
            for(Sensor s : neighbourSensor){
                if(s.typeOfSensor.equals(target.typeOfSensor)){
                    index = j;
                }
                j++;
            }
        }
        
        System.out.println(this.typeOfSensor);
        System.out.println(index);
        
        String case1 = neighbourSensor.get(index).typeOfSensor;
        String case2 = target.typeOfSensor;
        System.out.println("Target is: " + case2);
        System.out.println("Next is: " + case1);
        
        
        
        //neighborDistanceMatrix.remove(index);
        if(!case1.equals(case2)){
            neighbourSensor.get(index).shortestPath(target, neighbourSensor.get(index).neighborDistance);
        }
        

        
    }
    
    public void pingNeigbors(ArrayList<Sensor> origin){
        // Keep track of all the neighbors at the current time frame
    }
    
    
    
}

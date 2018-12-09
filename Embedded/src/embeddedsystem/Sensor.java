/*
This class describes the object sensor.
Each sensor has a position, defined with x and y integer, two ranges, communication and sensing. It can be active or inactive if the sensor doesn't work anymore
*/package embeddedsystem;

import static embeddedsystem.MyCanvas.surface;
import java.awt.Color;
import java.awt.Graphics;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class Sensor {
    int x;
    int y;
    String typeOfSensor;
    int senR; //Sensing range
    int comR; //Communication range
    int detectionR; //detect fire radius
    boolean active; //works or not
    ArrayList<Sensor> neighbourSensor;
    ArrayList<Double> neighborDistance;
    Double[] nd;
    ShortestPathList spl;
    int delay=5;//time delay
    boolean forwardMsg;//if message arrived
    boolean detectFire;
    static LinkedList<Sensor> queue = new LinkedList<Sensor>();
    ArrayList<Sensor> sp_list;
    int hops;
    ArrayList<Sensor> originNeighbor;
   
     
    public Sensor(int xP,int YP,String tS, int senRange,int comRange){
        x= xP;
        y=YP;
        typeOfSensor=tS;
        senR = senRange;
        comR = comRange;
        neighbourSensor= new ArrayList();
        neighborDistance = new ArrayList();
        spl=new ShortestPathList();
        sp_list = new ArrayList<>();
        forwardMsg = false;
        hops = 99;
        originNeighbor = new ArrayList<>();
        detectionR=senR/2;
        active=true;
      
        
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
    public void setDetectR(int newDetect){
        detectionR=newDetect;
    }
    public int getDetectR(){
        return detectionR;
    }
    public void setState(boolean state){
        active=state;
    }
    public boolean getState(){
        return active;
    }
    public void setSenRange(int newSenR){
        senR= newSenR;
    }
    public int getSenRange(){
        return senR;
    }
    public void setComRange(int newComR) {
    	comR = newComR;
    }
    
    public int getComRange() {
    	return comR;
    }
    
    public void setNeighbourSeonsor(ArrayList<Sensor> newNeighbours){
        neighbourSensor=new ArrayList(newNeighbours);
    }
    
    public int getNeighborNumber(){
        return neighbourSensor.size();
    }
    public String getTypeOfSensor(){
        return typeOfSensor;
    }
    
    public boolean getForwardMsg(){
        return forwardMsg;
    }
    public void setForwardMsg(boolean newMsg){
        forwardMsg=newMsg;
    }
    public void setTypeOfSensor(String newType){
        typeOfSensor=newType;
    }
    
    //////////////////////////////
   /* SHORTEST PATH CODE BLOCK */
  /////////////////////////////
    
    public void printNeighbors(){
        for(Sensor s : neighbourSensor){
            System.out.println(s.typeOfSensor);
        }
    }
    
    public  ArrayList<Sensor> getNeighbourSeonsor(){
        return  neighbourSensor;
    }
    
    public double distanceBetweenSensors(Sensor otherSensor){
        
        double d1X = this.x;
        double d1Y = this.y;
        double d2X = otherSensor.x;
        double d2Y = otherSensor.y;
        
        
        double distance = Math.sqrt(Math.pow(d1X-d2X,2) + Math.pow(d1Y-d2Y,2));
        
        
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        System.out.println("d1=("+ d1X + ","+ d1Y + " and d2=(" + d2X + "," + d2Y + ")");
        System.out.println("Distance is :" + df.format(distance));
        
        return distance;
    }
    
    public void printPathToFS(){
        System.out.println("The shorted path for " + this.typeOfSensor + " is ");
        ShortestPathList.printSP();
    }
    
    public void findSP(Sensor target, ShortestPathList spl){
        System.out.println("Calculating shortest path for " + this.typeOfSensor);
        
        shortestPath(target, spl);
    }
    
    //Routing - SP
    public void shortestPath(Sensor target, ShortestPathList spl){
        
        this.setState(true);
        System.out.println(this.typeOfSensor + " just became "+ this.getState());
        

        double maxDist = 0;
        
        int rep = 0;
        int index = 0;
        
        ArrayList<Sensor> properNeighbors = new ArrayList<>();
        ArrayList<Double> properNeighborDistance = new ArrayList<>();
        //exclude neighbors with State = true;
        for(Sensor otherSensor : this.neighbourSensor){
            if(!otherSensor.getState()){
                properNeighbors.add(otherSensor);
                properNeighborDistance.add(otherSensor.distanceBetweenSensors(this));
            }
        }
        
        
        //Check for the distances between the neighbors
        for(double d : properNeighborDistance){
            if(d > maxDist){
                maxDist = d;
                index = rep;
                
            }
            rep ++;
            
        }
        
        //Get the index of FireStation
        int j = 0;
        if(properNeighbors.contains(target)){
            for(Sensor s : properNeighbors){
                if(s.typeOfSensor.equals(target.typeOfSensor)){
                    index = j;
                }
                j++;
            }
        }
        
        System.out.println("Current: " + this.typeOfSensor);
        System.out.println("Next position: " +index);
        
        for(Sensor s : properNeighbors){
            System.out.println(s.typeOfSensor);
        }
        
        
        String case1 = properNeighbors.get(index).typeOfSensor;
        String case2 = target.typeOfSensor;
        System.out.println("Target is: " + case2);
        System.out.println("Next is: " + case1);
        
        
        spl.s_list.add(properNeighbors.get(index));
        //neighborDistanceMatrix.remove(index);
        if(!case1.equals(case2)){
            
            properNeighbors.get(index).shortestPath(target,  spl);
        }
         
    }
    
      
    public void pingNeigbors(ArrayList<Sensor> origin){
        // Keep track of all the neighbors at the current time frame
        ArrayList<Sensor> temp = new ArrayList<>();
        
        // exclude not active sensors
        for(Sensor s : origin){
            if(s.getState()){
                temp.add(s);
            }
        }
        
        this.neighbourSensor = temp;
        
    }
    
    
    
}

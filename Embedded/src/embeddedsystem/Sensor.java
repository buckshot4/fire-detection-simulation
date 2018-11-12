/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


public class Sensor {
    int x;
    int y;
    String typeOfSensor;
    int radious;
    boolean active;
    ArrayList<Sensor> neighbourSensor;
    ArrayList<Double> neighborDistance;
    Double[] nd;
    ShortestPathList spl;
    int delay=5;
    boolean forwardMsg;
    boolean detectFire;
    
    public Sensor(int xP,int YP,String tS, int ratioN){
        x= xP;
        y=YP;
        typeOfSensor=tS;
        radious=ratioN;
        neighbourSensor= new ArrayList();
        neighborDistance = new ArrayList();
        spl=new ShortestPathList();
        
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
    
    public void setState(boolean state){
        active=state;
    }
    public boolean getState(){
        return active;
    }
    public void setRatio(int newRad){
        radious= newRad;
    }
    public int getRatio(){
        return radious;
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
        
        shortestPath(target, this.neighborDistance, spl);
    }
    
    //Routing - SP
    public void shortestPath(Sensor target, ArrayList<Double> neighborDistanceMatrix, ShortestPathList spl){
        
        this.setState(true);
        System.out.println(this.typeOfSensor + " just became "+ this.getState());
        
        //System.out.println("Shortest path ...");
        
        
        
        double maxDist = 0;
        
        int rep = 0;
        int index = 0;
        
        //Check for the disctacnes between the neighbors
        for(double d : neighborDistanceMatrix){
            if(d > maxDist){
                maxDist = d;
                index = rep;
                
            }
            rep ++;
            
        }
        
        //Get the index of FireStation
        int j = 0;
        if(neighbourSensor.contains(target)){
            for(Sensor s : neighbourSensor){
                if(s.typeOfSensor.equals(target.typeOfSensor)){
                    index = j;
                }
                j++;
            }
        }
        
        System.out.println("Current: " + this.typeOfSensor);
        System.out.println("Next position: " +index);
        
        String case1 = neighbourSensor.get(index).typeOfSensor;
        String case2 = target.typeOfSensor;
        System.out.println("Target is: " + case2);
        System.out.println("Next is: " + case1);
        
        
        spl.s_list.add(neighbourSensor.get(index));
        //neighborDistanceMatrix.remove(index);
        if(!case1.equals(case2)){
            
            neighbourSensor.get(index).shortestPath(target, neighbourSensor.get(index).neighborDistance, spl);
        }
        
            
        
        

        
    }
      public void randomFail(ArrayList<Sensor> arraySensors,int howMany){
        Random random= new Random();
        for(int i=0;i<howMany;i++){
        int n=random.nextInt(arraySensors.size());
        arraySensors.get(n).setState(false);
        }
    }
    
    public void pingNeigbors(ArrayList<Sensor> origin){
        // Keep track of all the neighbors at the current time frame
    }
    
    
    
}

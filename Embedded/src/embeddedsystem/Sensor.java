/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author eleonora
 */
public class Sensor {
    int x;
    int y;
    String typeOfSensor;
    int ratio;
    //int fireDetectionRatio;
    boolean active;//working or not
    ArrayList<Sensor> neighbourSensor;
    ArrayList<Double> neighborDistance;
    Double[] nd;
    boolean detrectFire; //if the sensor detected fire
    boolean forwardMsg; //if the sensor got a msg
    static LinkedList<Sensor> queue = new LinkedList<Sensor>(); 
    
            
    public Sensor(int xP,int YP,String tS, int ratioN){
        x= xP;
        y=YP;
        typeOfSensor=tS;
        ratio=ratioN;
        neighbourSensor= new ArrayList();
        neighborDistance = new ArrayList();
    } 
    
  //used to broadcast the message of fire and storing the sensor where the fire is detected. 
  //and then the broadcast method is called. 
  public static void BCM(Sensor currentSensor, Sensor fs) {
  	Sensor fireStart = currentSensor;
  	BC(currentSensor, fs ,fireStart);    	
  }

  //broadcast firedetection.
      public static void BC(Sensor currentSensor, Sensor fs, Sensor firestart) {
      	Sensor neighbor;
      	Sensor s;
      	int numOfNeighbors1 = currentSensor.getNeighborNumber();

      	System.out.println(" ");
  		System.out.println(currentSensor.typeOfSensor+" ");     
      	
  		//goes through all neighbors and adds them to the queue if their state is not active/false. 
      		for(int i = 0; i < numOfNeighbors1; ++i){    
      		neighbor = currentSensor.neighbourSensor.get(i);
      		System.out.print(neighbor.typeOfSensor+" ");
      		if(neighbor.getState()==false) {	
      		//System.out.print(neighbor.typeOfSensor+" ");
      		queue.add(neighbor); 
      		}
      		}
      		//the state of the currentsensor is changed so it cannot be added to the queue again. 
      		currentSensor.setState(true);

  		System.out.println(" ");
      	
  		//takes the first sensor in the queue and checks if it is the fs sensor. 
  		//Otherwise it calls the BC method again with the first sensor in the queue. 
  		while(queue.size() !=0) {
  		s=queue.poll();
  		//System.out.print(s.typeOfSensor+" ");
  		if(s.typeOfSensor.equals(fs.typeOfSensor)) {
      			System.out.println("Reached FS");
      			System.out.print("fire started at: ");
      			System.out.println(firestart.typeOfSensor);
      	  		return;
  		}  		
  		if(s.getState()==false) {
  		BC(s,fs, firestart);
  		}
  		}
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
    public void setRatio(int newRatio){
        ratio= newRatio;
    }
    public int getRatio(){
        return ratio;
    }
    
      public String getTypeOfSensor(){
        return typeOfSensor;
    }
      public void setForwardMsg(boolean msg){
          forwardMsg=msg;
      }
      public boolean getForwardMsg(){
          return forwardMsg;
      }
      public void setDetrectFire(boolean fire){
          detrectFire=fire;
      }
      public boolean getDetectFire(){
          return detrectFire;
      }
     
  
  
  
    
    public void setNeighbourSeonsor(ArrayList<Sensor> newNeighbours){
        neighbourSensor=new ArrayList(newNeighbours);
    }
    
    public ArrayList<Sensor> getNeighbourSeonsor(){
        return neighbourSensor;
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

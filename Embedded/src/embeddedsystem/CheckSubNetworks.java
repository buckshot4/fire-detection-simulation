/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import static embeddedsystem.EmbeddedSystem.setJlabel;
import static embeddedsystem.MyCanvas.sensorList;
import static embeddedsystem.MyCanvas.surface;
import static embeddedsystem.MyCanvas.view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author eleonora
 */
public class CheckSubNetworks {
    static MyThread thread;
    static LinkedList<Sensor> queueNet = new LinkedList<Sensor>(); 
    static ArrayList<Sensor> disconnetedNet=new ArrayList();
    
    static ArrayList<subNet> subNetList=new ArrayList();

   static  Sensor fireS= sensorList.get(sensorList.size()-1);
   static ArrayList <Sensor> remainedSensors= new ArrayList();
 
    
     public static void checkNetworkCuts(Sensor currentSensor,subNet sb) throws InterruptedException {
   	
   		  	checkNetworkCutThread(currentSensor ,currentSensor,sb);   
   		  	
 }
     
  //broadcast firedetection.
     public static void checkNetworkCutThread(Sensor currentSensor, Sensor firestart,subNet sb) throws InterruptedException {
     	Sensor neighbor;
     	Sensor s;
        Graphics g = surface.getGraphics();
     
        g.setColor(Color.GREEN);
        
                int numOfNeighbors1 = currentSensor.getNeighborNumber();
               
                    
     	
 		//goes through all neighbors and adds them to the queue if their state is not active/false. 
     		for(int i = 0; i < numOfNeighbors1; ++i){    
     		neighbor = currentSensor.neighbourSensor.get(i);

     		if(neighbor.getForwardMsg()==false && neighbor.getState()==true) {	
                   // System.out.println("neighbour"+ neighbor.getTypeOfSensor());
     		queueNet.add(neighbor); 
     		}
                
     		}
     		//the state of the currentsensor is changed so it cannot be added to the queue again. 
     		currentSensor.setForwardMsg(true);
              
     		try {
     			
   			//sleep 1 seconds
   			Thread.sleep(200);
   			
   		} catch (InterruptedException e) {
   			e.printStackTrace();
   		}
     		
               
                MyCanvas.fillCenteredCircle((Graphics2D) g, currentSensor.x,currentSensor.y, 9);
  		
                g.dispose();
                view.repaint();
 		//takes the first sensor in the queue and checks if it is the fs sensor. 
 		//Otherwise it calls the BC method again with the first sensor in the queue. 
            
 		while(queueNet.size() !=0) {
 		s=queueNet.poll();
              
 		//System.out.print(s.typeOfSensor+" ");
 		if(s.typeOfSensor.equals("fs")) {        //got to fs
                        MyCanvas.fillCenteredCircle((Graphics2D) g, s.x,s.y, 7);
     			
     			setJlabel(firestart.typeOfSensor);
                       System.out.println("FINISHED");
                       // subNetList.add(new subNet(checkedSesnsors,true));
                       // System.out.println("checkS   "+checkedSesnsors.size() );
                       // checkedSesnsors.clear();
                     
                       sb.setCon(true);
                         checkNet(sb);
 		}	
 		if(s.getForwardMsg()==false) {
                  
 		checkNetworkCutThread(s, firestart,sb);
 		
               }
 		}
                
                
                if(fireS.getForwardMsg()!=true){
                    
                   sb.setCon(false);
                    checkNet(sb);
                }
                  
                
                
            
     }

     
     public static void checkNet(subNet sb) throws InterruptedException{
     Graphics g = surface.getGraphics();
       
       // remainedSensors=remainedS();
       for(int i=0;i<remainedSensors.size()-1;i++){
           if((remainedSensors.get(i).getState()==true)&&(remainedSensors.get(i).getForwardMsg()==false)){
              // canvas.changeElement(sensorList.get(i));
               disconnetedNet.add(sensorList.get(i));
              
           }else if((remainedSensors.get(i).getState()==true)&&(remainedSensors.get(i).getForwardMsg()==true)){
           sb.addToSubN(remainedSensors.get(i));
       }
       
  
    }
       
        subNetList.add(sb);
        remainedSensors.clear();
        remainedSensors.addAll(disconnetedNet);
        checkSubNetworks(false);
    
      
       

     }
     public static void checkSubNetworks(boolean first) throws InterruptedException{
         
         
            
            int sizeD=disconnetedNet.size();
          
            
            if(sizeD>0){
                
            thread= new MyThread(disconnetedNet.get(0),fireS);
            disconnetedNet.clear();
             
            thread.start();
           
          
             thread.join();
         
            }else if((sizeD==0)&&(first)){
               remainedSensors=sensorList;
              thread= new MyThread(sensorList.get(0),fireS);
             
             
            thread.start();
           
             thread.join();
            }else{
       
                
         printSubNets();
         
            }
         
         
     }
    public static void printSubNets(){
        ArrayList <Sensor> sL;
        for(int j=0;j<subNetList.size();j++){
            sL= subNetList.get(j).getSubNet();
           
               System.out.println("SubNetwork "+j+" sensors: ");
            for(int i=0;i<sL.size();i++){
             
                System.out.print(sL.get(i).getTypeOfSensor()+", ");
            }
            
        }
        
        
    }
    
    
   
}
 

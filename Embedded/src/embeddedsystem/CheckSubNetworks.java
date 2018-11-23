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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
                if(numOfNeighbors1==0){
                      sb.setCon(false);
                    checkNet(sb);
                }
                    
     	
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
             
            thread.run();
           
          
             thread.join();
         
            }else if((sizeD==0)&&(first)){
               remainedSensors=sensorList;
              thread= new MyThread(sensorList.get(0),fireS);
             
             
            thread.run();
           
             thread.join();
    
          
            }else{
                thread.stop();
            }
         
         
     }
    
    public static void printSubNets(){
      //   eliminateDoubles();
       JFrame window= EmbeddedSystem.getFrame();
       String connected= "connected";
       String disconneted= "disconneted";
        ArrayList <Sensor> sL;
        for(int j=0;j<subNetList.size();j++){
            JLabel l= new JLabel();
            //JLabel lNumber= new JLabel();
            sL= subNetList.get(j).getSubNet();
            if(subNetList.get(j).getConnectedToFS()){
                l.setText("SubNetwork "+j+" has "+sL.size()+"sensors "+ connected );
            }else{
                 l.setText("SubNetwork "+j+" has "+sL.size()+"sensors "+ disconneted );
            }
            
           System.out.println("SubNetwork "+j+" sensors: ");
         
            l.setBounds(80 ,250+(j*20),200, 3000);
             window.getContentPane().add(l);
            
              // window.getContentPane().add(lNumber);
             
              //lNumber.setBounds(80,250+(j*20),200,300);
            for(int i=0;i<sL.size();i++){
               // JLabel sensor= new JLabel();
                //sensor.setText(sL.get(i).getTypeOfSensor()+", ");
             
                System.out.println(sL.get(i).getTypeOfSensor()+", ");
            }
            
        }
       
        
        
    }
    public static void resetCheckNet(){
        remainedSensors.clear();
        disconnetedNet.clear();
        subNetList.clear();
        
        
    }
    /*
     public static void eliminateDoubles(){
            ArrayList <Sensor> sL;
               ArrayList <Sensor> sLNext;
               int legth=0;
            for(int j=0;j<subNetList.size()-2;j++){
                        sL= subNetList.get(j).getSubNet();
                        sLNext=subNetList.get(j+1).getSubNet();
                        if(sL.size()>=sLNext.size()){
                            legth= sLNext.size()-1;
                               for(int i=0;i<legth;i++){
                            if(sLNext.contains(sL.get(i))){
                                System.out.print("duplicate");
                                 subNetList.remove(j+1);
                            }
                      }  

                        }else{
                           
                        legth=sL.size()-1;
                         System.out.print("HERE"+legth);
                            for(int i=0;i<legth;i++){
                            if(sL.contains(sLNext.get(i))){
                                System.out.print("duplicate");
                                subNetList.remove(j+1);
                               
                            }
                      }  
                        }
                     
                      
                        
            }
            
     }
    */
    
    
   
}
 

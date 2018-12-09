/*
This class analyze the network looking for possible cuts that wouldn't allowed the message to be received by the fire station 
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
                    currentSensor.setForwardMsg(true);
                    
                MyCanvas.fillCenteredCircle((Graphics2D) g, currentSensor.x,currentSensor.y, 9);
                    sb.setCon(false);
                    checkNet(sb);
                }else{
                    
     	
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
                       
                        // checkNet(sb);
 		}	
 		if(s.getForwardMsg()==false) {
                  
 		checkNetworkCutThread(s, firestart,sb);
 		
               }
 		}
                
                
                /*if(fireS.getForwardMsg()!=true){
                        
                   sb.setCon(false);
                   // checkNet(sb);
                }*/
                  checkNet(sb);
                }
                
            
     }

     
     public static void checkNet(subNet sb) throws InterruptedException{
     Graphics g = surface.getGraphics();
       
       // remainedSensors=remainedS();
       for(int i=0;i<remainedSensors.size();i++){
           if((remainedSensors.get(i).getState()==true)&&(remainedSensors.get(i).getForwardMsg()==false)){
              // canvas.changeElement(sensorList.get(i));
               disconnetedNet.add(remainedSensors.get(i));
              
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
     
         
            
            int sizeD=remainedSensors.size();
          
            
            if(sizeD>0){
            for(Sensor s:remainedSensors ){
                if(s.getForwardMsg()){
                    remainedSensors.remove(s);
            }else{
            thread= new MyThread(s,fireS);
            disconnetedNet.clear();
             
            thread.start();
           
          
             thread.join();
                }}
            
            }else if((sizeD==0)&&(first)){
                
                 
                
               remainedSensors.addAll(sensorList);
              thread= new MyThread(sensorList.get(0),fireS);
             
             
            thread.start();
           
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
            if(sL.isEmpty()){
                subNetList.remove(j);
                System.out.println("removed");
            }else{
            if(subNetList.get(j).getConnectedToFS()){
                System.out.println("SubNetwork "+j+" has "+sL.size()+"sensors "+ connected );
            }else{
                     System.out.println("SubNetwork "+j+" has "+sL.size()+"sensors "+ disconneted );
            }
            
          // System.out.println("SubNetwork "+j+" sensors: ");
         
          //  l.setBounds(80 ,250+(j*20),200, 3000);
            // window.getContentPane().add(l);
            
              // window.getContentPane().add(lNumber);
             
              //lNumber.setBounds(80,250+(j*20),200,300);
            for(int i=0;i<sL.size();i++){
               // JLabel sensor= new JLabel();
                //sensor.setText(sL.get(i).getTypeOfSensor()+", ");
             
                System.out.println(sL.get(i).getTypeOfSensor()+", ");
            }
            
        }
       
        }
        
    }
    
    public static void checkDuplicates(){
        subNet current = new subNet();
           subNet next = new subNet();
        for(int i=0;i<subNetList.size();i++){
            current=subNetList.get(i);
           // for()
        }
    }
    
    public static void resetCheckNet(){
        remainedSensors.clear();
        disconnetedNet.clear();
        subNetList.clear();
        
        
    }
    public static int getConnectedSensors(){
        for(subNet sub:subNetList){
            if(sub.getConnectedToFS()){
                return sub.getSubNet().size()-1;
            }
        }
        return 0;
    }
    public static boolean myContains(Sensor s, ArrayList<Sensor> list){
        for(Sensor sen:list){
            if(sen.getTypeOfSensor().equalsIgnoreCase(s.getTypeOfSensor())&&sen.getState()){
                return true;
            }
        }
        return false;
    }
    public static boolean checkSesnorInSubNet(Sensor s){
    	//System.out.print("size:" + subNetList.size());
        for( subNet sub: subNetList){
            if(sub.getConnectedToFS()){
                ArrayList<Sensor> list= sub.getSubNet();
               
            
               if(myContains(s,list)){
                    //System.out.print("connected");
                    return true;
                }else{
                      //System.out.print("disconneted");
                      return false;

                }
            }
        } 
          return false;
                
       
    }
    }
 


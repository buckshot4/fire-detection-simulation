/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import static embeddedsystem.ShortestPathList.s_list;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
/**
 *
 * @author eleonora
 */
public class MyCanvas {
    static JLabel view;
   static BufferedImage surface;
    static  ArrayList<Sensor> sensorList =new ArrayList();
    private Fire fire;
        static LinkedList<Sensor> queue = new LinkedList<Sensor>(); 
    public MyCanvas(){
        
        
        fire= new Fire(650,550,20);
        surface = new BufferedImage(700,600,BufferedImage.TYPE_INT_RGB);
        
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
       g.setColor(Color.GREEN);
        g.fillRect(0,0,700,600);
       sensorList=createSensorList(600,500,110);
       
        g.dispose();
    }

     public void changeElement(){
           Graphics g = surface.getGraphics();
            Sensor s=sensorList.get(7);
            g.setColor(Color.red);
           fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
           
            g.dispose();
            view.repaint();
     }
     
     public static void BCM(Sensor currentSensor, Sensor fs) {
   	  if(currentSensor.getState()==false) {
   		  	BC(currentSensor, fs ,currentSensor);   
   		  	}  	
 }
     
  //broadcast firedetection.
     public static void BC(Sensor currentSensor, Sensor fs, Sensor firestart) {
     	Sensor neighbor;
     	Sensor s;
        Graphics g = surface.getGraphics();
     
        g.setColor(Color.red);
        
           int numOfNeighbors1 = currentSensor.getNeighborNumber();
       	System.out.println(" ");
 		System.out.println(currentSensor.typeOfSensor+" ");     
     	
 		//goes through all neighbors and adds them to the queue if their state is not active/false. 
     		for(int i = 0; i < numOfNeighbors1; ++i){    
     		neighbor = currentSensor.neighbourSensor.get(i);
     		System.out.print(neighbor.typeOfSensor+" ");
     		if(neighbor.setforwardMsg()==false && neighbor.getState()==false) {	
	        //System.out.print(neighbor.typeOfSensor+" ");
     		queue.add(neighbor); 
     		}
     		}
     		//the state of the currentsensor is changed so it cannot be added to the queue again. 
     		currentSensor.setforwardMsg(true);
               
     		try {
     			
   			//sleep 1 seconds
   			Thread.sleep(500);
   			
   		} catch (InterruptedException e) {
   			e.printStackTrace();
   		}
     		
               
                fillCenteredCircle((Graphics2D) g, currentSensor.x,currentSensor.y, 5);
  		System.out.println(" ");
                g.dispose();
                   view.repaint();
 		//takes the first sensor in the queue and checks if it is the fs sensor. 
 		//Otherwise it calls the BC method again with the first sensor in the queue. 
 		while(queue.size() !=0) {
 		s=queue.poll();
 		//System.out.print(s.typeOfSensor+" ");
 		if(s.typeOfSensor.equals(fs.typeOfSensor)) {
                    fillCenteredCircle((Graphics2D) g, s.x,s.y, 7);
     			System.out.println("Reached FS");
     			System.out.print("fire started at: ");
     			System.out.println(firestart.typeOfSensor);
     	  		//return;
 		}  		
 		if(s.setforwardMsg()==false) {
                   
 		BC(s,fs, firestart);
 		
               }
 		}
     }
     
    /* public void test(ArrayList<Sensor> n) throws InterruptedException{
          Graphics g = surface.getGraphics();
      
         Sensor neighbour;
         g.setColor(Color.red);
         for(int i=0;i<n.size();i++){
          neighbour=n.get(i);
          //TimeUnit.SECONDS.sleep(1);
          if(neighbour.getTypeOfSensor().equalsIgnoreCase("fs")){
              fillCenteredCircle((Graphics2D) g,neighbour.getPositionX(),neighbour.getPositionY(), 7);
          }else{
              if(neighbour.getState()){
                  
              }else{
              neighbour.setState(true);
              fillCenteredCircle((Graphics2D) g,neighbour.getPositionX(),neighbour.getPositionY(), 5);
             // TimeUnit.SECONDS.sleep(1);
              test(neighbour.getNeighbourSeonsor());
           
           
               g.dispose();
            view.repaint();
              }
         }
         }
           
     }
     public void fireDetection(Sensor s) throws InterruptedException{
         Graphics g = surface.getGraphics();
      
         Sensor neighbour;
         g.setColor(Color.red);
         fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
         
           for(int i=0;i<s.getNeighborNumber();i++){
            neighbour=s.getNeighbourSeonsor().get(i);
            if(neighbour.getTypeOfSensor().equalsIgnoreCase("fs")){
                fillCenteredCircle((Graphics2D) g,neighbour.getPositionX(),neighbour.getPositionY(), 7);
            }else{
                if(neighbour.getState()){
                    
                }else{
                neighbour.setState(true);
                fillCenteredCircle((Graphics2D) g,neighbour.getPositionX(),neighbour.getPositionY(), 5);
                g.dispose();
                view.repaint();
                 test(neighbour.getNeighbourSeonsor());
                 } 
            }
            
            }
           
        
     }
      */
         public static ArrayList<Sensor> createSensorList(int width, int height, int radious){

        ArrayList sensorList = new ArrayList<> ();
        Sensor tempSensor = null;
        
        //here we select 50 as every 50 meters we put a sensor
        int step = 50;
        int name = 0;
        
        for(int w = step; w < width; w = w + step){
            for(int h = step; h < height; h = h +step){
                if((w == width/2) && (h == height / 2) ){
                    tempSensor = new Sensor(w, h, "fs",radious);
                    
                }
                else{
                    tempSensor = new Sensor(w, h, "s" + Integer.toString(name),radious);
                }
                tempSensor.setState(false);
                
                sensorList.add(tempSensor);
                name ++;
            }
        }
        
        
        return sensorList;
    }
    public void drawLine(Sensor s,ArrayList<Sensor> l){
         
         Graphics g = surface.getGraphics();
          g.setColor(Color.BLUE);
          g.drawLine(s.x,s.y,l.get(0).getPositionX(),l.get(0).getPositionY());
          for(int i=0;i<l.size()-1;i++){
           g.drawLine(l.get(i).getPositionX(), l.get(i).getPositionY(), l.get(i+1).getPositionX(), l.get(i+1).getPositionY());
          }
        g.dispose();
        view.repaint();
     }
     
     public void weatherChange(int power){
           Graphics g = surface.getGraphics();
         Sensor s;
      g.setColor(Color.ORANGE);
        g.fillRect(0,0,800,700);
        
         for(int i=0;i<sensorList.size(); i++){
         s=sensorList.get(i);
         s.setRatio(s.radious-power);
        // drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), (s.ratio-power));
         }
         
         drawSensor();
          g.dispose();
            view.repaint();
     }
     
    

    
     public void drawSensor(){
          Graphics g = surface.getGraphics();
         Sensor s;
       
            for(int i=0;i<sensorList.size(); i++){
                s=sensorList.get(i);
                if(s.getTypeOfSensor().equalsIgnoreCase("fs")){
                     g.setColor(Color.BLUE);
               drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.radious);
                g.setColor(Color.BLACK);
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 7);
                }else{
                g.setColor(Color.BLACK);
               drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.radious);
              
                g.setColor(Color.BLACK);
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
            }
                    
           
     }
     }
        public static void fillCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x-(r/2);
         y = y-(r/2);
         g.fillOval(x,y,r,r);
    }
     public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x-(r/2);
         y = y-(r/2);
         g.drawOval(x,y,r,r);
    }
     
       public boolean distanceFireSensor(Sensor otherSensor){
        boolean b;
        double d1X = fire.getPositionX();
        double d1Y = fire.getPositonY();
        double d2X = otherSensor.x;
        double d2Y = otherSensor.y;
        int fireRange=fire.getRange();
       int fireDetected=(int)((fireRange/2)+(otherSensor.radious/2));
       
        
        double distance = Math.sqrt(Math.pow(d1X-d2X,2) + Math.pow(d1Y-d2Y,2));
          System.out.println("DistanceF:"+ fire.getRange()/2);
              System.out.println("DistanceS:"+ otherSensor.radious/2);
        System.out.println("Distance:"+distance+"  FireD:"+fireDetected);
        
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
       // System.out.println("d1=("+ d1X + ","+ d1Y + " and d2=(" + d2X + "," + d2Y + ")");
        //System.out.println("Distance is :" + df.format(distance));
        if(fireDetected>(distance+10)){
            b=true;
        }else{
            b=false;
        }
        return b;
    }
      
     public void setFire(){
          Graphics g = surface.getGraphics();
         Color c=new Color(1f,0f,0f,.1f );
         g.setColor(c);
         fillCenteredCircle((Graphics2D) g,fire.getPositionX(),fire.getPositonY(),fire.getRange());
         fire.setRange((fire.getRange()+20));
            g.dispose();
            view.repaint();
     }
 
}

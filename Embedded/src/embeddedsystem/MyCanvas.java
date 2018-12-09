/*
 *This class takes care of every graphic aspect of the simulation
 */
package embeddedsystem;


import static embeddedsystem.EmbeddedSystem.setJlabel;
import static embeddedsystem.MyCanvas.sensorList;
import static embeddedsystem.ShortestPathList.s_list;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import static java.lang.Thread.sleep;
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
    
    private Graphics g ;
    static JLabel view;
    static BufferedImage surface;
    static  ArrayList<Sensor> sensorList =new ArrayList();
    static  ArrayList<Sensor> sensorListsaved =new ArrayList();
    static ArrayList<Sensor> failedS= new ArrayList();
    static ArrayList<Sensor> SentSensors = new ArrayList();
       private static Fire firegrid;
    private Fire fire;
    
    static LinkedList<Sensor> queue = new LinkedList<Sensor>(); 
    static LinkedList<Sensor> FireDetectedqueue = new LinkedList<Sensor>(); 
    static ArrayList<Sensor> disconneted;
    static LinkedList<Fire> FireDetectedqueue2 = new LinkedList<Fire>(); 
     static boolean fireStop = false;
    
      //sensor layout and info
       static int height = 600;
       static int width = 600;
       static int SensorAmount = 121;
       static int SensingRange = 50;
       static int CommunicationRange = 120;
       static int mode = 5;
       
    
    public MyCanvas(){
        disconneted=new ArrayList();
        Random r= new Random();
        
        fire= new Fire(r.nextInt(750)+50,r.nextInt(650)+50,20);
        surface = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
        
        view = new JLabel(new ImageIcon(surface));
      
        Graphics g = surface.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0,600,600);
        g.dispose();
    }

     public void changeElement(Sensor s){
           Graphics g = surface.getGraphics();
           
            g.setColor(Color.BLUE);
           fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 20);
           
            g.dispose();
            view.repaint();
     }
     
    
     public void drawSubNetwork(ArrayList<Sensor> sr){
           Graphics g = surface.getGraphics();
         Sensor s;
        g.setColor(Color.WHITE);
        g.fillRect(0,0,800,700);
      //FailedSensors
        for(int i=0;i<failedS.size();i++){
            s=failedS.get(i);
                       g.setColor(Color.RED);
                     drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.comR);
           
                      fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 7);
        }
       //DRAW FS
              if(sensorList.get(sensorList.size()-1).getTypeOfSensor().equalsIgnoreCase("fs")){
                  s=sensorList.get(sensorList.size()-1);
                       g.setColor(Color.RED);
                     drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.comR);
                    
                     g.setColor(Color.BLUE);
                      fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 7);
              }
        //subNet
            for(int i=0;i<sr.size(); i++){
                s=sr.get(i);
          
                g.setColor(Color.BLACK);
                drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.senR);
                 g.setColor(Color.BLUE);
               drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.comR);
                g.setColor(Color.BLACK);
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
      
        }
            for(int i=0;i<sensorList.size();i++){
                Sensor sensor=sensorList.get(i);
                if((sr.contains(sensor))||(failedS.contains(sensor))){
                    
                }else{
                 g.setColor(Color.PINK);
                drawCenteredCircle ((Graphics2D) g,sensor.getPositionX(),sensor.getPositionY(), sensor.senR);
                 g.setColor(Color.PINK);
               drawCenteredCircle ( (Graphics2D)g,sensor.getPositionX(),sensor.getPositionY(), sensor.comR);
                g.setColor(Color.PINK);
                fillCenteredCircle((Graphics2D) g,sensor.getPositionX(),sensor.getPositionY(), 5);
                }
            }
            
           g.dispose();
         view.repaint();  
         
         
     }
     
  //Broadcasting Method   
     public static void BCM(Sensor currentSensor, Sensor fs) throws InterruptedException {
   	  if(currentSensor.getState()==true) {
   		  	BC(currentSensor, fs ,currentSensor);   
   		  	}  	
 }
     
  //broadcast firedetection.
     public static void BC(Sensor currentSensor, Sensor fs, Sensor firestart) throws InterruptedException {
     	Sensor neighbor;
     	Sensor s;
        Graphics g = surface.getGraphics();
     
        g.setColor(Color.GREEN);
        
                int numOfNeighbors1 = currentSensor.getNeighborNumber();
               
                    
               
                System.out.println(" ");
 		System.out.println(currentSensor.typeOfSensor+" ");     
     	
 		//goes through all neighbors and adds them to the queue if their state is not active/false. 
     		for(int i = 0; i < numOfNeighbors1; ++i){    
     		neighbor = currentSensor.neighbourSensor.get(i);
     		System.out.print(neighbor.typeOfSensor+" ");
     		if(neighbor.getForwardMsg()==false && neighbor.getState()==true) {	
	        //System.out.print(neighbor.typeOfSensor+" ");
     		queue.add(neighbor); 
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
     		
     		
               
                fillCenteredCircle((Graphics2D) g, currentSensor.x,currentSensor.y, 9);
  		System.out.println(" ");
                g.dispose();
                view.repaint();
 		//takes the first sensor in the queue and checks if it is the fs sensor. 
 		//Otherwise it calls the BC method again with the first sensor in the queue. 
 		while(queue.size() !=0) {
 		s=queue.poll();
 		if(s.typeOfSensor.equals(fs.typeOfSensor)) {
                    fillCenteredCircle((Graphics2D) g, s.x,s.y, 7);
     			System.out.println("Reached FS");
     			System.out.print("fire started at: ");             
 		}  		
 		if(s.getForwardMsg()==false) {
                  
 		BC(s,fs, firestart);
 		
               }
 		}
            
     }
     public static boolean checkFireStation(){
         int i=0;
         for(Sensor s: sensorList.get(sensorList.size()-1).getNeighbourSeonsor()){
             if(s.getState()==false){
                 i++;
             }
         }
         if(i==sensorList.get(sensorList.size()-1).getNeighbourSeonsor().size()){
             return false;
         }else{
             return true;
         }
         
     }
     
     //Creates the arrayList of sensors for each mode
    public static ArrayList<Sensor> createSensorList2(int width, int height, int senR, int comR, int numOfSensors){

               // ArrayList sensorList = new ArrayList<> ();
                Sensor tempSensor = null;
                Sensor tempSens2 = null;
                Random random = new Random();
                
                //here we select 50 as every 50 meters we put a sensor
                int step = 50;
                int name = 0;
                
                //grid system
               if(mode==0)
                for(int w = 0; w < width-50; w = w + step){
                    for(int h = 0; h < height-50; h = h +step){
                    	int n = random.nextInt(comR/5);
                        if((w == width-step-50) && (h == height-step-50) ){
                            tempSensor = new Sensor(w, h, "fs", senR, comR+n);                  
                        }
                        else{
                            tempSensor = new Sensor(w, h, "s" + Integer.toString(name), senR, comR+n);
                        }
                        tempSensor.setState(true);
                        
                        sensorList.add(tempSensor);
                        name ++;
                    }
                }
                
                //completely random
       if(mode==1) {
                    
                    for(int i = 0; i < numOfSensors-3; ++i){
                    	int w = random.nextInt(width-step-50);
                    	int h = random.nextInt(height-step-50);
                        	int n = random.nextInt(comR/5);
                            tempSensor = new Sensor(w, h, "s" + Integer.toString(name), senR, comR+n);
                            tempSensor.setState(true);                        
                            sensorList.add(tempSensor);
                            name ++;
                        }
                    
                    tempSensor = new Sensor(width-senR*2-step-50, height-senR*2-step-50, "s"  + Integer.toString(name), senR, comR+comR/5);  
                    tempSensor.setState(true);                        
                    sensorList.add(tempSensor); 
                    name ++;
                    
                    tempSensor = new Sensor(width-senR-step-50, height-senR-step-50, "s"  + Integer.toString(name), senR, comR+comR/5);  
                    tempSensor.setState(true);                        
                    sensorList.add(tempSensor); 
                    name ++;
                    
                    tempSensor = new Sensor(width-step-50, height-step-50, "fs", senR, comR+comR/5);  
                    tempSensor.setState(true);                        
                    sensorList.add(tempSensor); 
                }
                
                int midX = width/2;
                int midY = height/2;
                
                //semi random
                if(mode==2) {
                    
                    int preX = midX;
                    int preY = midY;
                    for(int i = 0; i < numOfSensors-1; ++i){
                    	int n = random.nextInt(comR/5);
                    	int w = (random.nextInt(comR/2)-comR/4)*2;
                    	int h = (random.nextInt(comR/2)-comR/4)*2;
                    	
                    	if(preX > height || preX < 0 || preY > width || preY < 0) {
                    		preX = midX+w; 
                    		preY = midY+h;          	
                    	}                             	
  
                            tempSensor = new Sensor(preX, preY, "s" + Integer.toString(name), senR, comR+n); 
                        	preX = preX+w;
                        	preY = preY+h;
                            tempSensor.setState(true);                        
                            sensorList.add(tempSensor);                         
                            name ++;
                        }
                    tempSensor = new Sensor(preX, preY, "fs", senR, comR);  
                    tempSensor.setState(true);                        
                    sensorList.add(tempSensor); 
                }   
                
                
                
                midX = width/2;
                midY = height/2;
                int preX = midX;
                int preY = midY;
                // semi random grid
                if(mode==3) { 
                    //for(int i = 0; i < numOfSensors-1; ++i){
                    for(int w = 50; w < width; w = w+random.nextInt(comR/3)+comR/5){
                        for(int h = 50; h < height; h = h+random.nextInt(comR/3)+comR/5){
                        	preX = w +(random.nextInt(comR/2)-comR/2);
                        	preY = h + (random.nextInt(comR/2)-comR/2);

                    	int n = random.nextInt(comR/5);
                    	   
                            tempSensor = new Sensor(preX, preY, "s" + Integer.toString(name), senR, comR+n); 
                        	//preX = preX+w;
                        	//preY = preY+h;
                            tempSensor.setState(true);                        
                            sensorList.add(tempSensor);                         
                            name ++;
                        }
                    }
                    tempSensor = new Sensor(width-comR/3, height-comR/3, "fs", senR, comR+comR/5);  
                    tempSensor.setState(true);                        
                    sensorList.add(tempSensor); 
                }
                
                
                
             
                int sectionX = 8;
                int sectionY = 8;
                
                int sections = sectionY*sectionX;
                
                int widthX = width/sectionX-10;
                int heightY = height/sectionY-10;          
                
                int w = 0;
                int h = 0;
            
           //SEMI RANDOM SECTION
           if(mode==4) { 
                	
                	while (name < numOfSensors-3) {
                    for(w = 0; w < width-widthX-50; w=w+widthX){                   	

                        for(h = 0; h < height-heightY-50; h=h+heightY){
                        	if (name < numOfSensors-3) {
                        	preX = w +(random.nextInt(widthX));
                        	preY = h + (random.nextInt(heightY));

                    	int n = random.nextInt(comR/5);
                    	   
                            tempSensor = new Sensor(preX, preY, "s" + Integer.toString(name), senR, comR+n); 
 
                            tempSensor.setState(true);                        
                            sensorList.add(tempSensor);                         
                            name ++;
                        	}
                    }
                    }
                    w = 0;
                    h = 0;
                	}
                	
                    tempSensor = new Sensor(width-senR*2-step-50, height-senR*2-step-50, "s"  + Integer.toString(name), senR, comR+comR/5);  
                    tempSensor.setState(true);                        
                    sensorList.add(tempSensor); 
                    name ++;
                    
                    tempSensor = new Sensor(width-senR-step-50, height-senR-step-50, "s"  + Integer.toString(name), senR, comR+comR/5);  
                    tempSensor.setState(true);                        
                    sensorList.add(tempSensor); 
                    name ++;
                    
                    tempSensor = new Sensor(width-step-50, height-step-50, "fs", senR, comR+comR/5);  
                    tempSensor.setState(true);                        
                    sensorList.add(tempSensor); 
                }
             
                //OPTIMAL GRID
                 if(mode==5){
    
                for(int x = 25; x < width-25; x = x + step){
                    for(int y = 25; y < height-25; y = y +step){
                    	int n = random.nextInt(comR/5);
                        if((x == width-(step+25)) && (y == height-(step+25)) ){
                            tempSensor = new Sensor(x, y, "fs", senR, comR+n);                  
                        }
                        else{
                            tempSensor = new Sensor(x, y, "s" + Integer.toString(name), senR, comR+n);
                        }
                        tempSensor.setState(true);
                        
                        sensorList.add(tempSensor);
                        name ++;
                    }
                }
                     if(sensorList.size()>121){
                         System.out.print(sensorList.size());
                        System.out.println("error");
                         }
                    for(int i=0;i<sensorList.size();i++){
                      if( FileInport.values[4][i]==0){
                          sensorList.get(i).setState(false);
                          failedS.add(sensorList.get(i));
                      }
           
                     }
                    
                
                 }

                return sensorList;
            }
    
//used to display the message sent if the networks implements the routing protocol
      public static void drawThickLine(Sensor s,ArrayList<Sensor> l){
            Graphics g = surface.getGraphics();
            int thickness=5;
          g.setColor(Color.BLUE);
   
                int x1=(s.getPositionX());
                  int x2=  (l.get(0).getPositionX()); 
                  int y2=(l.get(0).getPositionY());
                  int y1=(s.getPositionY());
                int dX = x2 - x1;
                int dY = y2 - y1;
            
                     double lineLength = Math.sqrt(dX * dX + dY * dY);

                     double scale = (double)(thickness) / (2 * lineLength);

          
                     double ddx = -scale * (double)dY;
                    double ddy = scale * (double)dX;
                    ddx += (ddx > 0) ? 0.5 : -0.5;
                    ddy += (ddy > 0) ? 0.5 : -0.5;
                     int dx = (int)ddx;
                     int dy = (int)ddy;

                     int xPoints[] = new int[4];
                     int yPoints[] = new int[4];

                     xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
                     xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
                      xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
                         xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;

                     g.fillPolygon(xPoints, yPoints, 4);
                     
                     for(int i=0;i<l.size()-1;i++){
                           x1=(l.get(i).getPositionX());
                           x2=  (l.get(i+1).getPositionX()); 
                           y2=(l.get(i+1).getPositionY());
                             y1=(l.get(i).getPositionY());
                             dX = x2 - x1;
                            dY = y2 - y1;
      
                             lineLength = Math.sqrt(dX * dX + dY * dY);

                             scale = (double)(thickness) / (2 * lineLength);

           
                             ddx = -scale * (double)dY;
                              ddy = scale * (double)dX;
                             ddx += (ddx > 0) ? 0.5 : -0.5;
                             ddy += (ddy > 0) ? 0.5 : -0.5;
                             dx = (int)ddx;
                             dy = (int)ddy;
                         
                
                  

                     xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
                     xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
                      xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
                         xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;

                     g.fillPolygon(xPoints, yPoints, 4);
                     }
        
        g.dispose();
        view.repaint();
          
      }
     
  
  //Used to change the sensing range and display the change in the canvas   
     public void weatherChange(int power){
           Graphics g = surface.getGraphics();
         Sensor s;
         g.setColor(Color.WHITE);
        g.fillRect(0,0,800,700);
              ShortestPathList.n_list.clear();
            ShortestPathList.s_list.clear();
                    
         for(int i=0;i<sensorList.size(); i++){
           
         s=sensorList.get(i);
          s.neighbourSensor.clear();
         s.setComRange(s.comR-power);
        // drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), (s.ratio-power));
         }
         
         drawSensor();
          g.dispose();
            view.repaint();
     }
     
     //Redraw a white canvas
     public void resetCanvas(){
         Graphics g = surface.getGraphics();
         Sensor s;
         g.setColor(Color.WHITE);
        g.fillRect(0,0,800,700);
         drawSensor();
   
     }
     //Used to change the layout of the network based on the selected mode
    public void changeSensorList(int number){
          sensorList.clear();
              Graphics g = surface.getGraphics();
         Sensor s;
         g.setColor(Color.WHITE);
        g.fillRect(0,0,800,700);
        mode=0;
        sensorList= MyCanvas.createSensorList2(MyCanvas.width,MyCanvas.height,MyCanvas.SensingRange,MyCanvas.CommunicationRange,MyCanvas.SensorAmount);
           
        if(sensorList.size()>121){
            System.out.println("error");
        }
         for(int i=0;i<121;i++){
            if( FileInport.values[number][i]==0){
                sensorList.get(i).setState(false);
                
            }
             
         }
         drawSensor();
         
    }

    // It draws the sensor in the sensorList array as black points inside 2 concentric circles, the sensing range and the comunication range
     public void drawSensor(){
          Graphics g = surface.getGraphics();
         Sensor s;
       
            for(int i=0;i<sensorList.size(); i++){
                s=sensorList.get(i);
                if(s.getTypeOfSensor().equalsIgnoreCase("fs")){
                     g.setColor(Color.BLACK);
                      s.setSenRange(s.senR-2);
                    
                    drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.senR);
                   // drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.comR);
                    drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.senR);
                       g.setColor(Color.RED);
                     drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.comR);
                     s=sensorList.get(i);
                     g.setColor(Color.BLUE);
                      fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 7);
                }else if(s.getState()){
              
                g.setColor(Color.BLACK);
                drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.senR);
                 g.setColor(Color.BLUE);
                drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.comR);
                g.setColor(Color.BLACK);
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
            }
                    
           
     }
           g.dispose();
         view.repaint();  
     }
    // It changes the color of the sensors that are failed to red
    public void drawFailedSensors () {
    	 Graphics g = surface.getGraphics();
         Sensor s;
    	
    	   for(int i=0;i<sensorList.size(); i++){
               s=sensorList.get(i);
    	if(s.getState()==false) {
       	 g.setColor(Color.BLACK);
            drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.senR);
             g.setColor(Color.RED);
            drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.comR);
            g.setColor(Color.RED);
            fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 8);
       }
    	   }
    	   g.dispose();
           view.repaint();  
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
     
     //calculates distance between fire and sensor for shortest path and broadcasting
     //and returns sensors that have detected fire
     public Sensor distanceFireSensor(){
    	 Sensor sp=null;
    	 Sensor s=null;
         double d1X = fire.getPositionX();
         double d1Y = fire.getPositonY();
         Sensor otherSensor;
         for(int i=0;i<sensorList.size();i++){
           otherSensor=sensorList.get(i);
         double d2X = otherSensor.x;
         double d2Y = otherSensor.y;
         int fireRange=fire.getRange();
        int fireDetected=(int)((fireRange/2)+(otherSensor.senR/2));
    
         double distance = Math.sqrt(Math.pow(d1X-d2X,2) + Math.pow(d1Y-d2Y,2));

         DecimalFormat df = new DecimalFormat("#.###");
         df.setRoundingMode(RoundingMode.CEILING);

         if(fireDetected>(distance+10) && otherSensor.getForwardMsg()==false && otherSensor.getState()==true){
        	 s= otherSensor;
        	 if(!SentSensors.contains(s)) {
        	 SentSensors.add(s);
        	 FireDetectedqueue.add(s);
        	 }
         }
         }
         return FireDetectedqueue.poll();
      }
      
     //used to calculate the distance between sensors and the fire grid and returns sensors that have detected fire.   
     public int distanceFireSensor2(){
    	 FireDetectedqueue2.clear();
    	 int count = 0;
    	 System.out.println("started");
    	 ArrayList<Fire> FireList = Fire.createFireList();
      for(int j = 0; j < FireList.size(); j++) {  
         fire = FireList.get(j);
         double d1X = fire.getPositionX();
         double d1Y = fire.getPositonY();
         Sensor otherSensor;
         for(int i=0;i<sensorList.size();i++){
           boolean inside = false;
           otherSensor=sensorList.get(i);
    	    if (CheckSubNetworks.checkSesnorInSubNet(otherSensor)){
     		    for(int n = 0; n < otherSensor.neighbourSensor.size(); n++) {
     		    if(CheckSubNetworks.checkSesnorInSubNet(otherSensor.neighbourSensor.get(n))) {
      			   n = otherSensor.neighbourSensor.size();
      			   inside = true;
      			 //System.out.println("sensor inside " + otherSensor.typeOfSensor);
      		    }  			  
      		    }
      	   	    }
         double d2X = otherSensor.x;
         double d2Y = otherSensor.y;
         int fireRange=fire.getRange();
        int fireDetected=(int)((fireRange/2)+(otherSensor.senR/2));
         
         double distance = Math.sqrt(Math.pow(d1X-d2X,2) + Math.pow(d1Y-d2Y,2));

         DecimalFormat df = new DecimalFormat("#.###");
         df.setRoundingMode(RoundingMode.CEILING);

         if(fireDetected>(distance) && otherSensor.getState()==true && inside == true){
        	 System.out.println("sensor detected fire" + otherSensor.typeOfSensor);
        	 if(!FireDetectedqueue2.contains(fire)) {
        	 System.out.println("sensor added" + otherSensor.typeOfSensor);
        	 FireDetectedqueue2.add(fire);
        	}
         }
         }
         }
         return FireDetectedqueue2.size();
         }
     
     //used for random fire
     public void setFire(){
          
          Graphics g = surface.getGraphics();
         Color c=new Color(1f,0f,0f,.4f );
         g.setColor(c);
        drawCenteredCircle((Graphics2D) g,fire.getPositionX(),fire.getPositonY(),fire.getRange());
         if(fireStop ==false)
         {
        	
         fire.setRange((fire.getRange()+20));
     }
         else 
         {
        	 
        	fire.setRange((fire.getRange()));
     }
            g.dispose();
            view.repaint();
     }
     
     //used to implement the fire grid
      public void setFire2(){
    	 ArrayList<Fire> FireList = Fire.createFireList();
         for(int j = 0; j < FireList.size(); j++) {      
         fire = FireList.get(j);
         Graphics g = surface.getGraphics();
        Color c=new Color(1f,0f,0f,.4f );
        g.setColor(c);
       drawCenteredCircle((Graphics2D) g,fire.getPositionX(),fire.getPositonY(),fire.getRange());
         g.dispose();
         view.repaint();
         }
    }
      
      
   //Used to fail random sensors    
        public static void randomFail(int howMany){
   
        int number=0;
        Graphics g = surface.getGraphics();
        g.setColor(Color.RED);
        Random random= new Random();
        for(int i=0;i<howMany;i++){
        int n=random.nextInt(sensorList.size());
             if(sensorList.get(n).getTypeOfSensor().equals("fs")||(n==0)){
            i--;
            }else{
             number++;
             if(failedS.contains(sensorList.get(n))){
                 i--;
             }else{
         failedS.add(sensorList.get(n));
         
         System.out.println(number+"  "+sensorList.get(n).getTypeOfSensor());    
        sensorList.get(n).setState(false);
        fillCenteredCircle((Graphics2D) g,sensorList.get(n).getPositionX(),sensorList.get(n).getPositionY(),8);
        }
             }
        }
        System.out.print("Size failed"+failedS.size());
        g.dispose();
         view.repaint();
    }

   

 
}
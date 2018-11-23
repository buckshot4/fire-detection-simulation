/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;


import static embeddedsystem.EmbeddedSystem.setJlabel;
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
    private Fire fire;
    static LinkedList<Sensor> queue = new LinkedList<Sensor>(); 
    static ArrayList<Sensor> disconneted;
   
     static boolean fireStop = false;
    
           static int height = 600;
       static int width = 600;
       static int SensorAmount = 60;
       static int SensingRange = 50;
       static int CommunicationRange = 120;
       static int mode = 0;
       
    
    public MyCanvas(){
        disconneted=new ArrayList();
        Random r= new Random();
        
        fire= new Fire(r.nextInt(800),r.nextInt(700),20);
        surface = new BufferedImage(800,700,BufferedImage.TYPE_INT_RGB);
        
        view = new JLabel(new ImageIcon(surface));
      
        Graphics g = surface.createGraphics();
             
            
         g.setColor(Color.WHITE);
        g.fillRect(0,0,800,700);
      
       
        g.dispose();
    }

     public void changeElement(Sensor s){
           Graphics g = surface.getGraphics();
           
            g.setColor(Color.ORANGE);
           fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 10);
           
            g.dispose();
            view.repaint();
     }
     
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
 		//System.out.print(s.typeOfSensor+" ");
 		if(s.typeOfSensor.equals(fs.typeOfSensor)) {
                    fillCenteredCircle((Graphics2D) g, s.x,s.y, 7);
     			System.out.println("Reached FS");
     			System.out.print("fire started at: ");
     			setJlabel(firestart.typeOfSensor);
                       
                       
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
     
    public static ArrayList<Sensor> createSensorList(int width, int height, int radious){

        ArrayList sensorList = new ArrayList<> ();
        Sensor tempSensor = null;
        
        //here we select 50 as every 50 meters we put a sensor
        int step = 50;
        int name = 0;
        
        for(int w = step; w < width; w = w + step){
            for(int h = step; h < height; h = h +step){
                if((w == width-step) && (h == height-step) ){
                    tempSensor = new Sensor(w+100, h+100, "fs",radious,0);
                    
                }
                else{
                    tempSensor = new Sensor(w+100, h+100, "s" + Integer.toString(name),radious,0);
                }
                tempSensor.setState(true);
                
                sensorList.add(tempSensor);
                name ++;
            }
        }
        
        
        return sensorList;
    }
         
         public static ArrayList<Sensor> createRandomSensorList(int width, int height, int radious){

        ArrayList<Sensor> sensorRandomList = new ArrayList<> ();
        Sensor tempSensor = null;
        
        //here we select 50 as every 50 meters we put a sensor and plus some random meters
        int step = 50;
        int name = 0;
        int rand = 0;
        Random r = new Random();
        
        for(int w = step; w < width; w = w + step + rand){
            for(int h = step; h < height; h = h +step + rand){
              
                    tempSensor = new Sensor(w, h, "s" + Integer.toString(name),radious,0);
                    name ++;
               
                //tempSensor.setState(false);
                rand = r.nextInt(10-0) + 0;
                
                sensorRandomList.add(tempSensor);
                
            }
        }
        sensorRandomList.get(sensorRandomList.size()-1).setTypeOfSensor("fs");
        
        
        
        
        return sensorRandomList;
    }
         
         
                  public static ArrayList<Sensor> createSensorList2(int width, int height, int senR, int comR, int numOfSensors){

                ArrayList sensorList = new ArrayList<> ();
                Sensor tempSensor = null;
                Sensor tempSens2 = null;
                Random random = new Random();
                
                //here we select 50 as every 50 meters we put a sensor
                int step = 50;
                int name = 0;
                
                //grid system
                if(mode==0)
                for(int w = step; w < width; w = w + step){
                    for(int h = step; h < height; h = h +step){
                    	int n = random.nextInt(comR/5);
                        if((w == width-step) && (h == height-step) ){
                            tempSensor = new Sensor(w+100, h+100, "fs", senR, comR+n);                  
                        }
                        else{
                            tempSensor = new Sensor(w+100, h+100, "s" + Integer.toString(name), senR, comR+n);
                        }
                        tempSensor.setState(true);
                        
                        sensorList.add(tempSensor);
                        name ++;
                    }
                }
                
                //total random
                if(mode==1) {
                    
                    for(int i = 0; i < numOfSensors-1; ++i){
                    	int w = random.nextInt(width);
                    	int h = random.nextInt(height);
                        	int n = random.nextInt(comR/5);
                            tempSensor = new Sensor(w, h, "s" + Integer.toString(name), senR, comR+n);
                            tempSensor.setState(true);                        
                            sensorList.add(tempSensor);
                            name ++;
                        }
                    tempSensor = new Sensor(width, height, "fs", senR, comR);  
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
                    	//int w = random.nextInt(comR/2-senR/2)+senR/3;
                    	//int h = random.nextInt(comR/2-senR/2)+senR/3;     
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
                   
 
                return sensorList;
            }
         
      public void drawThickLine(Sensor s,ArrayList<Sensor> l){
            Graphics g = surface.getGraphics();
            int thickness=5;
          g.setColor(Color.BLUE);
                int x1=(s.getPositionX());
                  int x2=  (l.get(0).getPositionX()); 
                  int y2=(l.get(0).getPositionY());
                  int y1=(s.getPositionY());
                int dX = x2 - x1;
                int dY = y2 - y1;
             // line length
                     double lineLength = Math.sqrt(dX * dX + dY * dY);

                     double scale = (double)(thickness) / (2 * lineLength);

             // The x,y increments from an endpoint needed to create a rectangle...
                     double ddx = -scale * (double)dY;
                    double ddy = scale * (double)dX;
                    ddx += (ddx > 0) ? 0.5 : -0.5;
                    ddy += (ddy > 0) ? 0.5 : -0.5;
                     int dx = (int)ddx;
                     int dy = (int)ddy;

                 // Now we can compute the corner points...
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
             // line length
                             lineLength = Math.sqrt(dX * dX + dY * dY);

                             scale = (double)(thickness) / (2 * lineLength);

             // The x,y increments from an endpoint needed to create a rectangle...
                             ddx = -scale * (double)dY;
                              ddy = scale * (double)dX;
                             ddx += (ddx > 0) ? 0.5 : -0.5;
                             ddy += (ddy > 0) ? 0.5 : -0.5;
                             dx = (int)ddx;
                             dy = (int)ddy;
                         
                 // Now we can compute the corner points...
                  

                     xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
                     xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
                      xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
                         xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;

                     g.fillPolygon(xPoints, yPoints, 4);
                     }
                     
                     
                     
                         g.dispose();
        view.repaint();
          
      }
      
      
      
      public void drawDashedOval( int x, int y, int r) {
      
         Graphics g = surface.getGraphics();
        Graphics2D g2d = (Graphics2D) g.create();

        
        float fl[] = { 12.0f };
        BasicStroke basicStroke = new BasicStroke(2.0f,
         BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 12.0f,
             fl, 0.0f);

             g2d.setPaint(Color.RED);
             g2d.setStroke(basicStroke);
             g2d.draw(new Ellipse2D.Double(x, y, r, r));

        
        //set the stroke of the copy, not the original 
       // Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
     


        //gets rid of the copy
        g.dispose();
         view.repaint();
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
         g.setColor(Color.WHITE);
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
     public void resetCanvas(){
            Graphics g = surface.getGraphics();
         Sensor s;
         g.setColor(Color.WHITE);
        g.fillRect(0,0,800,700);
         drawSensor();
        
       
        
     }
     
    

    
     public void drawSensor(){
          Graphics g = surface.getGraphics();
         Sensor s;
       
            for(int i=0;i<sensorList.size(); i++){
                s=sensorList.get(i);
                if(s.getTypeOfSensor().equalsIgnoreCase("fs")){
                     g.setColor(Color.BLACK);
                      s.setRatio(s.radious-2);
                    
                    drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.radious);
                   // drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.comR);
                    drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.radious);
                       g.setColor(Color.RED);
                     drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.comR);
                     s=sensorList.get(i);
                   
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 7);
                }else if(s.getState()){
                g.setColor(Color.BLACK);
                drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.radious);
                 g.setColor(Color.GREEN);
                drawCenteredCircle ( (Graphics2D)g,s.getPositionX(),s.getPositionY(), s.comR);
                g.setColor(Color.BLACK);
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
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
     
     public Sensor distanceFireSensor(){
        Sensor s=null;
        double d1X = fire.getPositionX();
        double d1Y = fire.getPositonY();
        Sensor otherSensor;
        for(int i=0;i<sensorList.size();i++){
          otherSensor=sensorList.get(i);
        double d2X = otherSensor.x;
        double d2Y = otherSensor.y;
        int fireRange=fire.getRange();
       int fireDetected=(int)((fireRange/2)+(otherSensor.radious/2));
       
        
        double distance = Math.sqrt(Math.pow(d1X-d2X,2) + Math.pow(d1Y-d2Y,2));
         /* System.out.println("DistanceF:"+ fire.getRange()/2);
              System.out.println("DistanceS:"+ otherSensor.radious/2);
        System.out.println("Distance:"+distance+"  FireD:"+fireDetected);
        */
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
       // System.out.println("d1=("+ d1X + ","+ d1Y + " and d2=(" + d2X + "," + d2Y + ")");
        //System.out.println("Distance is :" + df.format(distance));
        if(fireDetected>(distance+10)){
            s=otherSensor;
        }
        }
        return s;
        
   
        
    }
      
      
     public void setFire(){
          
          Graphics g = surface.getGraphics();
         Color c=new Color(1f,0f,0f,.4f );
         g.setColor(c);
         fillCenteredCircle((Graphics2D) g,fire.getPositionX(),fire.getPositonY(),fire.getRange());
         if(fireStop ==false)
         {
        	 System.out.println("fuck");
         fire.setRange((fire.getRange()+20));
     }
         else 
         {
        	 System.out.println("fuckfuck");
        	fire.setRange((fire.getRange()));
     }
            g.dispose();
            view.repaint();
     }
        public static void randomFail(int howMany){
          
            Graphics g = surface.getGraphics();
             g.setColor(Color.RED);
        Random random= new Random();
        for(int i=0;i<howMany;i++){
        int n=random.nextInt(sensorList.size());
             if( sensorList.get(n).getTypeOfSensor().equals("fs")||(n==0)){
            i--;
            }else{
                    
                    
                    
        sensorList.get(n).setState(false);
        fillCenteredCircle((Graphics2D) g,sensorList.get(n).getPositionX(),sensorList.get(n).getPositionY(),8);
        }
        }
        g.dispose();
         view.repaint();
    }

   

 
}

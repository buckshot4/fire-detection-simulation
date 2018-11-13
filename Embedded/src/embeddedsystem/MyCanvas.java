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
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
/**
 *
 * @author eleonora
 */
public class MyCanvas {
    JLabel view;
    BufferedImage surface;
    static  ArrayList<Sensor> sensorList =new ArrayList();
    private Fire fire;
    public MyCanvas(){
        
        
        
        
       
       
            fire= new Fire(50,50,20);
        surface = new BufferedImage(700,600,BufferedImage.TYPE_INT_RGB);
        
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
       g.setColor(Color.GREEN);
        g.fillRect(0,0,700,600);
       
       
        g.dispose();
    }
     public void addNewElement(int numberClusters) {
   
        Graphics g = surface.getGraphics();
       //cluster(numberClusters,g);
        g.dispose();
        view.repaint();
    }
 /*    public ArrayList<Sensor> getSensorList(){
         return sensorList;
     }*/
     public void changeElement(){
           Graphics g = surface.getGraphics();
            Sensor s=sensorList.get(7);
            g.setColor(Color.red);
           fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
           
            g.dispose();
            view.repaint();
     }
     public void test(ArrayList<Sensor> n) throws InterruptedException{
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
     
     public void fillSensorList(){
         //(sensorlist, amountof sensors, width, height, radius)
         Embedded.createSensorGrid(sensorList,50,400,400,60);
         //700 and 600 are the current value of the width and height of the forest
 
    }
     

    
     public void drawSensor(){
          Graphics g = surface.getGraphics();
         Sensor s;
       
            for(int i=0;i<sensorList.size()-1; i++){
                s=sensorList.get(i);
                g.setColor(Color.BLACK);
               drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.radious);
               System.out.println(s.radious);
                g.setColor(Color.BLACK);
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
            }
             s=sensorList.get(sensorList.size()-1);
                g.setColor(Color.BLUE);
               drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.radious);
                g.setColor(Color.BLACK);
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 7);
           
     }
        public void fillCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x-(r/2);
         y = y-(r/2);
         g.fillOval(x,y,r,r);
    }
     public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x-(r/2);
         y = y-(r/2);
         g.drawOval(x,y,r,r);
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
 
     
     
   /*  
      public void cluster(int numberCluster, Graphics g){
       int X;
       int Y;
       
       Random rand = new Random();


       int d=5;
       int distance=60; 
       
       int distanceY;
       
       int[] xValues={35,50,20,20,50};
       
       int[] yValues = {35,20,20,50,50};
        
    
         
         for(int j=0;j<numberCluster;j++){
             
            g.setColor(Color.RED);
             //distance=rand.nextInt(300) + 1;
             distanceY=7*rand.nextInt(15) + 1;
            for(int i=0;i<xValues.length;i++){
                
                X=xValues[i]+(distance*(j+1));
                Y=yValues[i]+(distanceY*j);
                if((X>700)||(Y>500)){
                }else{
              g.fillOval (X, Y, d, d);
                }
                g.setColor(Color.BLACK);
            }
            
            
         }
      }*/
}

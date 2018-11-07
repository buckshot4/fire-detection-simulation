/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
/**
 *
 * @author eleonora
 */
public class MyCanvas {
     JLabel view;
    BufferedImage surface;
    private  ArrayList<Sensor> sensorList;
    
    public MyCanvas(){
        
       
         sensorList = new ArrayList();
         
        surface = new BufferedImage(600,400,BufferedImage.TYPE_INT_RGB);
        
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        g.setColor(Color.ORANGE);
        g.fillRect(0,0,800,700);
        g.setColor(Color.BLACK);
        
        g.dispose();
    }
     public void addNewElement(int numberClusters) {
   
        Graphics g = surface.getGraphics();
       //cluster(numberClusters,g);
        g.dispose();
        view.repaint();
    }
     public void changeElement(){
           Graphics g = surface.getGraphics();
            Sensor s=sensorList.get(0);
            g.setColor(Color.red);
           fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
           
            g.dispose();
            view.repaint();
     }
     
     public void fillSensorList(){
         String type="t";
         int  ratio=40;
         int[] arrayPositionX = {50,50,250,250,220, 80,30,10,60,80,20,20,90,115,150};
         int[] arrayPositionY ={150,50,50,150,100,100,160,180,130,110,120,190,140,140,100};
         
         for(int i=0;i<arrayPositionX.length;i++){
         sensorList.add(new Sensor(arrayPositionX[i],arrayPositionY[i],type,ratio));
         }
         
         
         
        
    }
    
     public void drawSensor(){
          Graphics g = surface.getGraphics();
         Sensor s;
            
            for(int i=0;i<sensorList.size(); i++){
                s=sensorList.get(i);
                g.setColor(Color.RED);
               drawCenteredCircle ((Graphics2D) g,s.getPositionX(),s.getPositionY(), s.ratio);
                g.setColor(Color.BLACK);
                fillCenteredCircle((Graphics2D) g,s.getPositionX(),s.getPositionY(), 5);
            }
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

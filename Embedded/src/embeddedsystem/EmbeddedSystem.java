/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import static embeddedsystem.GridSP.initDistances;
import static embeddedsystem.GridSP.printAllDistances;
import static embeddedsystem.MyCanvas.sensorList;
import static embeddedsystem.ShortestPathList.s_list;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.nio.file.Files.size;
import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.beans.binding.Bindings.size;
import static javafx.beans.binding.Bindings.size;
import javax.swing.JButton;
import javax.swing.JLabel;
/**
 *
 * @author eleonora
 */
public class EmbeddedSystem {

 private static MYTimer timer;
 
 private MyCanvas canvas;
 private JFrame window;
    
 
 public EmbeddedSystem(){
       canvas = new MyCanvas();
         window= new JFrame();
        
        
 }
    public static void main(String[] args) {
       
        EmbeddedSystem emb=new EmbeddedSystem();
           JLabel seconds= new JLabel();
            JLabel minutes= new JLabel();
      JLabel milli= new JLabel();
        timer=new MYTimer(emb,minutes,seconds,milli);
        
        emb.canvas.drawSensor();
     
        emb.window.setSize(1000,900);
        emb.window.setTitle("Embedded");
         emb.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         emb.window.setContentPane( emb.canvas.view);
      
         emb.window.setLocationByPlatform(true);
         emb.window.setVisible(true);
      
        
        JButton button = new JButton("weather");
       button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               
                 emb.canvas.weatherChange(20);
                 //emb.canvas.setFire();
                
            }
           
       });
       button.setBounds(900,10,200,30);
        emb.window.getContentPane().add(button);
         emb.window.getContentPane().add(seconds);
         emb.window.getContentPane().add(minutes);
          emb.window.getContentPane().add(milli);
         
          //Clock timer
         minutes.setBounds(900,40,200,30);
         seconds.setBounds(920,40,200,30);
          milli.setBounds(940, 40, 200, 30);
         timer.startTimer();
         
      
        
        System.out.print("SIZE"+sensorList.size());
           ShortestPathList spl= new ShortestPathList();
        spl.findNeighbors(sensorList);
        
  
        /*  initDistances(sensorList);
        printAllDistances(sensorList);
       
        ArrayList<Sensor> l= new ArrayList();
        s.findSP(s2,spl);
         s.printPathToFS();
       l=s_list;
       if(l.size()>1){
      emb.canvas.drawLine(s,l);
       }
       //emb.canvas.drawLine(l.get(0), l.get(1));
      */
         
        
        
         emb.fireSpread(emb.canvas);
        
    }
      public void update(long dT){

        
        
        timer.getMinutesLabel().setText(String.valueOf((dT / (1000*60)) % 60)+"  :");
        timer.getsecondLabel().setText(String.valueOf((dT / 1000) % 60 ) + " : ");
        timer.getMilliseconds().setText(String.valueOf((dT)%1000));
    }
          
    public void fireSpread(MyCanvas canvas){
        Timer t= new Timer();
            TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Sensor s= sensorList.get(sensorList.size()-1);
            Sensor fs= sensorList.get(48);
           canvas.setFire();
           
          if (canvas.distanceFireSensor(s)){
                canvas.BC(s, fs, s);
           }
        
           
        }
    };
              
       t.schedule(task, 2000,900);
        
    }
      
    
    public void timeSchedule(MyCanvas canvas){
        Timer t= new Timer();
            TimerTask task = new TimerTask() {
        @Override
        public void run() {
           
          //  Sensor s= canvas.getSensorList().get(5);
           // Sensor s2= canvas.getSensorList().get(9);
            //canvas.fireDetection(s);
            //canvas.drawLine(s, s2);
           
        }
    };
       t.schedule(task, 4200);
        
    }
    
}

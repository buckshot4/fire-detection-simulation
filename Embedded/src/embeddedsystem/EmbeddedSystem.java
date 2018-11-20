/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import static embeddedsystem.GridSP.hopsCalculation;
import static embeddedsystem.GridSP.initDistances;
import static embeddedsystem.GridSP.newSP;
import static embeddedsystem.GridSP.printAllDistances;
import static embeddedsystem.MyCanvas.sensorList;
import static embeddedsystem.ShortestPathList.s_list;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.nio.file.Files.size;
import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 *
 * @author eleonora
 */
public class EmbeddedSystem implements Runnable {


 private Thread runThread;
  private Thread myThread;
 private static MyCanvas canvas;

 private boolean running = false;
 private long summedTime = 0;
  private Timer tFire;
  private   Timer timerNET;
 private JLabel seconds;
 private JLabel minutes;
 private JLabel milli;
  static JTextField startSensor ;
 public EmbeddedSystem(){
   seconds= new JLabel();
   minutes= new JLabel();
    milli= new JLabel();
  startSensor = new JTextField(20);
 tFire= new Timer();
    timerNET= new Timer();
 
 }
 
    public static void main(String[] args) {
            
            EmbeddedSystem emb=new EmbeddedSystem();
            JFrame window= new JFrame();
           
            JTextField failSensor = new JTextField(20);
           
            MyCanvas canvas = new MyCanvas();
          sensorList=MyCanvas.createSensorList(600,500,110);
            ShortestPathList spl= new ShortestPathList();
     
             window.setSize(1200,1000);
             window.setTitle("Embedded");
             window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             window.setContentPane( canvas.view);
             window.setLocationByPlatform(true);
             window.setVisible(true);
  
         
             
 //weather button
       JButton weather = new JButton("weather");
       weather.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
               
                 canvas.weatherChange(13);
                 //emb.canvas.setFire();
                
            }
           
       });
       weather.setBounds(900,10,200,30);
       window.getContentPane().add(weather);
          canvas.drawSensor();
        
       
        
        failSensor.setBounds(800,40,80,30);
        window.getContentPane().add(failSensor);
         JButton failSensorButton = new JButton("fail sensors");
        failSensorButton.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
               String failSensorStr = failSensor.getText();
                //resetAll();
                MyCanvas.randomFail(Integer.parseInt(failSensorStr));
                  
                // spl.checkNeighbors(sensorList);
              
             emb.net();
            }
           
       });
  
        failSensorButton.setBounds(900,40,200,30);
        window.getContentPane().add(failSensorButton);
        
        
       JButton setFire = new JButton("Random Fire");
       setFire.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                emb.fireSpread(canvas);
            }
           
       });
          startSensor.setBounds(800, 70, 80, 30);
        window.getContentPane().add(startSensor);
       setFire.setBounds(900,70,200,30);
       
       window.getContentPane().add(setFire);
        
         JButton restart = new JButton("restart");
        restart.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
             
                emb.tFire.cancel();
                emb.timerNET.cancel();
                sensorList.clear();
                sensorList=MyCanvas.createSensorList(600,500,110);
               canvas.resetCanvas();
              
               resetAll();
               ShortestPathList.n_list.clear();
                ShortestPathList.s_list.clear();
                
                         
                    spl.findNeighbors(sensorList);
                  
                    initDistances(sensorList);
               failSensor.setText("");
               startSensor.setText("");
                emb.tFire= new Timer();
             emb.timerNET= new Timer();
                
              /* if(disconneted.size()>0){
               for(Sensor s: disconneted){
                   System.out.println("DISC"+s.getTypeOfSensor());
               }
               }
               disconneted.clear();*/
            }
           
       });
  
        restart.setBounds(1000,120,200,30);
        window.getContentPane().add(restart);
        
        
        
        //timer
        window.getContentPane().add(emb.seconds);
        window.getContentPane().add(emb.minutes);
        window.getContentPane().add(emb.milli);
     
       
          
          //Clock timer
         emb.minutes.setBounds(900,90,200,30);
         emb.seconds.setBounds(920,90,200,30);
         emb.milli.setBounds(940, 90, 200, 30);
       
         emb.startTimer();
         
         
       
  
  
        //end grphics stuff
                   
                    spl.findNeighbors(sensorList);
                  
                    initDistances(sensorList);
                    printAllDistances(sensorList);
     
       
      // fireSpreadRouting(canvas,spl);
       //emb.fireSpread(canvas);
        
   

 
    }

      
      
      
      
      
      
public  void net( ){
      
        TimerTask task = new TimerTask() {
               
        @Override
        public void run() {
                   if(MyCanvas.checkFireStation()){ 
              try {
                  canvas.BCM2(sensorList.get(0),sensorList.get(sensorList.size()-1));
                   
              } catch (InterruptedException ex) {
                  Logger.getLogger(EmbeddedSystem.class.getName()).log(Level.SEVERE, null, ex);
              }
    
        }
        }
  
    };
              
       timerNET.schedule(task, 0);
        
    }   
               
     public static void fireSpreadRouting(MyCanvas canvas,ShortestPathList spl){
        Timer t= new Timer();
            TimerTask task = new TimerTask() {
                boolean message=true;
        @Override
        public void run() {
            
            if(message){
          Sensor s=null; //sensorList.get(sensorList.size()-1);
           
           canvas.setFire();
           
           s=canvas.distanceFireSensor();
          if (s!=null){
              newSP(s, spl);
           //   ShortestPathList.printSP();
              canvas.drawThickLine(s, s_list);
             // canvas.drawLine(s, spl.s_list);
              message=false;
           }
        }
        }
    };
              
       t.schedule(task, 2000,900);
        
    }
      
    
             
          
     public  void fireSpread(MyCanvas canvas){
     
        TimerTask task = new TimerTask() {
        @Override
        public void run() {
            boolean message=true;
            if(message){
          Sensor s=null; //sensorList.get(sensorList.size()-1);
            Sensor fs= sensorList.get(sensorList.size()-1);
           canvas.setFire();
           
           s=canvas.distanceFireSensor();
          if ((s!=null)){
              try {
                  
                  message=false;
                  canvas.BCM(s, fs);
              } catch (InterruptedException ex) {
                  Logger.getLogger(EmbeddedSystem.class.getName()).log(Level.SEVERE, null, ex);
              }
           }
        }}
    };
              
       tFire.schedule(task, 0,900);
        
    }
  public void update(long dT){

        
        
       minutes.setText(String.valueOf((dT /(1000*60)) % 60)+"  :");
        seconds.setText(String.valueOf((dT / 1000) % 60 ) + " : ");
        milli.setText(String.valueOf((dT)%1000));
    }
      
        public static void setJlabel(String str){
                startSensor.setText(str);
      
    }

    public static void resetAll(){
        for(Sensor s:sensorList){
            s.setForwardMsg(false);
           s.setState(true);
        }
    }     

  
  @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        // keep showing the difference in time until we are either paused or not running anymore
        while(running) {
          this.update(summedTime + (System.currentTimeMillis() - startTime));
        }
        // if we just want to pause the timer dont throw away the change in time, instead store it
       
    }
    public void startThread(){
         myThread = new Thread(this);
        myThread.start();
    }
  public void startTimer() {
        running = true;

        runThread = new Thread(this);
        runThread.start();
    }
 
    
}

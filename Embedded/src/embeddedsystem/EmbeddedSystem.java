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
import javax.swing.JPanel;
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
  private    ShortestPathList spl;
  static  JFrame window;
 public EmbeddedSystem(){
   seconds= new JLabel();
   minutes= new JLabel();
    milli= new JLabel();
  startSensor = new JTextField(5);
 tFire= new Timer();
    timerNET= new Timer();
    spl  = new ShortestPathList();
    window= new JFrame();
 
 }
 
    public static void main(String[] args) {
            
            EmbeddedSystem emb=new EmbeddedSystem();
            JFrame simulation= new JFrame();
          
           JPanel panel =new JPanel();
           JLabel l= new JLabel();
            JTextField failSensorText = new JTextField(10);
           
                JTextField ComR = new JTextField(20);
            JTextField SenR = new JTextField(20);
            JTextField Sensors = new JTextField(20);
            JTextField Mode = new JTextField(20);
            JTextField Height = new JTextField(20);
            JTextField Width = new JTextField(20);
           
            
            
            MyCanvas canvas = new MyCanvas();
           // sensorList=MyCanvas.createSensorList(700, 600, 110);
            sensorList=MyCanvas.createSensorList2(MyCanvas.width,MyCanvas.height,MyCanvas.SensingRange, MyCanvas.CommunicationRange, MyCanvas.SensorAmount);
       
             emb.window.setSize(400,600);
             emb.window.setTitle("Control");
             emb.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //  window.setContentPane(panel);
             //window.setLocationByPlatform(true);
             emb.window.setVisible(true);
          
          
          
             simulation.setSize(800,700);
             simulation.setTitle("Embedded");
             simulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             simulation.setContentPane( canvas.view);
             simulation.setLocationByPlatform(true);
             simulation.setVisible(true);
             
  
             
             
             
                 //GUI FOR HEIGHT 
          JButton StopFireButton = new JButton("Start/Stop Fire");     
          
          StopFireButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("THIS IS IT");
				System.out.println(MyCanvas.fireStop);
			    if(MyCanvas.fireStop == false)
				MyCanvas.fireStop  = true;	
			    else 
			    	MyCanvas.fireStop  = false;	

			   System.out.println(MyCanvas.fireStop); 
			}
          });
       
          StopFireButton.setBounds(50,440,200,30);
          window.getContentPane().add(StopFireButton);
          
          
        //GUI FOR WIDTH
          Width.setBounds(50,310,80,30);
          window.getContentPane().add(Width);
          JButton WidthButton = new JButton("Width");
          
          Width.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				System.out.println("PRESSED");
    				String widthStr = Width.getText();
    				MyCanvas.width  = Integer.parseInt(widthStr);	
    				EmbeddedSystem.Change();
    				canvas.resetCanvas();		 
    			}
          });
          
          WidthButton.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("PRESSED");
  				String widthStr = Width.getText();
  				MyCanvas.width  = Integer.parseInt(widthStr);	
  				EmbeddedSystem.Change();
  				canvas.resetCanvas();		 
  			}
        	  
          });
       
          WidthButton.setBounds(150,310,200,30);
          window.getContentPane().add(WidthButton);
        
          
          //GUI FOR HEIGHT 
          Height.setBounds(50,340,80,30);
          window.getContentPane().add(Height);
          JButton HeightButton = new JButton("Height");
          
          Height.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("PRESSED");
  				String heightStr = Height.getText();
  				MyCanvas.height  = Integer.parseInt(heightStr);	
  				EmbeddedSystem.Change();
  				canvas.resetCanvas();		 
  			}
          	  
            });
          
          HeightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PRESSED");
				String heightStr = Height.getText();
				MyCanvas.height  = Integer.parseInt(heightStr);	
				EmbeddedSystem.Change();
				canvas.resetCanvas();		 
			}
        	  
          });
       
          HeightButton.setBounds(150,340,200,30);
          window.getContentPane().add(HeightButton);
        
          
          
          //GUI FOR MODE (0 = GRID, 1 = COMPLETELY RANDOM, 2 = SEMI RANDOM (ALWAYS IN RANGE OF EACHOTHER), 3 = SEMI RANDOM GRID)
          Mode.setBounds(50,370,80,30);
          window.getContentPane().add(Mode);
          JButton ModeButton = new JButton("Mode");
          
          Mode.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("PRESSED");
  				String ModeStr = Mode.getText();
  				MyCanvas.mode  = Integer.parseInt(ModeStr);	
  				EmbeddedSystem.Change();
  				canvas.resetCanvas();		 
  			}
          	  
            });
          
          ModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PRESSED");
				String ModeStr = Mode.getText();
				MyCanvas.mode  = Integer.parseInt(ModeStr);	
				EmbeddedSystem.Change();
				canvas.resetCanvas();		 
			}
        	  
          });
       
          ModeButton.setBounds(150,370,200,30);
          window.getContentPane().add(ModeButton);
        
          //GUI FOR SensorAmount
          Sensors.setBounds(400,370,80,30);
          window.getContentPane().add(Sensors);
          JButton SensorsButton = new JButton("Sensors");
          
          Sensors.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("PRESSED");
  				if(MyCanvas.mode == 1 || MyCanvas.mode == 2) {
  				String SensorsStr = Sensors.getText();
  				MyCanvas.SensorAmount  = Integer.parseInt(SensorsStr);
  				EmbeddedSystem.Change();
  				canvas.resetCanvas();		
  				}
  			}
          	  
            });
          
          SensorsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PRESSED");
				if(MyCanvas.mode == 1 || MyCanvas.mode == 2) {
				String SensorsStr = Sensors.getText();
				MyCanvas.SensorAmount  = Integer.parseInt(SensorsStr);
				EmbeddedSystem.Change();
				canvas.resetCanvas();		
				}
			}
        	  
          });
       
          SensorsButton.setBounds(500,370,200,30);
          window.getContentPane().add(SensorsButton);
          
        
          //GUI FOR SENSINGRANGE
          SenR.setBounds(400,310,80,30);
          window.getContentPane().add(SenR);
          JButton SenRButton = new JButton("Sensing Range");
          
          SenR.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("PRESSED");
  				String SenRStr = SenR.getText();
  				MyCanvas.SensingRange  = Integer.parseInt(SenRStr);
  				EmbeddedSystem.Change();
  				canvas.resetCanvas();		 
  		 
  			}
          	  
            });
          
          SenRButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PRESSED");
				String SenRStr = SenR.getText();
				MyCanvas.SensingRange  = Integer.parseInt(SenRStr);
				EmbeddedSystem.Change();
				canvas.resetCanvas();		 
		 
			}
        	  
          });
          
          SenRButton.setBounds(500,310,200,30);
          window.getContentPane().add(SenRButton);
          
          
          //GUI FOR COMMUNICATION RANGE
          ComR.setBounds(400,340,80,30);
          window.getContentPane().add(ComR);
          JButton ComRButton = new JButton("Communication Range");
          
          ComR.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				String ComRStr = ComR.getText();
  				MyCanvas.CommunicationRange = Integer.parseInt(ComRStr);
  				EmbeddedSystem.Change();
                 canvas.resetCanvas();
  			}
          	  
            });
          
          ComRButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ComRStr = ComR.getText();
				MyCanvas.CommunicationRange = Integer.parseInt(ComRStr);
				EmbeddedSystem.Change();
               canvas.resetCanvas();
			}
        	  
          });
          
          ComRButton.setBounds(500,340,200,30);
          window.getContentPane().add(ComRButton);
        /////////////////////////////////////////////////////////////      
             
 //weather button
       JButton weather = new JButton("weather");
       weather.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
               
                 canvas.weatherChange(13);
                 //emb.canvas.setFire();
                
            }
           
       });
       weather.setBounds(40,100,200,30);
      window.getContentPane().add(weather);
           /////////////////////////////////////////////////////////////
       canvas.drawSensor();
        
          /////////////////////////////////////////////////////////////  
        
        failSensorText.setBounds(250,40,80,30);
        emb.window.getContentPane().add(failSensorText);
         JButton failSensorButton = new JButton("fail sensors");
        failSensorButton.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                
               String failSensorStr = failSensorText.getText();
          
                MyCanvas.randomFail(Integer.parseInt(failSensorStr));
             
             
            }
           
       });
  
        failSensorButton.setBounds(40,40,200,30);
        emb.window.getContentPane().add(failSensorButton);
        
            /////////////////////////////////////////////////////////////   
         JButton checkNet = new JButton("checkNet");
        checkNet.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                CheckSubNetworks.remainedSensors=sensorList;
                  
               emb.net();
               
             
            }
           
       });
 
        checkNet.setBounds(40,80,200,30);
        window.getContentPane().add(checkNet);
        
         /////////////////////////////////////////////////////////////          PRINT SUBNET
         
          JButton displaySubNet = new JButton("print sub network");     
        displaySubNet.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                CheckSubNetworks.printSubNets();
             
            }
           
       });
  
        displaySubNet.setBounds(240,80,200,30);
       window.getContentPane().add(displaySubNet);
       
       
            /////////////////////////////////////////////////////////////       RANDOM FIRE
       
       JButton setFire = new JButton("Random Fire");
       setFire.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                emb.fireSpread(canvas);
            }
           
       });
          startSensor.setBounds(200, 100, 80, 30);
        emb.window.getContentPane().add(startSensor);
        
        
       setFire.setBounds(40,400,200,30);
       
       emb.window.getContentPane().add(setFire);
        
            /////////////////////////////////////////////////////////////
       
         JButton restart = new JButton("restart");
        restart.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
             
                emb.tFire.cancel();
                emb.timerNET.cancel();
                sensorList.clear();
                sensorList=MyCanvas.createSensorList(600,500,110);
                canvas.resetCanvas();
                emb.resetAll();
                ShortestPathList.n_list.clear();
                ShortestPathList.s_list.clear();
                
                emb.spl.findNeighbors(sensorList);
                initDistances(sensorList);
               
                
                startSensor.setText("");
                 emb.tFire= new Timer();
                 emb.timerNET= new Timer();
                 failSensorText.setText("");
                
                 
                 CheckSubNetworks.resetCheckNet();
              /* if(disconneted.size()>0){
               for(Sensor s: disconneted){
                   System.out.println("DISC"+s.getTypeOfSensor());
               }
               }
               disconneted.clear();*/
            }
           
       });
  
        restart.setBounds(40,200,200,30);
        window.add(restart);
        
        
          /////////////////////////////////////////////////////////////
         
         
        JButton shoethestPath = new JButton("shoethestPath");
       shoethestPath.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                            
       for(Sensor s : sensorList){
            if(s.typeOfSensor.equals("fs")){
                s.hops = 0;
                System.out.println(s.typeOfSensor + " is " + s.hops + " away from fs");
            }
        }
        
        for(int i = 0; i < 99 ; i++){
            for(Sensor s : sensorList){
                hopsCalculation(s);
            }
        }
        
        
        Sensor s0 = null;
        for(Sensor s : sensorList){
            if(s.typeOfSensor.equals("s0")){
                s0 = s;
            }
        }
                fireSpreadRouting(canvas,emb.spl); 
            }
           
       });
       shoethestPath.setBounds(40,120,200,30);
       emb.window.getContentPane().add(shoethestPath);
    
  
       /////////////////////////////////////////////////////////////
        //end grphics stuff
                   
                  emb.spl.findNeighbors(sensorList);    
                  initDistances(sensorList);
     //  fireSpreadRouting(canvas,spl);
       //emb.fireSpread(canvas);
        
   
        
        //timer
        window.getContentPane().add(emb.seconds);
        window.getContentPane().add(emb.minutes);
        window.getContentPane().add(emb.milli);
     
       window.getContentPane().add(l);
          
          //Clock timer
         emb.minutes.setBounds(60,200,200,30);
         emb.seconds.setBounds(80,200,200,30);
        emb.milli.setBounds(100 ,200,200, 30);
        
        l.setBounds(120,200,200,30);
         emb.startTimer();
 
    }

      
      
      
      
public static JFrame getFrame(){
    return window;
}
      
public  void net( ){
      
        TimerTask task = new TimerTask() {
               
        @Override
        public void run() {
                   if(MyCanvas.checkFireStation()){ 
       
                       try {
                           System.out.println("checking net");
                           CheckSubNetworks.checkSubNetworks(true);
                       
                       } catch (InterruptedException ex) {
                           Logger.getLogger(EmbeddedSystem.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   
            
    
        }else{
                           JOptionPane.showMessageDialog(window,
                      "FireStation disconetted");
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
         /* if ((s!=null)){
              try {
                  
                  message=false;
                  canvas.BCM(s, fs);
              } catch (InterruptedException ex) {
                  Logger.getLogger(EmbeddedSystem.class.getName()).log(Level.SEVERE, null, ex);
              }
           }
        }*/
}}
    };
              
       tFire.schedule(task, 0,900);
        
    }
     
       public static void Change() {
    	
    	EmbeddedSystem emb=new EmbeddedSystem();
        ShortestPathList spl= new ShortestPathList();
        emb.tFire.cancel();
        emb.timerNET.cancel();
        sensorList.clear();
        sensorList=MyCanvas.createSensorList2(MyCanvas.height,MyCanvas.width,MyCanvas.SensingRange,MyCanvas.CommunicationRange,MyCanvas.SensorAmount);
       emb.resetAll();
       ShortestPathList.n_list.clear();
        ShortestPathList.s_list.clear();
                    
            spl.findNeighbors(sensorList);
          
            initDistances(sensorList);
       //failSensor.setText("");
       startSensor.setText("");
        emb.tFire= new Timer();
     emb.timerNET= new Timer();
     
    }
     
  public void update(long dT){

        
        
       minutes.setText(String.valueOf((dT /(1000*60)) % 60)+"  :");
        seconds.setText(String.valueOf((dT / 1000) % 60 ) + " : ");
        milli.setText(String.valueOf((dT)%1000));
    }
      
        public static void setJlabel(String str){
                startSensor.setText(str);
      
    }

    public  void resetAll(){
       
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
 /*   public void startThread(){
         myThread = new Thread(this);
        myThread.start();
    }
*/
  public void startTimer() {
        running = true;

        runThread = new Thread(this);
        runThread.start();
    }
 
    
}

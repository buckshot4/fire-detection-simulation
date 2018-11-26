
 /* To change this license header, choose License Headers in Project Properties.
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
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import static java.nio.file.Files.size;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
 
 private static MyCanvas canvas;

 private boolean running = false;
 private long summedTime = 0;
 private Timer tFire;
 private Timer timerNET;
 private JLabel seconds;
 private JLabel minutes;
 private JLabel milli;
 static JTextField startSensor ;
 private ShortestPathList spl;
 static  JFrame window;
 static int ModeInt=0;
 //private   List<MyRunnable> listThread;
 static Sensor fs;

 public EmbeddedSystem(){
 seconds= new JLabel();
 minutes= new JLabel();
 milli= new JLabel();
 startSensor = new JTextField(5);
 tFire= new Timer();
 timerNET= new Timer();
 spl  = new ShortestPathList();
 window= new JFrame();
 //listThread = new LinkedList<MyRunnable>();
 
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
            JTextField cS= new JTextField(20);
            JTextField drawSub= new JTextField(20);
            OptionClass op= new OptionClass();
          
           /*
            0 = GRID,
            1 = COMPLETELY RANDOM 
            2 = SEMI RANDOM (ALWAYS IN RANGE OF EACHOTHER) 
            3 = SEMI RANDOM GRID
            */
            String[] modes = new String[] {"Grid", "Completley Random", "Semi-Random", "Semi-Random Grid"};
 
            JComboBox<String> comboModes = new JComboBox<>(modes);
            
            MyCanvas canvas = new MyCanvas();
           // sensorList=MyCanvas.createSensorList(700, 600, 110);

            sensorList=MyCanvas.createSensorList2(MyCanvas.width,MyCanvas.height,MyCanvas.SensingRange,MyCanvas.CommunicationRange,MyCanvas.SensorAmount);
           
            System.out.println("SensorList size"+sensorList.size());
          
             window.setSize(800,600);
             window.setTitle("Control");
             window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
         
        
             window.setContentPane(op.viewWindow);
             window.setLocationByPlatform(true);
             window.setVisible(true);
          
          
          
             simulation.setSize(800,700);
             simulation.setTitle("Embedded");
             simulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             simulation.setContentPane( canvas.view);
             simulation.setLocationByPlatform(true);
             simulation.setVisible(true);
             
  
             
             
             
                 //GUI FOR STOP/START FIRE 
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
       
          StopFireButton.setBounds(500,210,200,30);
          window.getContentPane().add(StopFireButton);
          
          cS.setBounds(50,510,80,30);
          window.getContentPane().add(cS);
          JButton colorSensorB = new JButton("drawNeighbour");
          
          colorSensorB.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				System.out.println("PRESSED");
    				String colorSensorStr = cS.getText();
                                
                                for(Sensor s: sensorList){
                                    if(s.getTypeOfSensor().equalsIgnoreCase(colorSensorStr)){
                                       // canvas.changeElement(s);
                                     
                                     GridSP.printN(s);
                                     canvas.drawSubNetwork(s.getNeighbourSeonsor());
                                    }
                                }
    					 
    			}
          });
          
          colorSensorB.setBounds(150,510,80,30);
           window.getContentPane().add(colorSensorB);
           
           
          drawSub.setBounds(50,570,80,30);
          window.getContentPane().add(drawSub);
          JButton drawSubB = new JButton("draw subNet");
          
          drawSubB.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
                            int index = Integer.parseInt(drawSub.getText());
                            if(index<CheckSubNetworks.subNetList.size()){
                                
                            //    canvas.drawSubNetwork(MyCanvas.failedS);
                            canvas.drawSubNetwork(CheckSubNetworks.subNetList.get(index).getSubNet());
                            }
                        }
          });
          
          drawSubB.setBounds(150,570,80,30);
           window.getContentPane().add(drawSubB);
           
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
    				emb.Change();
    				canvas.resetCanvas();		 
    			}
          });
          
          WidthButton.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("PRESSED");
  				String widthStr = Width.getText();
  				MyCanvas.width  = Integer.parseInt(widthStr);	
  				emb.Change();
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
  				emb.Change();
  				canvas.resetCanvas();		 
  			}
          	  
            });
          
          HeightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PRESSED");
				String heightStr = Height.getText();
				MyCanvas.height  = Integer.parseInt(heightStr);	
				emb.Change();
				canvas.resetCanvas();		 
			}
        	  
          });
       
          HeightButton.setBounds(150,340,200,30);
          window.getContentPane().add(HeightButton);
        
          
          
          //GUI FOR MODE (0 = GRID, 1 = COMPLETELY RANDOM, 2 = SEMI RANDOM (ALWAYS IN RANGE OF EACHOTHER), 3 = SEMI RANDOM GRID)
          comboModes.setBounds(20,370,120,30);
          window.getContentPane().add(comboModes);
          comboModes.setSelectedIndex(0);
        comboModes.addActionListener(new ActionListener() {
 
         @Override
         public void actionPerformed(ActionEvent event) {
            ModeInt=comboModes.getSelectedIndex();
            
            
             }});
        //  Mode.setBounds(50,370,80,30);
          //window.getContentPane().add(Mode);
          JButton ModeButton = new JButton("Mode");
          
          Mode.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
                          
  				System.out.println("PRESSED");
  				String ModeStr = Mode.getText();
  				MyCanvas.mode  = Integer.parseInt(ModeStr);	
                                emb.Change();
                                
  				canvas.resetCanvas();	
                                 CheckSubNetworks.resetCheckNet();
                        }                                
          	  
            });
          
         ModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PRESSED");
				String ModeStr = Mode.getText();
				MyCanvas.mode  =ModeInt;// Integer.parseInt(ModeStr);	
                                emb.Change();
				canvas.resetCanvas();
                                   CheckSubNetworks.resetCheckNet();
                                  for(int i=0;i< sensorList.size();i++){
                                    System.out.println (i+" "+sensorList.get(i).getTypeOfSensor());
                                }
  			
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
                                emb.Change();
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
				emb.Change();
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
  				emb.Change();
  				canvas.resetCanvas();		 
  		 
  			}
          	  
            });
          
          SenRButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PRESSED");
				String SenRStr = SenR.getText();
				MyCanvas.SensingRange  = Integer.parseInt(SenRStr);
				emb.Change();
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
  				emb.Change();
                                canvas.resetCanvas();
  			}
          	  
            });
          
          ComRButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ComRStr = ComR.getText();
				MyCanvas.CommunicationRange = Integer.parseInt(ComRStr);
				emb.Change();
               canvas.resetCanvas();
			}
        	  
          });
          
          ComRButton.setBounds(500,340,200,30);
          window.getContentPane().add(ComRButton);
        /////////////////////////////////////////////////////////////      
             
 //weather button
       JButton weather = new JButton("Weather");
       weather.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
               
                 canvas.weatherChange(13);
                 
                 //emb.canvas.setFire();
                
            }
           
       });
       weather.setBounds(150,210,200,30);
      window.getContentPane().add(weather);
           /////////////////////////////////////////////////////////////
       canvas.drawSensor();
        
          /////////////////////////////////////////////////////////////  
        
        failSensorText.setBounds(50,240,80,30);
        emb.window.getContentPane().add(failSensorText);
         JButton failSensorButton = new JButton("Fail Sensors");
         
         failSensorText.addActionListener(new ActionListener(){
             @Override
               public void actionPerformed(ActionEvent e) {
                   
                  String failSensorStr = failSensorText.getText();
             
                   MyCanvas.randomFail(Integer.parseInt(failSensorStr));
                
                
               }
              
          });
         
        failSensorButton.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                
               String failSensorStr = failSensorText.getText();
          
                MyCanvas.randomFail(Integer.parseInt(failSensorStr));
             
             
            }
           
       });
  
        failSensorButton.setBounds(150,240,200,30);
        emb.window.getContentPane().add(failSensorButton);
        
        
            /////////////////////////////////////////////////////////////   
         JButton checkNet = new JButton("CheckNet");
        checkNet.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
           
               emb.net();
            }
           
       });
 
        checkNet.setBounds(40,80,200,30);
        window.getContentPane().add(checkNet);
        
         /////////////////////////////////////////////////////////////          PRINT SUBNET
         
          JButton displaySubNet = new JButton("Print Sub Network");     
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
          startSensor.setBounds(400, 240, 80, 30);
        emb.window.getContentPane().add(startSensor);
        
        
       setFire.setBounds(500,240,200,30);
       
       emb.window.getContentPane().add(setFire);
        
            /////////////////////////////////////////////////////////////
       
         JButton restart = new JButton("Restart");
        restart.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
             
              emb.Change();
              canvas.resetCanvas();   
              
              CheckSubNetworks.resetCheckNet();
              
              /* if(disconneted.size()>0){
               for(Sensor s: disconneted){
                   System.out.println("DISC"+s.getTypeOfSensor());
               }
               }
               disconneted.clear();*/
            }
           
       });
  
        restart.setBounds(240,120,200,30);
        window.add(restart);
        
        
          /////////////////////////////////////////////////////////////
         
         
        JButton shortestPath = new JButton("Shortest Path");
       shortestPath.addActionListener(new ActionListener(){
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
       shortestPath.setBounds(440,80,200,30);
       emb.window.getContentPane().add(shortestPath);
    
  
       /////////////////////////////////////////////////////////////
        //end grphics stuff
                   
                  emb.spl.findNeighbors(sensorList);    
                  initDistances(sensorList);
              /*    for(Sensor s: sensorList){
                      System.out.println("SensorN  "+s.getTypeOfSensor());
                      GridSP.printN(s);
                      System.out.println();
                  }*/
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
        // emb.startTimer();
 
    }

      
      
      
      
public static JFrame getFrame(){
    return window;
}
      
public void net( ){
      
        TimerTask task = new TimerTask() {
               
        @Override
        public void run() {
                  // if(MyCanvas.checkFireStation()){ 
       
                       try {
                           System.out.println("checking net");
                       
                       CheckSubNetworks.checkSubNetworks(true);
                       } catch (InterruptedException ex) {
                           Logger.getLogger(EmbeddedSystem.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   
            
    
      /*  }else{
                           JOptionPane.showMessageDialog(window,
                      "FireStation disconetted");
                   }*/
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
      
    
             
      public static List<MyRunnable> runnableSensors  (){
          List<MyRunnable> list= new LinkedList<MyRunnable>();
          fs=sensorList.get(sensorList.size()-1);
          for(Sensor s: CheckSubNetworks.remainedSensors){
              MyRunnable run= new MyRunnable(s,fs,s.getTypeOfSensor());
              list.add(run);
          }
          return list;
      }  
      public void setRunnableList(){
          
      }
     public  void fireSpread(MyCanvas canvas){
     
        TimerTask task = new TimerTask() {
        @Override
        public void run() {
            boolean message=true;
           // if(message){
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
}
    };
              
       tFire.schedule(task, 0,600);
        
    }
     
       public  void Change() {
    	
     // CheckSubNetworks.resetCheckNet();
   
        tFire.cancel();
        timerNET.cancel();
        MyCanvas.failedS.clear();
        sensorList.clear();
        sensorList=MyCanvas.createSensorList2(MyCanvas.height,MyCanvas.width,MyCanvas.SensingRange,MyCanvas.CommunicationRange,MyCanvas.SensorAmount);
  
         ShortestPathList.n_list.clear();
            ShortestPathList.s_list.clear();
                    
            spl.findNeighbors(sensorList);
          
            initDistances(sensorList);
       //failSensor.setText("");
       startSensor.setText("");
        tFire= new Timer();
         timerNET= new Timer();
     
     
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
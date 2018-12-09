
 /* This is the main class of our project, it takes care of every action that each button has and 
    initialize the main objects needed throughout the simulator.
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
import java.io.IOException;

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
 
 private static WriteExcel WriteExcels;
 private static MyCanvas canvas;

 private boolean running = false;
 private long summedTime = 0;
 private Timer tFire;
 private Timer timerRouting;
 private Timer timerNET;
 private JLabel seconds;
 private JLabel minutes;
 private JLabel milli;
 static JTextField startSensor ;
 private ShortestPathList spl;
 static  JFrame window;
 static int ModeInt=0;
 static Sensor fs;
 static ArrayList<SP> toDraw;
 
  

  public EmbeddedSystem(){
     seconds= new JLabel();
     minutes= new JLabel();
     milli= new JLabel();
    startSensor = new JTextField(5);
    tFire= new Timer();
    timerNET= new Timer();
    timerRouting = new Timer();
    spl  = new ShortestPathList();
    window= new JFrame();
    toDraw=new ArrayList<SP>();
     }
 
    public static void main(String[] args) {
            
    	 //for Excel
    	 ArrayList<Integer> TotalSensors = new ArrayList(); 
    	 ArrayList<Integer> Connected = new ArrayList(); 
    	 ArrayList<Integer> subnets = new ArrayList(); 
    	 ArrayList<Integer> disconnected = new ArrayList(); 
    	 ArrayList<Integer> FailedSensors = new ArrayList(); 
         ArrayList<Integer> Coverage = new ArrayList();
    	 
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
            JTextField optimalSol = new  JTextField (20);
            
               //Options for Excel 2 
            JTextField SensorAmount = new JTextField(20);
            JTextField SensorStep = new JTextField(20);
            JTextField SensorLimit = new JTextField(20);
              
            //Options for Excel
            JTextField Failsensors = new JTextField(20);
            JTextField FailSteps = new JTextField(20);
            JTextField UpperLimit = new JTextField(20);
            
            OptionClass op= new OptionClass();
          
           /*
            0 = GRID,
            1 = COMPLETELY RANDOM 
            2 = SEMI RANDOM (ALWAYS IN RANGE OF EACHOTHER) 
            3 = SEMI RANDOM GRID
            */
            String[] modes = new String[] {"Grid", "Completley Random", "Semi-Random", "Semi-Random Grid", "Semi-Random-Sections","optimal"};
 
            JComboBox<String> comboModes = new JComboBox<>(modes);
            FileInport.InportFile();
            MyCanvas canvas = new MyCanvas();        
          

            sensorList=MyCanvas.createSensorList2(MyCanvas.width,MyCanvas.height,MyCanvas.SensingRange,MyCanvas.CommunicationRange,MyCanvas.SensorAmount);
           
            System.out.println("SensorList size"+sensorList.size());
          
             window.setSize(800,600);
             window.setTitle("Control");
             window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
         
        
             window.setContentPane(op.viewWindow);
             window.setLocationByPlatform(true);
             window.setVisible(true);
             window.pack();
          
          
             simulation.setSize(800,700);
             simulation.setTitle("Embedded");
             simulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             simulation.setContentPane(canvas.view);
             simulation.setLocationByPlatform(true);
             simulation.pack();
             simulation.setVisible(true);
             
                canvas.drawSensor();
       
             

////////////////////////////////////////////////
             JButton PrintExcelB = new JButton("Print Excel");
             
             PrintExcelB.addActionListener(new ActionListener() {
       			@Override
       			public void actionPerformed(ActionEvent e) {
       				WriteExcel WriteExcels = new WriteExcel(sensorList);
                           }
             });
             
             PrintExcelB.setBounds(150,500,200,30);
              window.getContentPane().add(PrintExcelB);
             
////////////////////////////////////////////////             
                 //GUI FOR STOP/START FIRE 
          JButton StopFireButton = new JButton("Start/Stop Fire");     
          
          StopFireButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				emb.timerRouting.cancel();
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
          
          cS.setBounds(50,420,80,30);
          window.getContentPane().add(cS);
          
  ////////////////////////////////////////////////  ******************************      
          JButton colorSensorB = new JButton("drawNeighbour");
          
          colorSensorB.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				
    				String colorSensorStr = cS.getText();
                               
    				checkShortestPath();
    				
    				/*
                                for(Sensor s: sensorList){
                                    if(s.getTypeOfSensor().equalsIgnoreCase(colorSensorStr)){
                                       // canvas.changeElement(s);
                                     
                                     GridSP.printN(s);
                                     canvas.drawSubNetwork(s.getNeighbourSeonsor());
                                    }
                                }
                                */
    					 
    			}
          });
          
          colorSensorB.setBounds(150,420,200,30);
           window.getContentPane().add(colorSensorB);
 //////////////////////////////////////////////// Display the sensor names for each point
           
           JButton SensorLabelButton = new JButton("Show Sensor Label");   
           SensorLabelButton.addActionListener(new ActionListener() {
     			@Override
     			public void actionPerformed(ActionEvent e) {
     				 //emb.Change();
     	              canvas.resetCanvas();   
     	              			
   				 Sensor s;
	          	   for(int i=0;i<sensorList.size(); i++){
	                     s=sensorList.get(i);
	                     
	             JLabel Sensorname = new JLabel(s.typeOfSensor);
	             Sensorname.setBounds(s.getPositionX()+5,s.getPositionY()-50,100,100);
	             simulation.getContentPane().add(Sensorname);
     			}
     			}
           });
           
           SensorLabelButton.setBounds(500,420,200,30);
            window.getContentPane().add(SensorLabelButton);
////////////////////////////////////////////////    Button to add sensors until an upper limit checking for each intervall the connettivity and the 
////////////////////////////////////////////////    and the coverage of the network, saving everything in an excel file
      
    
             SensorAmount.setBounds(100,570,50,30);
            SensorStep.setBounds(150,570,50,30);
            SensorLimit.setBounds(200,570,50,30);
            
            window.getContentPane().add(SensorAmount);     
            window.getContentPane().add(SensorStep);      
            window.getContentPane().add(SensorLimit);                   

            JButton SubnetExcel2 = new JButton("Print To Excel (Sensor Amount) ");
            
            SubnetExcel2.addActionListener(new ActionListener() {
      			@Override
      			public void actionPerformed(ActionEvent e) {
      				
                    String SensorAmountStr = SensorAmount.getText();
                    int SensorAmount = (Integer.parseInt(SensorAmountStr));
                    
                    String SensorStepsStr = SensorStep.getText();
                    int steps = (Integer.parseInt(SensorStepsStr));     
                    
                    String SensorLimitStr = SensorLimit.getText();
                    int Limit = (Integer.parseInt(SensorLimitStr));
                                     
      				for(int j = SensorAmount; j < Limit; j=j+steps) {
      					
      					MyCanvas.SensorAmount = j;    					
  	
      				for(int i = 0; i < 30; i++) {
                                    int check =0;
                        CheckSubNetworks.resetCheckNet();
      				    emb.Change();
       					emb.net();
                        canvas.resetCanvas(); 
                        canvas.drawFailedSensors();

      					try {      		     			
      			   			//sleep 
      			   			Thread.sleep(100);
      			   			
      			   		} catch (InterruptedException n) {
      			   			n.printStackTrace();
      			                }
                                        check=emb.fireSpread2(canvas);
                                        if(check>0){
                                            
                                      
      					
      				    Coverage.add(emb.fireSpread2(canvas));
      					FailedSensors.add(0);
      					TotalSensors.add(MyCanvas.sensorList.size());
      					subnets.add(CheckSubNetworks.subNetList.size());
      					disconnected.add(MyCanvas.sensorList.size()-CheckSubNetworks.getConnectedSensors()-1);
      					Connected.add(CheckSubNetworks.getConnectedSensors()+1);     	      					
      				}else{
                                            i--;
                                        } 
                                }
      				}
                    
                   WriteExcel.PrintConnectedNetwork2(TotalSensors, FailedSensors,Connected, disconnected, subnets, Coverage);       
                          }
            });
            
            SubnetExcel2.setBounds(250,570,250,30);
            window.getContentPane().add(SubnetExcel2);
////////////////////////////////////////////////    Button that allowes you to fail sensors up to a limit defining the gap between each iteration  
  ////////////////////////////////////////////////    It checks the connectivity of the network and the coverage each time saving everything in an excel file                         
            Failsensors.setBounds(100,540,50,30);
            FailSteps.setBounds(150,540,50,30);
            UpperLimit.setBounds(200,540,50,30);
            
            window.getContentPane().add(Failsensors);     
            window.getContentPane().add(FailSteps);      
            window.getContentPane().add(UpperLimit);                   

            JButton SubnetExcel = new JButton("Print To Excel (Failed Amount)");
            
            SubnetExcel.addActionListener(new ActionListener() {
      			@Override
      			public void actionPerformed(ActionEvent e) {
      				
                    String FailsensorsStr = Failsensors.getText();
                    int failedSensors = (Integer.parseInt(FailsensorsStr));
                    
                    String FailStepsStr = FailSteps.getText();
                    int steps = (Integer.parseInt(FailStepsStr));     
                    
                    String UpperLimitStr = UpperLimit.getText();
                    int Limit = (Integer.parseInt(UpperLimitStr));
                                     
      				for(int j = failedSensors; j < Limit; j=j+steps) {
  	
      				for(int i = 0; i < 30; i++) {
                                     int check =0;
                        CheckSubNetworks.resetCheckNet();
      				    emb.Change();
                        canvas.resetCanvas();                          			
      					MyCanvas.randomFail(j);
      					emb.net();
      					
      					try {
      		     			
      			   			//sleep 
      			   			Thread.sleep(100);
      			   			
      			   		} catch (InterruptedException n) {
      			   			n.printStackTrace();
      			                }
                                        check=emb.fireSpread2(canvas);
                                      if(check>0){
      					Coverage.add(emb.fireSpread2(canvas));
                                        
      					FailedSensors.add(j);
      					TotalSensors.add(MyCanvas.sensorList.size());
      					subnets.add(CheckSubNetworks.subNetList.size());
      					disconnected.add(MyCanvas.sensorList.size()-CheckSubNetworks.getConnectedSensors()-1);
      					Connected.add(CheckSubNetworks.getConnectedSensors()+1);     	
      					
                                      }else{
                                          i--;
                                      }
                                }
      				}
                    
                   WriteExcel.PrintConnectedNetwork2(TotalSensors, FailedSensors ,Connected, disconnected, subnets, Coverage);       
                          }
            });
            
            SubnetExcel.setBounds(250,540,250,30);
             window.getContentPane().add(SubnetExcel);

            
            
    ////////////////////////////////////////////////
  ////////////////////////////////////////////////                               

            JButton SaveButton = new JButton("Save");
            
            SaveButton.addActionListener(new ActionListener() {
      			@Override
      			public void actionPerformed(ActionEvent e) {
      				MyCanvas.sensorListsaved.addAll(sensorList);
      				System.out.println("saved");
      				for(int i=0;i< MyCanvas.sensorListsaved.size();i++){
                        System.out.println (i+" "+MyCanvas.sensorListsaved.get(i).getTypeOfSensor());
                    }
      				System.out.println (+MyCanvas.sensorListsaved.size());
                    
                          }
            });
            
            SaveButton.setBounds(500,470,200,30);
             window.getContentPane().add(SaveButton);
             
             
             JButton LoadButton = new JButton("Load");            
             LoadButton.addActionListener(new ActionListener() {
       			@Override
       			public void actionPerformed(ActionEvent e) {
                 
                emb.tFire.cancel();
       	        emb.timerNET.cancel();
       	        emb.timerRouting.cancel();
       	        MyCanvas.failedS.clear();
       	        sensorList.clear();
       	        sensorList.addAll(MyCanvas.sensorListsaved);
       	      
       	         ShortestPathList.n_list.clear();
       	            ShortestPathList.s_list.clear();
       	                    
       	            emb.spl.findNeighbors(sensorList);
       	          
       	            initDistances(sensorList);
       	       //failSensor.setText("");
       	       startSensor.setText("");
       	        emb.tFire= new Timer();
       	         emb.timerNET= new Timer();
       	         emb.timerRouting=new Timer();
       	     
       				//sensorList.addAll(MyCanvas.sensorListsaved);
       				
       				canvas.resetCanvas();
                           }
             });
             
             LoadButton.setBounds(500,500,200,30);
              window.getContentPane().add(LoadButton);
             
//////////////////////////////////////////////// 
     //It shows the sensors belonging to the sub network that has number equal to the input text that you put
          drawSub.setBounds(50,450,80,30);
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
          
          drawSubB.setBounds(150,450,200,30);
           window.getContentPane().add(drawSubB);
 ////////////////////////////////////////////////  
 // it allows you to change the width of the forest, so the different mode that are based on the range of the forest will be displayed differently
     
          Width.setBounds(50,310,80,30);
          window.getContentPane().add(Width);
          JButton WidthButton = new JButton("Width");
          
          Width.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				
    				String widthStr = Width.getText();
    				MyCanvas.width  = Integer.parseInt(widthStr);	
    				emb.Change();
    				canvas.resetCanvas();		 
    			}
          });
          
          WidthButton.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  			
  				String widthStr = Width.getText();
  				MyCanvas.width  = Integer.parseInt(widthStr);	
  				emb.Change();
  				canvas.resetCanvas();		 
  			}
        	  
          });
       
          WidthButton.setBounds(150,310,200,30);
          window.getContentPane().add(WidthButton);
        
     ////////////////////////////////////////////////  
 // it allows you to change the Height of the forest, so the different mode that are based on the range of the forest will be displayed differently
           
          //GUI FOR HEIGHT 
          Height.setBounds(50,340,80,30);
          window.getContentPane().add(Height);
          JButton HeightButton = new JButton("Height");
          
          Height.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				
  				String heightStr = Height.getText();
  				MyCanvas.height  = Integer.parseInt(heightStr);	
  				emb.Change();
  				canvas.resetCanvas();		 
  			}
          	  
            });
          
          HeightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String heightStr = Height.getText();
				MyCanvas.height  = Integer.parseInt(heightStr);	
				emb.Change();
				canvas.resetCanvas();		 
			}
        	  
          });
       
          HeightButton.setBounds(150,340,200,30);
          window.getContentPane().add(HeightButton);
        
          
 ////////////////////////////////////////////////  
 // it allows you to change the deployment mode
               
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
          
 ////////////////////////////////////////////////  
 // it allows you to change the amount of sensor used in the current deployment mode, 
 // it doesn't work for the grid and optimal mode since they are based on the range of the forest
          Sensors.setBounds(400,370,80,30);
          window.getContentPane().add(Sensors);
          JButton SensorsButton = new JButton("Sensors");
          
          Sensors.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				
  				if(MyCanvas.mode == 1 || MyCanvas.mode == 2 || MyCanvas.mode == 4) {
  				String SensorsStr = Sensors.getText();
  				MyCanvas.SensorAmount  = Integer.parseInt(SensorsStr);
                                emb.Change();
  				canvas.resetCanvas();		
  				CheckSubNetworks.resetCheckNet();
  				}
  			}
          	  
            });
          
          SensorsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(MyCanvas.mode == 1 || MyCanvas.mode == 2) {
				String SensorsStr = Sensors.getText();
				MyCanvas.SensorAmount  = Integer.parseInt(SensorsStr);
				emb.Change();
				canvas.resetCanvas();		
				CheckSubNetworks.resetCheckNet();
				}
			}
        	  
          });
       
          SensorsButton.setBounds(500,370,200,30);
          window.getContentPane().add(SensorsButton);
 ////////////////////////////////////////////////  
 // it allows you to change the sensing range of every sensor in the network
        
       
          SenR.setBounds(400,310,80,30);
          window.getContentPane().add(SenR);
          JButton SenRButton = new JButton("Sensing Range");
          
          SenR.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e) {
  			
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
         ////////////////////////////////////////////////  
 // it allows you to change the comunication range of every sensor in the network  
          
          
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
       
             
        
  ////////////////////////////////////////////////  
 //it displays the fire grid that it is used to calculate the coverage of the network

 
  JButton setFire2 = new JButton("Coverage Fire");
       setFire2.addActionListener(new ActionListener(){
       @Override
         public void actionPerformed(ActionEvent e) {
    	   emb.net();
           canvas.resetCanvas(); 
           canvas.drawFailedSensors();
           emb.fireSpread2(canvas);
         }
      
      });
       
     setFire2.setBounds(700,240,200,30);  
     emb.window.getContentPane().add(setFire2);
 ////////////////////////////////////////////////  
 //it changes the communication range due to the effect of the weaather.
 
       JButton weather = new JButton("Weather");
       weather.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
               
          //  printToDraw();
               
             CheckSubNetworks.resetCheckNet();
             
                 canvas.weatherChange(10);
                  emb.spl.findNeighbors(sensorList);    
                  initDistances(sensorList);
                 
                 
            }
           
       });
       weather.setBounds(150,210,200,30);
      window.getContentPane().add(weather);
 /////////////////////////////////////////////////////////////
   // It randomly fails the amount of sensors that you put as input. 
        
        
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
// it checks for each sensors if it is able to send a message that will reach the fire station. The one that are not able are not connected. 
         JButton checkNet = new JButton("CheckNet");
        checkNet.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
           
               emb.net();
            }
           
       });
 
        checkNet.setBounds(40,80,200,30);
        window.getContentPane().add(checkNet);
        
         /////////////////////////////////////////////////////////////         
        //it prints the result of the checkNet
         
        JButton displaySubNet = new JButton("Print Sub Network");     
        displaySubNet.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                if(CheckSubNetworks.getConnectedSensors()==sensorList.size()-1){
                     JOptionPane.showMessageDialog(window,
                      "Everyone is connected to Fire Station");
                }else{
                JOptionPane.showMessageDialog(window,
                      "Number of sensors connected to FS: "+CheckSubNetworks.getConnectedSensors() + "\n"+ "number of subNet" + CheckSubNetworks.subNetList.size());
                }
                CheckSubNetworks.printSubNets();
                Sensor s=sensorList.get(10);
    
					WriteExcel.PrintConnectedNetwork(CheckSubNetworks.getConnectedSensors(),CheckSubNetworks.subNetList);
	       
            }
           
       });
  
        displaySubNet.setBounds(240,80,200,30);
       window.getContentPane().add(displaySubNet);
       
       
/////////////////////////////////////////////////////////////      
//it starts a random fire
       
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
 // It restart the canvas and the information about the senors in order to perform different options without having to ran the simulator again      
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
//it starts a random fire, the network will implement a shortest path communication protocol instead of the broadcasting one
         
         
        JButton shortestPath = new JButton("Shortest Path");
       shortestPath.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
              
           
        	  //emb.Change();
              canvas.resetCanvas(); 
              canvas.drawFailedSensors();
                            
              for(Sensor s : sensorList){
                  s.setForwardMsg(false);
              }
              
        	  
       for(Sensor s : sensorList){
            if(s.typeOfSensor.equals("fs")){
                s.hops = 0;
                System.out.println(s.typeOfSensor + " is " + s.hops + " away from fs");
            }
        }
        
        for(int i = 0; i < sensorList.size() ; i++){
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
                emb.fireSpreadRouting(canvas,emb.spl); 
            }
           
       });
       shortestPath.setBounds(440,80,200,30);
       emb.window.getContentPane().add(shortestPath);
    
       //------------------------------------------------
       
       
  
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
    public int fireSpread2(MyCanvas canvas){   
 
     canvas.setFire2();
  
     int coverage = canvas.distanceFireSensor2();

     System.out.print("count: " + coverage);
	return coverage;
  }
  public void fireSpreadRouting(MyCanvas canvas,ShortestPathList spl){
      
            TimerTask task = new TimerTask() {
        boolean connected=false;
        @Override
        public void run() {
            
            
               
            //if(message){
          Sensor s=null; //sensorList.get(sensorList.size()-1);
          
          canvas.setFire();
          
          s=canvas.distanceFireSensor();
          if (s!=null) {
                    
                  
            
        	  if (CheckSubNetworks.checkSesnorInSubNet(s)){
                           System.out.println("HERE");
                           connected = true;
               
        		/*  for(int i = 0; i < s.neighbourSensor.size(); i++) {
        		  if(CheckSubNetworks.checkSesnorInSubNet(s.neighbourSensor.get(i))) {
        			 connected = true;
        			 i = s.neighbourSensor.size();
        		  }  			  
        		  }*/
                if(connected==true) {
              SPThread   spT= new SPThread(s);
                     spT.start();
                             
                                   
                                   /*   newSP(s, spl);
                                   //   ShortestPathList.printSP();
                                   canvas.drawThickLine(s, s_list);
                                   
                                   ShortestPathList.n_list.clear();
                                   ShortestPathList.s_list.clear();
                                   
                                   spl.findNeighbors(sensorList);
                                   
                                   //initDistances(sensorList);
                                   
                                   // canvas.drawLine(s, spl.s_list);
                               */                    
                }
           } else {
        	   s.setForwardMsg(true);
           }
        }
        }
    };
              
    timerRouting.schedule(task, 0,200);
        
    }
      
    
             
      
     public void fireSpread(MyCanvas canvas){
     
        TimerTask task = new TimerTask() {
        @Override
        public void run() {
          Sensor s=null; //sensorList.get(sensorList.size()-1);
            Sensor fs= sensorList.get(sensorList.size()-1);
           canvas.setFire();
           
           s=canvas.distanceFireSensor();
          if ((s!=null)){
              try {
                  
                  canvas.BCM(s, fs);
              } catch (InterruptedException ex) {
                  Logger.getLogger(EmbeddedSystem.class.getName()).log(Level.SEVERE, null, ex);
              }
           }
}
    };
              
       tFire.schedule(task, 0,600);
        
    }
    //Called when you switch mode or reastart the simulation with the restart button 
     public void Change() {
    	
     // CheckSubNetworks.resetCheckNet();
   
        tFire.cancel();
        timerNET.cancel();
        timerRouting.cancel();
        MyCanvas.failedS.clear();
        sensorList.clear();
        sensorList=MyCanvas.createSensorList2(MyCanvas.height,MyCanvas.width,MyCanvas.SensingRange,MyCanvas.CommunicationRange,MyCanvas.SensorAmount);
  
         ShortestPathList.n_list.clear();
            ShortestPathList.s_list.clear();
                    
            spl.findNeighbors(sensorList);
          
            initDistances(sensorList);
    
       startSensor.setText("");
        tFire= new Timer();
         timerNET= new Timer();
         timerRouting=new Timer();
     
     
    }
       
       
     
  public void update(long dT){

        
        
       minutes.setText(String.valueOf((dT /(1000*60)) % 60)+"  :");
        seconds.setText(String.valueOf((dT / 1000) % 60 ) + " : ");
        milli.setText(String.valueOf((dT)%1000));
    }
      
        public static void setJlabel(String str){
                startSensor.setText(str);
      
    }

    public void resetAll(){
       
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
  
  
  public static void checkShortestPath(){
      int i=0;
       ArrayList<Sensor> list =new ArrayList();
      for(subNet sub:CheckSubNetworks.subNetList){
          if(sub.getConnectedToFS()){
              list= sub.getSubNet();
          }
      }
     
      for(Sensor s:sensorList){
          if(s.getForwardMsg()){
              if(CheckSubNetworks.myContains(s,list)){
                  i++;
              }else{
                  System.out.println("sensor not connected "+s.getTypeOfSensor());

              }
          }
          
      }
      if(i==list.size()){
          System.out.println("EVERYONE has been check in the sp");
      }else{
           System.out.println("something is wrong in the sp");
      }
  }
  public static void ADDToDraw(SP sp){
    toDraw.add(sp);
  }
  public static void printToDraw( ){
      SP sp;
      
      System.out.println("SIZE"+toDraw.size());
      for(int i=0;i<toDraw.size();i++){
          sp=toDraw.get(i);
          MyCanvas.drawThickLine(sp.s, sp.shorthestPath);
          /*for(Sensor s: sp.shorthestPath){
              System.out.println(s.getTypeOfSensor());
          }*/
     }
  }
  
  
  
  
  
 
}
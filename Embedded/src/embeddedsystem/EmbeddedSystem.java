/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
/**
 *
 * @author eleonora
 */
public class EmbeddedSystem {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyCanvas canvas = new MyCanvas();
        //canvas.fillSensorList();
        canvas.fillSensorList();
        canvas.drawSensor();
        JFrame window= new JFrame();
        window.setSize(1000,900);
        window.setTitle("Embedded");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(canvas.view);
       // window.pack();
        window.setLocationByPlatform(true);
        window.setVisible(true);
        JButton button = new JButton("weather");
       button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("works");
                canvas.weatherChange(20);
                
            }
           
       });
       button.setBounds(900,10,200,30);
       window.getContentPane().add(button);
        
       timeSchedule(canvas);
       
     /*  
         Timer timer = new Timer();
         
         TimerTask task = new TimerTask() {
        @Override
        public void run() {
           
            Sensor s= canvas.getSensorList().get(1);
           canvas.fireDetection(s);
           
        }
    };
       timer.schedule(task, 4200);
           
  
       */
   
    

        
    
        
    }
    public  static void timeSchedule(MyCanvas canvas){
        Timer t= new Timer();
            TimerTask task = new TimerTask() {
        @Override
        public void run() {
           
            Sensor s= canvas.getSensorList().get(8);
            try {
                canvas.fireDetection(s);
            } catch (InterruptedException ex) {
                Logger.getLogger(EmbeddedSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    };
       t.schedule(task, 4200);
        
    }
    
}

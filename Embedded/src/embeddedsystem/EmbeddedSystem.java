/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import javax.swing.JFrame;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;
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
        canvas.fillSensorList();
        canvas.drawSensor();
        JFrame window= new JFrame();
        window.setSize(1000,900);
        window.setTitle("Embedded");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(canvas.view);
        window.pack();
        window.setLocationByPlatform(true);
        window.setVisible(true);
        
       
        
       
         Timer timer = new Timer();
         TimerTask task = new TimerTask() {
        @Override
        public void run() {
           
            
            canvas.changeElement();
           
        }
    };
       timer.schedule(task, 4200);
           
  
       
   
    

        
    
        
    }
    
}

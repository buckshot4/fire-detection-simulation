/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import static embeddedsystem.MyCanvas.sensorList;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eleonora
 */
public class SPThread  extends Thread {
     //ArrayList <Sensor> checkedSesnsors=new ArrayList();
      ArrayList<Sensor> shortestPath_list; 
     Sensor s;
     Sensor fs;

     public SPThread(Sensor s){
         this.s= s;
         this.fs=sensorList.get(sensorList.size()-1);
         shortestPath_list=new ArrayList<Sensor>();

     }

  
     
         public void run(){
         System.out.print("THREAD SP");
            GridSP grid = new GridSP(s);
             grid.newSPThread(s, shortestPath_list);
            
             
             

    }
}
/*
Thread used to ran the checkNetwork method to see the connectivity of the network
 */
package embeddedsystem;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eleonora
 */
 public class MyThread extends Thread {
     //ArrayList <Sensor> checkedSesnsors=new ArrayList();
     subNet sb= new subNet();
     Sensor s;
     Sensor fs;

     public MyThread(Sensor s,Sensor fs){
         this.s= s;
         this.fs=fs;
         
         
     }
      public void run(){
         try {
             System.out.print("THREAD");
             CheckSubNetworks.checkNetworkCutThread(s, fs,sb);
         } catch (InterruptedException ex) {
             Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
   
  }
 
    

    
    
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eleonora
 */
public class MyRunnable implements Runnable{
    
    public volatile boolean stopThread = false;
    private subNet sb= new subNet();
     private Sensor s;
    private Sensor fs;
    private Thread t;
    private String threadName;
    public MyRunnable(Sensor s,Sensor fs,String name){
         this.s= s;
         this.fs=fs;
         threadName=name;
    }
    public void stopThread(){
        stopThread=true;
    }
    public Thread getThread(){
        return t;
    }
    public Sensor getS(){
        return s;
    }
   
        @Override
        public void run() {
             while(!stopThread) {
                 System.out.println("THREAD"+threadName);
               try {
                     CheckSubNetworks.checkNetworkCutThread(s, fs,sb);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(MyRunnable.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        }
        }            
         public void start () {
         System.out.println("Starting " +  threadName );
         if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
        
    }
   
  

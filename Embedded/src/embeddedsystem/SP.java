/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.util.ArrayList;

/**
 *
 * @author eleonora
 */
public class SP {
    Sensor s;
    ArrayList <Sensor>shorthestPath;
    
    public SP(Sensor newS, ArrayList<Sensor> newSP){
        this.s=newS;
        this.shorthestPath=newSP;
        
    }
     public SP( ArrayList<Sensor> newSP){
       
        this.shorthestPath=newSP;
        
    }
     public Sensor getSensorSP(){
          return s;
     }
    
    
}


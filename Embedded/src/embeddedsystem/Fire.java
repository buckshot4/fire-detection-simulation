/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author eleonora
 */
public class Fire {
    private int intensity;
    private boolean state;
    private int positionX;
    private int positionY;
    private int range;
    
    static  ArrayList<Fire> FireList =new ArrayList();
    

 public Fire(int x,int y,int range){
     positionX=x;
     positionY=y;
     this.range=range;   
 }   
 
 public static ArrayList<Fire> createFireList(){

     ArrayList FireList = new ArrayList<> ();
     Fire tempFire = null;
     Random random= new Random();
     int Xstep = MyCanvas.height/19;
     int Ystep = MyCanvas.width/19;
     for(int i = 0; i < 17; i++) {
    	 for(int j = 0; j < 17; j++) {
    		 tempFire = new Fire(i*Xstep, j*Ystep, 8);
    		 FireList.add(tempFire);
    	 }  
     }
	return FireList;     
 }
    
public void setIntensity(int intensity){
    this.intensity=intensity;
}    
public void setActive(boolean b){
    state=b;
}
public void setPositionX(int pX){
    positionX=pX;
    
}
public void setPositionY(int pY){
    positionY=pY;
}
public void setRange(int range){
    this.range= range;
}
public int getRange(){
    return range;
}
public int getPositionX(){
    return positionX;
}
public int getPositonY(){
    return positionY;
}
public boolean getState(){
    return state;
}
}

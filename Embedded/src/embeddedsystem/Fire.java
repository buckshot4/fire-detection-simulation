/*
 Class that describes the fire. It initialize the fire grid used to calculate the coverage of the system
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
    
    //stores fires for the fire grid that we use to analyze coverage
    static  ArrayList<Fire> FireList =new ArrayList();
    

 public Fire(int x,int y,int range){
     positionX=x;
     positionY=y;
     this.range=range;   
 }   
 
 //creates the fire grid based on the size of the forest
 public static ArrayList<Fire> createFireList(){

     ArrayList FireList = new ArrayList<> ();
     Fire tempFire = null;
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

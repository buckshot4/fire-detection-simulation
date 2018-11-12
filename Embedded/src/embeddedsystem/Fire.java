/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

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
    

 public Fire(int x,int y,int range){
     positionX=x;
     positionY=y;
     this.range=range;
  
     
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

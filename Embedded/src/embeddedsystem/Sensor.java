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
public class Sensor {
    int x;
    int y;
    String typeOfSensor;
    int ratio;
    boolean active;
    ArrayList<Sensor> neighbourSensor;
    
    public Sensor(int xP,int YP,String tS, int ratioN){
        x= xP;
        y=YP;
        typeOfSensor=tS;
        ratio=ratioN;
        neighbourSensor= new ArrayList();
    } 
    public int getPositionX(){
        return x;
    }
    public int getPositionY(){
        return y;
    }
    public void setPX(int xN){
        x= xN;
    }
    public void setPY(int YN){
        y=YN;
    }
    public String getTypeOfSensor(){
        return typeOfSensor;
    }
    public void setActive(boolean state){
        active=state;
    }
    public boolean getState(){
        return active;
    }
    public void setRatio(int newRatio){
        ratio= newRatio;
    }
    public int getRatio(){
        return ratio;
    }
    
    public void setNeighbourSeonsor(ArrayList<Sensor> newNeighbours){
        neighbourSensor=new ArrayList(newNeighbours);
    }
    
    public ArrayList<Sensor> getNeighbourSeonsor(){
        return neighbourSensor;
    }
    public int getNeighborNumber(){
        return neighbourSensor.size();
    }
    
}

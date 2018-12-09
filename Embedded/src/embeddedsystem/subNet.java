/*
Class that define the object sub network as an arrayList of sensors that are componing the subNetwork
and a boolean that defines if the subnetwork is connected somehow to the fire station
 */
package embeddedsystem;

import java.util.ArrayList;

/**
 *
 * @author eleonora
 */
public class subNet {
    
    private boolean connectedToFS;
    private ArrayList<Sensor> nodes;
    public subNet(){
     connectedToFS=false;
     nodes=new ArrayList();
    }
    public subNet(ArrayList<Sensor> nodes,boolean b){
       this.nodes=nodes;
       connectedToFS=b;
    }
    public boolean  getConnectedToFS(){
        return connectedToFS;
    }
    public ArrayList<Sensor> getSubNet(){
        return  nodes;
    }
   
    
    public void addToSubN(Sensor s){
        nodes.add(s);
    }
    public void setCon(boolean b){
        connectedToFS=b;
    }
}

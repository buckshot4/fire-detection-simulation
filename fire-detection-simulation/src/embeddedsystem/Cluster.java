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
public class Cluster {
    
    int id;
    
    int[] positionCenter= new int[2];
    int[][] positionCluster= new int[4][2];
    int[][] closeClusters=new int[4][2];
    
     public Cluster(int nId,int[]center,int[][]positionC){
        id=nId;
        positionCenter=center;
        positionCluster=positionC;
        
    }
    
    public Cluster(int nId,int[]center,int[][]positionC, int[][]closeC){
        id=nId;
        positionCenter=center;
        positionCluster=positionC;
        closeClusters=closeC;
    }
    
    public int getId(){
        return id;
    }
    public void setPCenter(int[] center){
        positionCenter=center;
    }
     public void setPCluster(int[][] cluster){
        positionCluster=cluster;
    }
      public void setnearCluster(int[][] nearC){
        closeClusters=nearC;
    }
     
      public int[] getPCenter(){
        return positionCenter;
    }
     public int[][] getPCluster(){
        return positionCluster;
    }
      public int[][] getnearCluster(){
        return closeClusters;
    }
    
}

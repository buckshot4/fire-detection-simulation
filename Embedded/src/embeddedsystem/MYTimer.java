/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author eleonora
 */
public class MYTimer implements Runnable {

    private Thread runThread;
    private boolean running = false;
    private boolean paused = false;
    private EmbeddedSystem timeFrame;
    private long summedTime = 0;
    private JLabel secondL;
    private JLabel minutesL;
    private JLabel millisL;
    public MYTimer(){
        
    }
   public MYTimer(EmbeddedSystem timeFrame,JLabel mLabel ,JLabel secondLabel,JLabel milliL) {
       this.secondL=secondLabel;
        this.timeFrame = timeFrame;
        this.minutesL=mLabel;
        this.millisL=milliL;
               
                
    }

  /*  public static void main(String[] args) {
        TimeFrame t = new TimeFrame();
        JFrame f = new JFrame("Timer");
        f.setSize(300,200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.getContentPane().add(t);
        f.setVisible(true);
    }
*/
   public JLabel getMilliseconds(){
       return millisL;
   }
   public JLabel getsecondLabel(){
       return secondL;
   }
   
   public JLabel getMinutesLabel(){
       return minutesL;
   }
    public void startTimer() {
        running = true;
       // paused = false;
        // start the thread up
        runThread = new Thread(this);
        runThread.start();
    }

  /*  public void pauseTimer() {
        // just pause it
        paused = true;
    }

    public void stopTimer() {
        // completely stop the timer
        running = false;
        paused = false;
    }*/

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        // keep showing the difference in time until we are either paused or not running anymore
        while(running) {
            timeFrame.update(summedTime + (System.currentTimeMillis() - startTime));
        }
        // if we just want to pause the timer dont throw away the change in time, instead store it
       
    }
}
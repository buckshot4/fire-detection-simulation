/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author eleonora
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeFrame extends JPanel{
    private JLabel time = new JLabel("Time goes here", JLabel.CENTER);
    private MYTimer timer;
    private JButton pause = new JButton ("Pause");
    private JButton start = new JButton ("Start");

    public TimeFrame(){
        //timer = new Timer(this);
         start.addActionListener(new starts());
         pause.addActionListener(new starts());
         add(time);
         add(start);
         add(pause);
    }
  
    public class starts implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == start){
                timer.startTimer();
            }else{
                timer.pauseTimer();
            }
        }
    }
}
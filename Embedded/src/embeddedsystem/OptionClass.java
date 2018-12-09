/*
Initialize the second Frame used to display every button 
 */
package embeddedsystem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author eleonora
 */
public class OptionClass {
     static JLabel viewWindow ;
 static BufferedImage surface2;
 
 
 public OptionClass(){
        surface2 = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
        
         viewWindow = new JLabel(new ImageIcon(surface2));
             Graphics gWindow = surface2.createGraphics();
             
            
                gWindow.setColor(Color.WHITE);
                 gWindow.fillRect(0,0,800,600);
                 gWindow.dispose();
 }
    
}

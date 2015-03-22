/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Ryan
 */
public class SimplePaint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame("PAAAIIINNNTTTTTT");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        frame.setSize((int)width, (int)height);

        ApplicationFrame panel = new ApplicationFrame();
        
        frame.addMouseListener(panel);
        frame.addMouseMotionListener(panel);
        frame.addKeyListener(panel);
        
        frame.add(panel);
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Ryan
 */
public class SimplePaint {

  /**
     * @param args the command line arguments
     */
    /*
    public static void main(String args[]) {

        //JFrame f = new JFrame("PAAAAAIIINNNTTTTT");
        
        SketchUI f = new SketchUI();
        ApplicationFrame p = new ApplicationFrame();
        //f.getPanel().add(p);
        /*
        ApplicationFrame ryanDoTheThing = new ApplicationFrame();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        f.setSize((int)width, (int)height);
        
        f.setVisible(true);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        
        f.add(ryanDoTheThing);
        f.addMouseListener(ryanDoTheThing);
        f.addMouseMotionListener(ryanDoTheThing);
        f.addKeyListener(ryanDoTheThing);
        */
    //}
   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SketchUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //SketchUI f = new SketchUI();
                ButtonFrame f = new ButtonFrame();
                f.setVisible(true);
                
                //ApplicationFrame p = new ApplicationFrame();
                
                //JPanel tmp = f.getPanel();
                //tmp.setLayout(new BorderLayout());
                //p.setSize(tmp.getWidth(),tmp.getHeight()-100);
                //tmp.add(p, BorderLayout.CENTER);
                //tmp.addMouseListener(p);
                //tmp.addMouseMotionListener(p);
                //tmp.addKeyListener(p);
                //f.addMouseListener(p);
                //f.addMouseMotionListener(p);
                //f.addKeyListener(p);
                //f.add(p, BorderLayout.CENTER);
                //f.validate();
                //f.repaint();
            }
        });
    }

}

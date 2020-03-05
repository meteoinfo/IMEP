/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteothink.imep;

import org.meteothink.imep.forms.FrmMain;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import org.meteoinfo.global.util.GlobalUtil;

/**
 *
 * @author yaqiang
 */
public class IMEP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        runApplication();
    }

    private static void runApplication() {
        try {
            /* Set look and feel */
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IMEP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(IMEP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(IMEP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(IMEP.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmMain frame = new FrmMain();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                //frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}

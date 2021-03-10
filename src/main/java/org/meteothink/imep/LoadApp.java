/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteothink.imep;

import org.meteothink.imep.forms.FrmMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import org.meteoinfo.ui.plugin.IApplication;
import org.meteoinfo.ui.plugin.PluginBase;

/**
 *
 * @author wyq
 */
public class LoadApp extends PluginBase {
    // <editor-fold desc="Variables">

    private JMenuItem appMI = null;

    //final private String path;
    // </editor-fold>
    // <editor-fold desc="Constructor">
    /**
     * Constructor
     */
    public LoadApp() {
        this.setName("IMEP");
        this.setAuthor("Yaqiang Wang");
        this.setVersion("0.3");
        this.setDescription("IMEP - Verification application");
        //path = GlobalUtil.getAppPath(LoadApp.class);
    }
    // </editor-fold>
    // <editor-fold desc="Get Set Methods">
    // </editor-fold>
    // <editor-fold desc="Methods">

    @Override
    public void load() {
        //Add menu item
        if (appMI == null) {
            appMI = new JMenuItem("IMEP - Verification");
            ImageIcon image = null;
            try {
                image = new ImageIcon(ImageIO.read(this.getClass().getResource("/imep/resources/check.png")));
            } catch (Exception e) {
            }
            appMI.setIcon(image);
            appMI.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onAppClick();
                }
            });
        }

        IApplication app = this.getApplication();
        JMenuBar mainMenuBar = app.getMainMenuBar();
        JMenu appMenu = app.getPluginMenu();
        appMenu.add(appMI);
        mainMenuBar.validate();
    }

    @Override
    public void unload() {
        if (appMI != null) {
            this.getApplication().getPluginMenu().remove(appMI);
            this.getApplication().getMainMenuBar().repaint();
        }
    }

    private void onAppClick() {
        IApplication app = this.getApplication();
        FrmMain frm = new FrmMain();
        frm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frm.setLocationRelativeTo((JFrame) app);
        frm.setVisible(true);
    }
    // </editor-fold>
}

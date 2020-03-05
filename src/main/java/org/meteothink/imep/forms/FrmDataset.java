/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteothink.imep.forms;

import org.meteothink.imep.global.Globals;
import org.meteothink.imep.verification.Dataset;
import java.io.File;
import javax.swing.JFileChooser;
import org.meteoinfo.data.meteodata.MeteoDataType;

/**
 *
 * @author yaqiang
 */
public class FrmDataset extends javax.swing.JDialog {
    
    private Dataset _dataset;

    /**
     * Creates new form FrmDataset
     *
     * @param parent
     * @param modal
     */
    public FrmDataset(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.jCheckBox_BigEndian.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton_OK = new javax.swing.JButton();
        jButton_Cancel = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jComboBox_DataType = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jTextField_FileName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField_Variable = new javax.swing.JTextField();
        jComboBox_TimeZone = new javax.swing.JComboBox();
        jButton_FileName = new javax.swing.JButton();
        jLabel_LevelIndex = new javax.swing.JLabel();
        jTextField_LevelIndex = new javax.swing.JTextField();
        jCheckBox_BigEndian = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton_OK.setText("OK");
        jButton_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OKActionPerformed(evt);
            }
        });

        jButton_Cancel.setText("Cancel");
        jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelActionPerformed(evt);
            }
        });

        jLabel13.setText("DataType:");

        jComboBox_DataType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_DataType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_DataTypeActionPerformed(evt);
            }
        });

        jLabel14.setText("File:");

        jLabel15.setText("TimeZone:");

        jLabel16.setText("Variable:");

        jComboBox_TimeZone.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton_FileName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Folder_1_16x16x8.png"))); // NOI18N
        jButton_FileName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FileNameActionPerformed(evt);
            }
        });

        jLabel_LevelIndex.setText("LevelIndex:");

        jTextField_LevelIndex.setText("0");

        jCheckBox_BigEndian.setText("Big_Endian");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_FileName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_FileName))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 114, Short.MAX_VALUE)
                        .addComponent(jButton_OK, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jButton_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(111, 111, 111))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_DataType, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jCheckBox_BigEndian)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox_TimeZone, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_Variable, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_LevelIndex)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_LevelIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox_DataType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox_BigEndian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jTextField_FileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_FileName))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField_Variable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_LevelIndex)
                    .addComponent(jTextField_LevelIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox_TimeZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_OK)
                    .addComponent(jButton_Cancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_FileNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FileNameActionPerformed
        // TODO add your handling code here:
        String path = System.getProperty("user.dir");
        File pathDir = new File(path);
        JFileChooser aDlg = new JFileChooser();
        aDlg.setAcceptAllFileFilterUsed(false);
        aDlg.setCurrentDirectory(pathDir);
        if (JFileChooser.APPROVE_OPTION == aDlg.showOpenDialog(this)) {
            File aFile = aDlg.getSelectedFile();
            System.setProperty("user.dir", aFile.getParent());
            
            this.jTextField_FileName.setText(aFile.getAbsolutePath());
        }
    }//GEN-LAST:event_jButton_FileNameActionPerformed

    private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
        // TODO add your handling code here:
        this._dataset.setMeteoDataType((MeteoDataType) this.jComboBox_DataType.getSelectedItem());
        this._dataset.setFileName(this.jTextField_FileName.getText());
        this._dataset.setTimeZone(Globals.getTimeZone(this.jComboBox_TimeZone.getSelectedItem().toString()));
        this._dataset.setVariableName(this.jTextField_Variable.getText());
        this._dataset.setLevelIndex(Integer.parseInt(this.jTextField_LevelIndex.getText()));
        this._dataset.setBigEndian(this.jCheckBox_BigEndian.isSelected());
        
        this.dispose();
    }//GEN-LAST:event_jButton_OKActionPerformed

    private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton_CancelActionPerformed

    private void jComboBox_DataTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_DataTypeActionPerformed
        // TODO add your handling code here:
        if (jComboBox_DataType.getItemCount() > 0) {
            MeteoDataType mdt = (MeteoDataType) this.jComboBox_DataType.getSelectedItem();
            this.updateMeteoDataType(mdt);
        }
    }//GEN-LAST:event_jComboBox_DataTypeActionPerformed
    
    private void updateMeteoDataType(MeteoDataType mdt) {
        switch (mdt) {
            case GrADS_Grid:
            case GrADS_Station:
                this.jCheckBox_BigEndian.setVisible(true);
                break;
            default:
                this.jCheckBox_BigEndian.setVisible(false);
                break;
        }
    }
    
    public void setDataset(Dataset dataset) {
        _dataset = dataset;
        this.jComboBox_DataType.removeAllItems();
        for (MeteoDataType dataType : MeteoDataType.values()) {
            this.jComboBox_DataType.addItem(dataType);
        }
        this.jComboBox_DataType.setSelectedItem(dataset.getMeteoDataType());
        this.jCheckBox_BigEndian.setSelected(dataset.isBigEndian());
        
        this.jTextField_FileName.setText(dataset.getFileName());
        
        this.jComboBox_TimeZone.removeAllItems();
        for (int i = -12; i <= 12; i++) {
            this.jComboBox_TimeZone.addItem(Globals.getTimeZoneString(i));
        }
        this.jComboBox_TimeZone.setSelectedItem(Globals.getTimeZoneString(dataset.getTimeZone()));
        
        this.jTextField_Variable.setText(dataset.getVariableName());
        this.jTextField_LevelIndex.setText(String.valueOf(dataset.getLevelIndex()));
    }

    /**
     * @param args the command line arguments
     */
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmDataset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmDataset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmDataset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmDataset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmDataset dialog = new FrmDataset(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Cancel;
    private javax.swing.JButton jButton_FileName;
    private javax.swing.JButton jButton_OK;
    private javax.swing.JCheckBox jCheckBox_BigEndian;
    private javax.swing.JComboBox jComboBox_DataType;
    private javax.swing.JComboBox jComboBox_TimeZone;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel_LevelIndex;
    private javax.swing.JTextField jTextField_FileName;
    private javax.swing.JTextField jTextField_LevelIndex;
    private javax.swing.JTextField jTextField_Variable;
    // End of variables declaration//GEN-END:variables
}

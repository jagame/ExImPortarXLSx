/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimportarexcel;

import java.awt.Color;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;
import pruebaimportarexcel.database.DBClass;
import pruebaimportarexcel.database.DBScheme;
import pruebaimportarexcel.util.SwingUtil;
/**
 *
 * @author Emilio
 */
public class DialogConfigurarBaseDatos extends javax.swing.JDialog {
    private DBScheme baseDatos;
    private Color defaulColor;
    /**
     * Creates new form DialogConfigurarBaseDatos
     */
    public DialogConfigurarBaseDatos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        baseDatos = null;
        
        initComponents();
        this.defaulColor = this.jToggleButton1.getBackground();
        this.setLocationRelativeTo(null);
        SwingUtil.initComboBoxModel(this.jComboBox1.getModel(),DBClass.values());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configurar Conexión a Base de Datos");

        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<String>());

        jLabel1.setText("Gestor de Base de Datos:");

        jSpinner2.setModel(new SpinnerNumberModel(3306,1,65535,1)
        );

        jLabel2.setText("IP Servidor:");

        jLabel3.setText("Puerto:");

        jToggleButton1.setText("Conectar");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Usuario:");

        jTextField1.setText("root");

        jLabel6.setText("Password:");

        jTextField2.setText("root");
        jTextField2.setToolTipText("");

        jTextField3.setText("localhost");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            if (this.baseDatos.isOpen()) {
                this.baseDatos.close();
            }
        } catch (Exception ex) {
            //Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            this.dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        String mensaje = "";
        
        if(this.jToggleButton1.isSelected()){
            try {
                this.baseDatos = new DBScheme(
                        DBClass.valueOf(this.jComboBox1.getSelectedItem().toString().toUpperCase()),
                        this.jTextField3.getText(), 
                        String.valueOf(this.jSpinner2.getValue()),
                        "",
                        this.jTextField1.getText(),
                        this.jTextField2.getText()
                );
                               
                mensaje = "Conectado a: ";
                mensaje += this.baseDatos.getURL();
                this.jToggleButton1.setBackground(Color.GREEN);
                this.jToggleButton1.setText("Desconectar");
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = "ERROR: \n" + ex.getClass().getSimpleName();
                mensaje += "\n Causa: \n"+ex.getMessage();
                this.baseDatos=null;
            } catch (IllegalAccessException ex) {
                //Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = "ERROR: \n" + ex.getClass().getSimpleName();
                mensaje += "\n Causa: \n"+ex.getMessage();
                this.baseDatos=null;
            } catch (InstantiationException ex) {
                //Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = "ERROR: \n" + ex.getClass().getSimpleName();
                mensaje += "\n Causa: \n"+ex.getMessage();
                this.baseDatos=null;
            } catch (SQLException ex) {
                //Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = "ERROR: \n" + ex.getClass().getSimpleName();
                mensaje += "\n Causa: \n"+ex.getMessage();
                this.baseDatos=null;
            } catch (Exception ex){
                //Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = "ERROR: \n" + ex.getClass().getSimpleName();
                mensaje += "\n Causa: \n"+ex.getMessage();
                this.baseDatos=null;
            }finally{
                if(this.baseDatos==null){
                    this.jToggleButton1.setBackground(Color.RED);
                    this.jToggleButton1.setSelected(false);
                    this.jToggleButton1.setText("Conectar");
                }
            }
        }else{
            try {
                this.baseDatos.close();
                mensaje = "Desconectado de la base de datos.";
                this.jToggleButton1.setBackground(this.defaulColor);
                this.jToggleButton1.setText("Conectar");
            } catch (Exception ex) {
                //Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = "ERROR: \n" + this.baseDatos.getURL();
                mensaje += "\n "+ex.getCause();
                this.baseDatos=null;
            }finally{
                if(this.baseDatos==null){
                    this.jToggleButton1.setBackground(Color.RED);
                    this.jToggleButton1.setSelected(false);
                }
            }
        }
        
        this.jTextArea1.setText(mensaje);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogConfigurarBaseDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogConfigurarBaseDatos dialog = new DialogConfigurarBaseDatos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
    

    //Getter and Setters.
    public DBScheme getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(DBScheme baseDatos) {
        this.baseDatos = baseDatos;
    }
    
    
}
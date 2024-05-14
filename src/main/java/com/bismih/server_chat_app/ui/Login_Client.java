/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bismih.server_chat_app.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.bismih.server_chat_app.components.Request;
import com.bismih.server_chat_app.constants.s;
import com.bismih.server_chat_app.network_.Client;
import com.bismih.server_chat_app.utils.JsonProcessor;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;

/**
 *
 * @author bismih
 */
public class Login_Client extends javax.swing.JFrame {

    private static Client client;
    private static JFrame frame;

    public Login_Client() {
        initComponents();
        client = new Client(Login_Client::sign_in);
        client.start_client();

    }

    private static void sign_in(String msg) {
        System.out.println("sign_in: " + msg);
        Request request = Request.getRequest(msg);
        System.out.println("sign_in: " + request.getCode() + " " + request.getResult());
        try {
            if (request.getCode().equals(s.SIGN_IN)) {
                if (!request.getResult().equals("-1")) {
                    JFrame mainApp = new 
                        MainApp(Integer.parseInt(request.getResult()));
                    mainApp.setVisible(true);
                    client.disconnect();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: User name or password incorrect!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tx_user_name = new com.bismih.server_chat_app.view.TextField.TextFeild1();
        jLabel1 = new javax.swing.JLabel();
        tx_password = new com.bismih.server_chat_app.view.TextField.TextFeild1();
        jLabel2 = new javax.swing.JLabel();
        bt_sign_up = new com.bismih.server_chat_app.view.buttons.ButtonN();
        bt_sign_in = new com.bismih.server_chat_app.view.buttons.ButtonN();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");

        tx_user_name.setText("u5");

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 17)); // NOI18N
        jLabel1.setText("User Name:");

        tx_password.setText("u5");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 17)); // NOI18N
        jLabel2.setText("Password:");

        bt_sign_up.setText("Sign Up");
        bt_sign_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_sign_upActionPerformed(evt);
            }
        });

        bt_sign_in.setText("Sign In");
        bt_sign_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_sign_inActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(215, 215, 215)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(tx_password,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 278,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(tx_user_name,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 278,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(296, 296, 296)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(bt_sign_up,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 107,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(bt_sign_in,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 107,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(249, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel1)
                                .addGap(27, 27, 27)
                                .addComponent(tx_user_name, javax.swing.GroupLayout.PREFERRED_SIZE, 47,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel2)
                                .addGap(27, 27, 27)
                                .addComponent(tx_password, javax.swing.GroupLayout.PREFERRED_SIZE, 47,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(bt_sign_in, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bt_sign_up, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(34, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_sign_upActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bt_sign_upActionPerformed
        JFrame f = new Sing_Up_Client();
        f.setVisible(true);
        client.disconnect();
        frame.dispose();
    }// GEN-LAST:event_bt_sign_upActionPerformed

    private void bt_sign_inActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bt_sign_inActionPerformed
        client.send(JsonProcessor.sing_in(tx_user_name.getText(),
                tx_password.getText()));
    }// GEN-LAST:event_bt_sign_inActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login_Client.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login_Client.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login_Client.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_Client.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FlatMaterialDeepOceanIJTheme.setup();
                frame = new Login_Client();
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.bismih.server_chat_app.view.buttons.ButtonN bt_sign_in;
    private com.bismih.server_chat_app.view.buttons.ButtonN bt_sign_up;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.bismih.server_chat_app.view.TextField.TextFeild1 tx_password;
    private com.bismih.server_chat_app.view.TextField.TextFeild1 tx_user_name;
    // End of variables declaration//GEN-END:variables
}

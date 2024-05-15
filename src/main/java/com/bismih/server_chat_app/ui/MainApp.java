/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bismih.server_chat_app.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import com.bismih.server_chat_app.components.Messages;
import com.bismih.server_chat_app.components.Project;
import com.bismih.server_chat_app.components.Request;
import com.bismih.server_chat_app.components.User;
import com.bismih.server_chat_app.constants.s;
import com.bismih.server_chat_app.network_.Client;
import com.bismih.server_chat_app.utils.JsonProcessor;
import com.bismih.server_chat_app.view.buttons.ButtonN;

/**
 *
 * @author bismih
 */
public class MainApp extends javax.swing.JFrame {

    private static JFrame frame;
    private static Client client;
    private static int user_id;
    private static JPanel pnl_elements_s;
    private static JPanel pnl_messages_s;
    private static int project_id_global;
    private boolean is_in_project = true;

    enum State {
        PROJECT, MSG
    };

    public MainApp(int id) {
        initComponents();

        pnl_elements_s = pnl_elements;
        pnl_messages_s = pnl_msg;
        lb_id.setText("id: " + id);
        user_id = id;
        frame = this;

        configuration();
    }

    private static void configuration() {
        client = new Client(MainApp::communication);
        client.start_client();
        client.send(JsonProcessor.get_project(user_id));

    }

    private static void communication(String msg) {
        System.out.println("MainApp communication: 60\n" + msg);
        Request request = Request.getRequest(msg);

        if (request.getCode().equals(s.GET_PROJECT)) {
            pnl_elements_s.removeAll();
            Project.getProjects(request.getResult()).forEach(project -> {
                System.out.println(project.getName() + " " + project.getProject_id());
                ButtonN btn = ButtonN.getBtn(project.getName() + " " + project.getProject_id());
                // butonlara tıklanınca mesajları getirme
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        client.send(JsonProcessor.get_msg(project.getProject_id(), -1, user_id));
                        client.send(JsonProcessor.get_users(project.getProject_id()));
                        System.out.println("mesajlar temizlendi");
                        project_id_global = project.getProject_id();
                    }
                });
                pnl_elements_s.add(btn);
            });

        // kullanıcıların getirilmesi
        } else if (request.getCode().equals(s.GET_USERS)) {
            pnl_elements_s.removeAll();
            // TODO: kullanıcıların bilgilerine erişilmesi gerekebilir
            User.get_users(request.getResult()).forEach(user -> {
                System.out.println(user.getName() + " " + user.getUser_name());
                ButtonN btn = ButtonN.getBtn(user.getName() + " " + user.getUser_name());
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        client.send(JsonProcessor.get_msg(project_id_global, user.getId(), user_id));
                        System.out.println("mesajlar temizlendi");
                    }

                });
                pnl_elements_s.add(btn);
            });
        }

        else if (request.getCode().equals(s.GET_MSG)) {
            pnl_messages_s.removeAll();
            frame.revalidate();
            frame.repaint();
            Messages.getMessages(request.getResult(), project_id_global).forEach(msg_ -> {
                System.out.println(msg_.getMsg() + " " + msg_.getSender_id() + " " + msg_.getReceiver_id());
                pnl_messages_s.add(new JLabel(msg_.getMsg() + " " + msg_.getSender_id()
                        + " " + msg_.getReceiver_id() + " " + msg_.getProject_id() + " " + msg_.getType()));
            });
        }
        frame.revalidate();
        frame.repaint();
    }

    private static void fill_elements(JSONArray jArr) {
        ButtonN btn;
        String project_name;
        for (int i = 0; i < jArr.length(); i++) {
            project_name = jArr.getJSONObject(i).getString(s.PROJECT_NAME);
            int project_id = jArr.getJSONObject(i).getInt(s.PROJECT_ID);

            // projeler için buton ekleme
            btn = new ButtonN();
            btn.setText(project_name + " " + project_id);
            System.out.println(project_name + " " + project_id);

            // butonlara tıklanınca mesajları getirme
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    client.send(JsonProcessor.get_msg(project_id, -1, user_id));
                    client.send(JsonProcessor.get_users(project_id));
                    System.out.println("mesajlar temizlendi");
                    project_id_global = project_id;
                }

            });
            pnl_elements_s.add(btn);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_messages = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pnl_msg = new javax.swing.JPanel();
        pnl_elements_ = new javax.swing.JPanel();
        pnl_top_id_back = new javax.swing.JPanel();
        btn_back = new com.bismih.server_chat_app.view.buttons.ButtonN();
        lb_id = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnl_elements = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main App");
        setBackground(new java.awt.Color(204, 255, 204));

        pnl_messages.setBackground(new java.awt.Color(153, 255, 153));
        pnl_messages.setPreferredSize(new java.awt.Dimension(300, 426));

        pnl_msg.setLayout(new javax.swing.BoxLayout(pnl_msg, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(pnl_msg);

        javax.swing.GroupLayout pnl_messagesLayout = new javax.swing.GroupLayout(pnl_messages);
        pnl_messages.setLayout(pnl_messagesLayout);
        pnl_messagesLayout.setHorizontalGroup(
                pnl_messagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_messagesLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane2)
                                .addGap(0, 0, 0)));
        pnl_messagesLayout.setVerticalGroup(
                pnl_messagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_messagesLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane2)
                                .addGap(0, 0, 0)));

        pnl_elements_.setBackground(new java.awt.Color(255, 255, 153));
        pnl_elements_.setPreferredSize(new java.awt.Dimension(150, 450));

        btn_back.setText("<");
        btn_back.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        lb_id.setText("id: ");

        javax.swing.GroupLayout pnl_top_id_backLayout = new javax.swing.GroupLayout(pnl_top_id_back);
        pnl_top_id_back.setLayout(pnl_top_id_backLayout);
        pnl_top_id_backLayout.setHorizontalGroup(
                pnl_top_id_backLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_top_id_backLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(lb_id, javax.swing.GroupLayout.PREFERRED_SIZE, 56,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)));
        pnl_top_id_backLayout.setVerticalGroup(
                pnl_top_id_backLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_top_id_backLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnl_top_id_backLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lb_id)));

        pnl_elements.setLayout(new javax.swing.BoxLayout(pnl_elements, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(pnl_elements);

        javax.swing.GroupLayout pnl_elements_Layout = new javax.swing.GroupLayout(pnl_elements_);
        pnl_elements_.setLayout(pnl_elements_Layout);
        pnl_elements_Layout.setHorizontalGroup(
                pnl_elements_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_top_id_back, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_elements_Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane1)));
        pnl_elements_Layout.setVerticalGroup(
                pnl_elements_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_elements_Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(pnl_top_id_back, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                                .addGap(2, 2, 2)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pnl_elements_, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(pnl_messages, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                                .addGap(0, 0, 0)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_elements_, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                        .addComponent(pnl_messages, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_backActionPerformed
        client.send(JsonProcessor.get_project(user_id));
    }// GEN-LAST:event_btn_backActionPerformed

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
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame = new MainApp(3);
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.bismih.server_chat_app.view.buttons.ButtonN btn_back;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_id;
    private javax.swing.JPanel pnl_elements;
    private javax.swing.JPanel pnl_elements_;
    private javax.swing.JPanel pnl_messages;
    private javax.swing.JPanel pnl_msg;
    private javax.swing.JPanel pnl_top_id_back;
    // End of variables declaration//GEN-END:variables
}

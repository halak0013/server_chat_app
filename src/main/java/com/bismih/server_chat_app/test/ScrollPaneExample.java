package com.bismih.server_chat_app.test;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneExample {
    public static void main(String[] args) {
        // Ana frame oluştur
        JFrame frame = new JFrame("JScrollPane Örneği");

        // ScrollPane oluştur
        JScrollPane scrollPane = new JScrollPane();

        // ScrollPane içine eklenecek paneli oluştur
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // ScrollPane'a eklenecek olan label'ları oluştur
        for (int i = 1; i <= 20; i++) {
            JLabel label = new JLabel("Label " + i);
            panel.add(label);
        }

        // Paneli ScrollPane'e ekle
        scrollPane.setViewportView(panel);

        // Frame'i yapılandır
        frame.add(scrollPane);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


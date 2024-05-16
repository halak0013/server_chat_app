package com.bismih.server_chat_app.network_;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

import com.bismih.server_chat_app.constants.Constants;
import com.bismih.server_chat_app.utils.JsonProcessor;

public class Client {
    // listen içine bir tane runnable func alcak
    // gelen veriye göre direk arayüz fonksiyonunun parametresi verilip
    // arayüz fonksiyonu çalıştırılacak

    private Socket socket;
    private DataInputStream sInput;
    private DataOutputStream sOutput;
    private String server;
    private int port;
    private boolean client_status = false;

    // Runnable ui_worker;
    Consumer<String> ui_worker;

    public Client(Consumer<String> ui_worker) {
        this.ui_worker = ui_worker;
        this.server = Constants.server_ip;
        this.port = Constants.server_port;
    }

    public boolean start_client() {
        try {
            socket = new Socket(server, port);
            sInput = new DataInputStream(socket.getInputStream());
            sOutput = new DataOutputStream(socket.getOutputStream());
            listen();
            client_status = true;
        } catch (Exception e) {
            System.out.println("Error connecting to server:" + e);
            return false;
        }
        System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
        return true;
    }

    public void listen() {
        Thread listenThread = new Thread(() -> {
            while (client_status) {
                try {
                    String msg = sInput.readUTF();
                    System.out.println("client listen: " + msg);
                    ui_worker.accept(msg);
                } catch (Exception e) {
                    System.out.println("Error listening to server:" + e);
                    e.printStackTrace();
                    break;
                }
            }
        });
        listenThread.start();
    }

    public void send_msg(String msg) {
        try {
            System.out.println("client send: " + msg);
            sOutput.writeUTF(msg);
        } catch (Exception e) {
            System.out.println("Error sending to server:" + e);
            e.printStackTrace();
        }
    }

    // https://www.geeksforgeeks.org/transfer-the-file-client-socket-to-server-socket-in-java/
    private static void receiveFile(String fileName, DataInputStream sInput)
            throws Exception {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = sInput.readLong(); // read file size
        byte[] buffer = new byte[4 * 1024];
        while (size > 0
                && (bytes = sInput.read(
                        buffer, 0,
                        (int) Math.min(buffer.length, size))) != -1) {
            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read upto file size
        }
        // Here we received file
        System.out.println("File is Received");
        fileOutputStream.close();
    }

    private static void sendFile(String path, DataOutputStream sOutput)
            throws Exception {
        int bytes = 0;
        // Open the File where he located in your pc
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        // Here we send the File to Server
        sOutput.writeLong(file.length());
        // Here we break file into chunks
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            // Send the file to Server Socket
            sOutput.write(buffer, 0, bytes);
            sOutput.flush();
        }
        // close the file here
        fileInputStream.close();
    }

    public void disconnect() {
        try {
            this.send_msg(JsonProcessor.exit());
            if (sInput != null)
                sInput.close();
            if (sOutput != null)
                sOutput.close();
            if (socket != null)
                socket.close();
            client_status = false;
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}

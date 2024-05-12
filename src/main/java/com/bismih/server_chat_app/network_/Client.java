package com.bismih.server_chat_app.network_;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

import com.bismih.server_chat_app.constants.Constants;

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
        Thread listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (client_status) {
                    try {
                        String msg = sInput.readUTF();
                        System.out.println("client listen: " + msg);
                        // ! buraya dikkat
                        // TODO: burada gelen mesajı arayüz fonksiyonuna parametre olarak ver
                        ui_worker.accept(msg);
                    } catch (Exception e) {
                        System.out.println("Error listening to server:" + e);
                        break;
                    }
                }
            }
        });
        listenThread.start();
    }

    public void send(String msg) {
        try {
            System.out.println("client send: " + msg);
            sOutput.writeUTF(msg);
        } catch (Exception e) {
            System.out.println("Error sending to server:" + e);
        }
    }

    public void disconnect() {
        try {
            this.send("**exit**");
            if (sInput != null)
                sInput.close();
            if (sOutput != null)
                sOutput.close();
            if (socket != null)
                socket.close();
            client_status = false;
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

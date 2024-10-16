package com.bismih.server_chat_app.network_;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientNodeFile {
    public Socket socket;
    public DataInputStream sInput;
    public DataOutputStream sOutput;
    public boolean client_status = true;
    public int user_id;

    public ClientNodeFile(Socket socket) {
        this.socket = socket;
        try {
            sInput = new DataInputStream(socket.getInputStream());
            sOutput = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}

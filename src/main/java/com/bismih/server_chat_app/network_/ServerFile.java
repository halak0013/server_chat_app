package com.bismih.server_chat_app.network_;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;

import com.bismih.server_chat_app.components.Request;
import com.bismih.server_chat_app.utils.JsonProcessor;

public class ServerFile {
    private ServerSocket serverSocket;
    private final int PORT = 14571;
    private Socket clientSocket;

    private ArrayList<ClientNode> clients = new ArrayList<ClientNode>();

    public boolean server_status = false;

    public boolean start_server() {
        try {
            serverSocket = new ServerSocket(PORT);
            server_status = true;
            System.out.println("Server started on port " + PORT);
            Thread t_accept = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (server_status) {
                        accept_client();
                    }
                }
            });
            t_accept.start();

            return true;
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }

    public void accept_client() {
        try {
            clientSocket = serverSocket.accept();
            ClientNode client = new ClientNode(clientSocket);
            clients.add(client);
            Thread t_listen = new Thread(() -> listen(client));
            t_listen.start();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public void listen(ClientNode client) {
        try {
            String msg;
            while (client.client_status) {
                msg = client.sInput.readUTF();
                if (msg.contains("set_id")) {
                    Request result = JsonProcessor.parse(msg);
                    client.user_id = Integer.parseInt(result.getResult());
                    continue;
                } else if (msg.contains("exit")) {
                    client.client_status = false;
                    clients.remove(client);
                    break;
                }
                JSONObject json = new JSONObject(msg);
                String file_name = json.getString("file_name");
                int receiver_id = json.getInt("receiver_id");
                int project_id = json.getInt("project_id");
                receiveFile(file_name, client.sInput);
                send_messages_to_users(receiver_id, project_id, file_name);

            }
        } catch (Exception e) {
            client.client_status = false;

            clients.remove(client);
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private void send_messages_to_users(int receiver_id, int project_id, String file_name) {
        if (receiver_id == -1) {
            ArrayList<Integer> user_list = JsonProcessor.get_users_id(project_id);
            for (int i = 0; i < clients.size(); i++) {
                int user_id = clients.get(i).user_id;
                if (user_list.contains(user_id)) {
                    send_msg(file_name, clients.get(i));
                    sendFile(file_name, clients.get(i).sOutput);
                }
            }
        } else {
            for (int i = 0; i < clients.size(); i++) {
                int user_id = clients.get(i).user_id;
                if (user_id == receiver_id) {
                    send_msg(file_name, clients.get(i));
                    sendFile(file_name, clients.get(i).sOutput);
                    break;
                }
            }
        }

        
    }

    public void send_msg(String msg, ClientNode client) {
        try {
            client.sOutput.writeUTF(msg);
            System.out.println("server send: " + msg);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    // https://www.geeksforgeeks.org/transfer-the-file-client-socket-to-server-socket-in-java/
    private void receiveFile(String fileName, DataInputStream sInput)
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

    private void sendFile(String path, DataOutputStream sOutput) {
        int bytes = 0;
        // Open the File where he located in your pc
        File file = new File(path);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerFile server = new ServerFile();
        server.start_server();

    }

}

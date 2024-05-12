package com.bismih.server_chat_app.network_;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.bismih.server_chat_app.utils.JsonProcessor;

public class Server {
    private ServerSocket serverSocket;
    private final int PORT = 57145;
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
            return false;
        }
    }

    public void accept_client() {
        try {
            clientSocket = serverSocket.accept();
            ClientNode client = new ClientNode(clientSocket);
            clients.add(client);
            Thread t_listen = new Thread(new Runnable() {
                @Override
                public void run() {
                    listen(client);
                }
            });
            t_listen.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void listen(ClientNode client) {
        try {
            String msg;
            String result;
            while (client.client_status) {
                msg = client.sInput.readUTF();
                if (msg.equals("exit")) {
                    client.client_status = false;
                    client.socket.close();
                    clients.remove(client);
                    break;//!
                }
                result = JsonProcessor.parse(msg);
                send_msg(result, client);
                System.out.println("server listen: "+msg);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void send_msg(String msg, ClientNode client) {
        try {
            client.sOutput.writeUTF(msg);
            System.out.println("server send: "+msg);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start_server();

    }

}

package com.bismih.server_chat_app.network_;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

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
                    break;// !
                }
                result = JsonProcessor.parse(msg);
                if (JsonProcessor.is_send_msg(result))
                    send_messages_to_users(msg, client, result);
                else
                    send_msg(result, client);
                System.out.println("server listen: "+msg);
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private void send_messages_to_users(String msg, ClientNode client, String result) {
        int receiver = JsonProcessor.get_receiver(msg);
        System.out.println("msg: " + msg + " result: " + result);
        if (receiver == -1) {
            Queue<Integer> queue = JsonProcessor.get_users(msg);
            for (int i = 0; i < clients.size(); i++) {
                if (queue.contains(clients.get(i).user_id)) {
                    send_msg(result, clients.get(i));
                }
            }
        } else {
            send_msg(result, client);
        }
        System.out.println("server listen aranan nokta: " + msg + " result: " + result);
    }

    public void send_msg(String msg, ClientNode client) {
        try {
            client.sOutput.writeUTF(msg);
            System.out.println("server send: " + msg);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start_server();

    }

}

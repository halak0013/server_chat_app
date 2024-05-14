package com.bismih.server_chat_app.network_;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

import com.bismih.server_chat_app.components.Request;
import com.bismih.server_chat_app.constants.s;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void listen(ClientNode client) {
        try {
            String msg;
            Request result;
            while (client.client_status) {
                msg = client.sInput.readUTF();
                System.out.println("server listen: 67" + msg);
                result = JsonProcessor.parse(msg);
                if (result.getCode().equals("exit")) {
                    client.client_status = false;
                    client.socket.close();
                    clients.remove(client);
                    break;// !
                }
                if (result.getCode().equals(s.SEND_MSG))
                    send_messages_to_users(client, result, msg);
                else
                    send_msg(Request.getJsonRequest(result), client);
                System.out.println("server listen: " + msg);
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private void send_messages_to_users(ClientNode client, Request result, String msg) {
        int receiver = JsonProcessor.get_receiver(msg);
        System.out.println("msg: " + msg + " result: " + result);
        if (receiver == -1) {
            Queue<Integer> queue = JsonProcessor.get_users(msg);
            for (int i = 0; i < clients.size(); i++) {
                if (queue.contains(clients.get(i).user_id)) {
                    send_msg(Request.getJsonRequest(result), clients.get(i));
                }
            }
        } else {
            send_msg(Request.getJsonRequest(result), client);
        }
        System.out.println("server listen aranan nokta: " + msg + " result: " + result);
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

    public static void main(String[] args) {
        Server server = new Server();
        server.start_server();

    }

}

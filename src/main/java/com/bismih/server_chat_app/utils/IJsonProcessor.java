package com.bismih.server_chat_app.utils;

public interface IJsonProcessor {
    static String sing_in(String user_name, String password) {
        return "null";
    }

    static String sing_up(String name, String user_name, String password) {
        return "null";
    }

    static String get_msg(int project_id, int receiver_id, int sender_id) {
        return "null";
    }

    static String send_msg(String msg, String type, int receiver_id, int sender_id, int project_id) {
        return "null";
    }

    static String get_project(int user_id) {
        return "null";
    }

    static String add_project(String project_name, int admin, String project_link) {
        return "null";
    }
}

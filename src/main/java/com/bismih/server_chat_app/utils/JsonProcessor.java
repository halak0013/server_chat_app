package com.bismih.server_chat_app.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bismih.server_chat_app.components.Request;
import com.bismih.server_chat_app.constants.s;

public class JsonProcessor {

    // ?Client send to server part
    public static String sing_in(String user_name, String password) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.SIGN_IN);
        jObj.put(s.USER_NAME, user_name);
        jObj.put("password", password);
        return jObj.toString();
    }

    public static String sing_up(String name, String user_name, String password) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.SIGN_UP);
        jObj.put(s.PROJECT_NAME, name);
        jObj.put(s.USER_NAME, user_name);
        jObj.put("password", password);
        return jObj.toString();
    }

    public static String send_msg(String msg, String type, int receiver_id, int sender_id, int project_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.SEND_MSG);
        jObj.put("msg", msg);
        jObj.put("type", type);
        jObj.put(s.RECEIVER_ID, receiver_id);
        jObj.put(s.SENDER_ID, sender_id);
        jObj.put(s.PROJECT_ID, project_id);
        return jObj.toString();
    }

    public static String get_msg(int project_id, int receiver_id, int sender_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.GET_MSG);
        jObj.put(s.PROJECT_ID, project_id);
        jObj.put(s.RECEIVER_ID, receiver_id);
        jObj.put(s.SENDER_ID, sender_id);
        return jObj.toString();
    }

    public static String get_users(int project_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.GET_USERS);
        jObj.put(s.PROJECT_ID, project_id);
        return jObj.toString();
    }

    public static String get_project(int user_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.GET_PROJECT);
        jObj.put(s.USER_ID, user_id);
        return jObj.toString();
    }

    public static String add_project(String project_name, int admin, String project_link) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.ADD_PROJECT);
        jObj.put(s.PROJECT_NAME, project_name);
        jObj.put("admin", admin);
        jObj.put("project_link", project_link);
        return jObj.toString();
    }

    public static String join_project(int user_id, int project_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.JOIN_PROJECT);
        jObj.put(s.USER_ID, user_id);
        jObj.put(s.PROJECT_ID, project_id);
        return jObj.toString();
    }

    public static String set_id(int user_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "set_id");
        jObj.put("user_id", user_id);
        return jObj.toString();
    }

    public static String get_project_id(String link) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "get_project_id");
        jObj.put("link", link);
        return jObj.toString();
    }

    public static String get_project_link(int user_id, int project_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", s.GET_PROJECT_LINK);
        jObj.put(s.USER_ID, user_id);
        jObj.put(s.PROJECT_ID, project_id);
        return jObj.toString();
    }

    public static String exit() {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "exit");
        jObj.put("result", "exit");
        return jObj.toString();
    }

    // ?Server parse and send to client part
    public static Request parse(String json) {
        try {
            System.out.println("parse: " + json + "*");
            JSONObject jObj = new JSONObject(json);
            String code = jObj.getString("code");
            Request request = null;
            switch (code) {
                case s.SIGN_IN:
                    String user_name = jObj.getString(s.USER_NAME);
                    String password = jObj.getString("password");
                    request = new Request(s.SIGN_IN, Db_process.userValidation(user_name, password));
                    break;
                case s.SIGN_UP:
                    String name = jObj.getString(s.PROJECT_NAME);
                    user_name = jObj.getString(s.USER_NAME);
                    password = jObj.getString("password");
                    request = new Request(s.SIGN_UP, Db_process.add_user(name, user_name, password));
                    break;
                case s.GET_MSG:
                    int project_id = jObj.getInt(s.PROJECT_ID);
                    int receiver_id = jObj.getInt(s.RECEIVER_ID);
                    int sender_id = jObj.getInt(s.SENDER_ID);
                    request = new Request(s.GET_MSG, Db_process.getMsgs(project_id, receiver_id, sender_id));
                    break;
                case s.GET_USERS:
                    project_id = jObj.getInt(s.PROJECT_ID);
                    request = new Request(s.GET_USERS, Db_process.getUsers(project_id));
                    break;
                case s.SEND_MSG:
                    String msg = jObj.getString("msg");
                    String type = jObj.getString("type");
                    receiver_id = jObj.getInt(s.RECEIVER_ID);
                    sender_id = jObj.getInt(s.SENDER_ID);
                    project_id = jObj.getInt(s.PROJECT_ID);
                    request = new Request(s.SEND_MSG, Db_process.addMsg(msg, type, receiver_id, sender_id, project_id));
                    break;
                case s.GET_PROJECT:
                    int user_id = jObj.getInt(s.USER_ID);
                    request = new Request(s.GET_PROJECT, Db_process.getProjects(user_id));
                    break;
                case s.ADD_PROJECT:
                    String project_name = jObj.getString(s.PROJECT_NAME);
                    int admin = jObj.getInt("admin");
                    String project_link = jObj.getString("project_link");
                    request = new Request(s.ADD_PROJECT, Db_process.add_project(project_name, admin, project_link));
                    break;
                case s.JOIN_PROJECT:
                    user_id = jObj.getInt(s.USER_ID);
                    project_id = jObj.getInt(s.PROJECT_ID);
                    request = new Request(s.JOIN_PROJECT, Db_process.add_user_project_relation(user_id, project_id));
                    break;
                case "get_project_id":
                    String link = jObj.getString("link");
                    request = new Request("get_project_id", Db_process.get_project_id_form_link(link) + "");
                    break;
                case "set_id":
                    user_id = jObj.getInt("user_id");
                    request = new Request("set_id", user_id + "");
                    break;
                case s.GET_PROJECT_LINK:
                    project_id = jObj.getInt(s.PROJECT_ID);
                    user_id = jObj.getInt(s.USER_ID);
                    request = new Request(s.GET_PROJECT_LINK, Db_process.get_project_link(user_id, project_id));
                    break;
                case "online_check":
                    request = new Request("online_check", jObj.getString("result"));
                    break;
                case "exit":
                    request = new Request("exit", "exit");
                    break;
                default:
                    System.out.println("parse: ");
            }
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateRequest(String code, String result) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", code);
        jObj.put("result", result);
        return jObj.toString();
    }

    public static ArrayList<Integer> get_users(String json) {
        System.out.println("get_users: json procces " + json);
        ArrayList<Integer> result = new ArrayList<>();
        JSONObject jObj = new JSONObject(json);
        int project_id = jObj.getInt(s.PROJECT_ID);
        JSONArray jArr = new JSONArray(Db_process.getUsers(project_id));

        for (int i = 0; i < jArr.length(); i++) {
            JSONObject jObj2 = jArr.getJSONObject(i);
            int user_id = jObj2.getInt(s.USER_ID);
            result.add(user_id);
        }
        return result;
    }

    public static ArrayList<Integer> get_users_id(int project_id) {
        ArrayList<Integer> result = new ArrayList<>();
        JSONArray jArr = new JSONArray(Db_process.getUsers(project_id));

        for (int i = 0; i < jArr.length(); i++) {
            JSONObject jObj2 = jArr.getJSONObject(i);
            int user_id = jObj2.getInt(s.USER_ID);
            result.add(user_id);
        }
        return result;
    }

    public static int get_receiver(String json) {
        System.out.println("get_receiver: " + json);
        JSONObject jObj = new JSONObject(json);
        return jObj.getInt(s.RECEIVER_ID);
    }

    public static Request getRequest(String json) {
        JSONObject jObj = new JSONObject(json);
        return new Request(jObj.getString("code"), jObj.getString("result"));
    }

    public static String online_check(String string) {
        Request request = new Request("online_check", string);
        return request.toString();
    }

}

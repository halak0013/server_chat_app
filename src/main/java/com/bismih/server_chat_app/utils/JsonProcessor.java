package com.bismih.server_chat_app.utils;

import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonProcessor{

    //?Client send to server part
    public static String sing_in(String user_name, String password) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "sign_in");
        jObj.put("user_name", user_name);
        jObj.put("password", password);
        return jObj.toString();
    }


    public static String sing_up(String name, String user_name, String password) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "sign_up");
        jObj.put("name", name);
        jObj.put("user_name", user_name);
        jObj.put("password", password);
        return jObj.toString();
    }
    
    public static String send_msg(String msg, String type, int receiver_id, int sender_id, int project_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "send_msg");
        jObj.put("msg", msg);
        jObj.put("type", type);
        jObj.put("receiver_id", receiver_id);
        jObj.put("sender_id", sender_id);
        jObj.put("project_id", project_id);
        return jObj.toString();
    }
    
    public static String get_msg(int project_id, int receiver_id, int sender_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "get_msg");
        jObj.put("project_id", project_id);
        jObj.put("receiver_id", receiver_id);
        jObj.put("sender_id", sender_id);
        return jObj.toString();
    }

    public static String get_project(int user_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "get_project");
        jObj.put("user_id", user_id);
        return jObj.toString();
    }

    public static String add_project(String project_name, int admin, String project_link) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "add_project");
        jObj.put("project_name", project_name);
        jObj.put("admin", admin);
        jObj.put("project_link", project_link);
        return jObj.toString();
    }

    public static String join_project(int user_id, int project_id) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", "join_project");
        jObj.put("user_id", user_id);
        jObj.put("project_id", project_id);
        return jObj.toString();
    }


    //?Server parse and send to client part
    public static String parse(String json){
        JSONObject jObj = new JSONObject(json);
        String code = jObj.getString("code");
        switch (code) {
            case "sign_in":
                String user_name = jObj.getString("user_name");
                String password = jObj.getString("password");
                JSONObject j = new JSONObject();
                j.put("code", "sign_in");
                j.put("result", Db_process.userValidation(user_name, password));
                return j.toString();

            case "sign_up":
                String name = jObj.getString("name");
                user_name = jObj.getString("user_name");
                password = jObj.getString("password");
                j = new JSONObject();
                j.put("code", "sign_up");
                j.put("result", Db_process.add_user(name, user_name, password));
                return j.toString();
            case "get_msg":
                int project_id = jObj.getInt("project_id");
                int receiver_id = jObj.getInt("receiver_id");
                int sender_id = jObj.getInt("sender_id");
                j = new JSONObject();
                j.put("code", "get_msg");
                j.put("result", Db_process.getMsgs(project_id, receiver_id, sender_id));
                return j.toString();
            case "send_msg":
                String msg = jObj.getString("msg");
                String type = jObj.getString("type");
                receiver_id = jObj.getInt("receiver_id");
                sender_id = jObj.getInt("sender_id");
                project_id = jObj.getInt("project_id");
                j = new JSONObject();
                j.put("code", "send_msg");
                j.put("result", Db_process.addMsg(msg, type, receiver_id, sender_id, project_id));
                return j.toString();
            case "get_project":
                int user_id = jObj.getInt("user_id");
                j = new JSONObject();
                j.put("code", "get_project");
                j.put("result", Db_process.getProjects(user_id));
                return j.toString();
            case "add_project":
                String project_name = jObj.getString("project_name");
                int admin = jObj.getInt("admin");
                String project_link = jObj.getString("project_link");
                j = new JSONObject();
                j.put("code", "add_project");
                j.put("result", Db_process.add_project(project_name, admin, project_link));
                return j.toString();
            case "join_project":
                user_id = jObj.getInt("user_id");
                project_id = jObj.getInt("project_id");
                j = new JSONObject();
                j.put("code", "join_project");
                j.put("result", Db_process.add_user_project_relation(user_id, project_id));
                return j.toString();
            default:
                return "null";
        }
    }

    public static Queue<Integer> get_users(String json){
        System.out.println("get_users: "+json);
        Queue<Integer> result = new LinkedList<>();
        JSONObject jObj = new JSONObject(json);
        int project_id = jObj.getInt("project_id");
        JSONArray jArr = new JSONArray(Db_process.getUsers(project_id));
        
        for (int i = 0; i < jArr.length(); i++) {
            JSONObject jObj2 = jArr.getJSONObject(i);
            int user_id = jObj2.getInt("user_id");
            result.add(user_id);
        }
        return result;
    }

    public static int get_receiver(String json){
        System.out.println("get_receiver: "+json);
        JSONObject jObj = new JSONObject(json);
        return jObj.getInt("receiver_id");
    }

    public static boolean is_send_msg(String json){
        JSONObject jObj = new JSONObject(json);
        return jObj.getString("code").equals("send_msg");
    }


}

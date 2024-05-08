package com.bismih.server_chat_app.utils;

import java.sql.ResultSet;

import org.json.JSONObject;

public class Db_proccess {
    private static Db_helper db_helper = new Db_helper();

    public static void add_user(String name, String user_name, String password) {
        db_helper.start_connection();
        db_helper.add_row("user", new String[] { "name", "user_name", "password" },
                new String[] { name, user_name, password });
        db_helper.closeConnection();
    }

    public static void add_project(String project_name, int admin, String project_link) {
        db_helper.start_connection();
        db_helper.add_row("projects", new String[] { "name", "admin", "link" },
                new String[] { project_name, Integer.toString(admin), project_link });
        int project_id = db_helper.get_last_id("projects");
        db_helper.closeConnection();
        Db_proccess.add_user_project_relation(admin, project_id);
        db_helper.closeConnection();
    }

    public static void add_user_project_relation(int user_id, int project_id) {
        db_helper.start_connection();
        db_helper.add_row("user_project", new String[] { "user_id", "project_id" },
                new String[] { Integer.toString(user_id), Integer.toString(project_id) });
        db_helper.closeConnection();
    }

    public static String convert_json(String msg, String type) {
        // String jsonStr = "{\"type\":\""+type+"\",\"msg\":\""+msg+"\"}";
        JSONObject jObj = new JSONObject();
        jObj.put("type", type);
        jObj.put("msg", msg);
        return jObj.toString();
    }

    public static void addMsg(String msg, String type, int receiver_id, int sender_id, int project_id) {
        db_helper.start_connection();
        db_helper.add_row("messages", new String[] { "msg", "receiver_id", "sender_id", "project_id" },
                new String[][] { { convert_json(msg, type), "0" }, { Integer.toString(receiver_id), "1" },
                        { Integer.toString(sender_id), "1" },
                        { Integer.toString(project_id), "1" } });
        db_helper.closeConnection();
    }

    // select msg, receiver, sender from message where project = project_id and
    // ((receiver = receiver_id and sender = sender_id) or (receiver = sender_id and
    // sender = receiver_id)) order by id desc
    public static String getMsgs(int project_id, int receiver_id, int sender_id) {
        db_helper.start_connection();
        String query = "select id, msg, receiver_id, sender_id from messages where project_id = " + project_id
                + " and ((receiver_id = " + receiver_id + " and sender_id = " + sender_id + ") or (receiver_id = "
                + sender_id
                + " and sender_id = " + receiver_id + ")) order by id desc";
        System.out.println(query);
        ResultSet rs = db_helper.get_query(query);
        JSONObject jObj = new JSONObject();
        int i = 0;
        try {
            while (rs.next()) {
                jObj.put(Integer.toString(i), "{\"msg\":\"" + rs.getString("msg") + "\",\"receiver\":\""
                        + rs.getString("receiver_id") + "\",\"sender\":\"" + rs.getString("sender_id") + "\"}");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db_helper.closeConnection();
        return jObj.toString();
    }

    /// projelerin id ve ismini, user id ye göre getirir
    public static String getProjects(int user_id) {
        db_helper.start_connection();

        String query = "select project_id, name from user_project, projects where user_project.project_id=projects.id and user_project.user_id = "
                + user_id;
        ResultSet rs = db_helper.get_query(query);
        JSONObject jObj = new JSONObject();
        int i = 0;
        try {
            while (rs.next()) {
                jObj.put(Integer.toString(i), "{\"project_id\":\"" + rs.getString("project_id") +
                        "\", \"name\":\"" + rs.getString("name") + "\"}");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db_helper.closeConnection();
        return jObj.toString();
    }

    /// projenin id ve ismini, user id ye göre getirir
    public static String getUsers(int project_id) {
        db_helper.start_connection();
        String query = "select user_id from user_project, user project_id where user_project.user_id=user.id and user_project.user_id = "
                + project_id;
        ResultSet rs = db_helper.get_query(query);
        JSONObject jObj = new JSONObject();
        int i = 0;
        try {
            while (rs.next()) {
                jObj.put(Integer.toString(i), "{\"user_id\":\"" + rs.getString("user_id") +
                        "\", \"name\":\"" + rs.getString("name") + "\"}");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db_helper.closeConnection();
        return jObj.toString();
    }
}

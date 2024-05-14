package com.bismih.server_chat_app.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sqlite.SQLiteException;

import com.bismih.server_chat_app.constants.s;

public class Db_process {
    private static Db_helper db_helper = new Db_helper();

    public static Queue<Runnable> queue = new java.util.LinkedList<>();
    private static boolean queue_is_running = false;

    public static void add_to_queue(Runnable r) {
        queue.add(r);
        if (!queue_is_running) {
            run_queue();
        }
    }

    public static void run_queue() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!queue.isEmpty()) {
                    queue.poll().run();
                }
                queue_is_running = false;
            }
        });
        t.start();
    }

    public static String add_user(String name, String user_name, String password) {
        String result = "";
        db_helper.start_connection();
        try {
            db_helper.add_row("user", new String[] { s.PROJECT_NAME, s.USER_NAME, "password" },
                    new String[] { name, user_name, password });
            result = "success";
        } catch (SQLiteException e) {
            // e.printStackTrace();
            if (e.getErrorCode() == 19) {
                System.out.println("Row already exists");
                result = "same_user";
            }
            System.out.println(e.getMessage() + " " + e.getErrorCode());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            result = "error";
        } finally {
            db_helper.closeConnection();
        }
        return result;

    }

    public static String add_project(String project_name, int admin, String project_link) {

        String result = "";
        db_helper.start_connection();
        try {
            db_helper.add_row("projects", new String[] { s.PROJECT_NAME, "admin", "link" },
                    new String[] { project_name, Integer.toString(admin), project_link });
            result = "success";
        } catch (SQLException e) {
            e.printStackTrace();
            result = "error";
        }
        int project_id = db_helper.get_last_id("projects");
        db_helper.closeConnection();
        Db_process.add_user_project_relation(admin, project_id);
        db_helper.closeConnection();
        return result;
    }

    public static String add_user_project_relation(int user_id, int project_id) {
        String result = "";
        db_helper.start_connection();
        try {
            db_helper.add_row("user_project", new String[] { s.USER_ID, s.PROJECT_ID },
                    new String[] { Integer.toString(user_id), Integer.toString(project_id) });
            result = "success";
        } catch (SQLException e) {
            e.printStackTrace();
            result = "error";
        }
        db_helper.closeConnection();
        return result;
    }

    public static String convert_json(String msg, String type) {
        // String jsonStr = "{\"type\":\""+type+"\",\"msg\":\""+msg+"\"}";
        JSONObject jObj = new JSONObject();
        jObj.put("type", type);
        jObj.put("msg", msg);
        return jObj.toString();
    }

    public static String addMsg(String msg, String type, int receiver_id, int sender_id, int project_id) {
        db_helper.start_connection();
        String result = "";
        try {
            db_helper.add_row("messages", new String[] { "msg", s.RECEIVER_ID, s.SENDER_ID, s.PROJECT_ID },
                    new String[][] { { convert_json(msg, type), "0" }, { Integer.toString(receiver_id), "1" },
                            { Integer.toString(sender_id), "1" },
                            { Integer.toString(project_id), "1" } });
            result = "success";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            result = "error";
        }
        db_helper.closeConnection();
        return result;
    }

    // select msg, receiver, sender from message where project = project_id and
    // ((receiver = receiver_id and sender = sender_id) or (receiver = sender_id and
    // sender = receiver_id)) order by id desc
    public static String getMsgs(int project_id, int receiver_id, int sender_id) {
        db_helper.start_connection();
        String query = "select id, msg, receiver_id, sender_id from messages where project_id = " + project_id;
        if (receiver_id != -1)
            query +=" and ((receiver_id = " + receiver_id + " and sender_id = " + sender_id + ") or (receiver_id = "
                + sender_id + " and sender_id = " + receiver_id + ")) order by id desc";
        else
            query += " order by id desc";
        
        System.out.println(query);
        ResultSet rs = db_helper.get_query(query);
        JSONArray jArr = new JSONArray();
        JSONObject jTmp;
        int i = 0;
        String id, msg, receiver, sender;
        try {
            while (rs.next()) {
                jTmp = new JSONObject();
                // id = Integer.toString(i);
                msg = rs.getString("msg");
                receiver = rs.getString(s.RECEIVER_ID);
                sender = rs.getString(s.SENDER_ID);
                jTmp.put("msg", msg);
                jTmp.put(s.RECEIVER_ID, receiver);
                jTmp.put(s.SENDER_ID, sender);
                jArr.put(jTmp);
                // jObj.put(Integer.toString(i), "{\"msg\":\"" + rs.getString("msg") +
                // "\",\"receiver\":\""
                // + rs.getString(s.RECEIVER_ID) + "\",\"sender\":\"" +
                // rs.getString(s.SENDER_ID) + "\"}");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db_helper.closeConnection();
        return jArr.toString();
    }

    /// projelerin id ve ismini, user id ye göre getirir
    public static String getProjects(int user_id) {
        db_helper.start_connection();

        String query = "select project_id, project_name, admin, link from user_project, projects where user_project.project_id=projects.id and user_project.user_id = "
                + user_id;
        System.out.println(query);
        ResultSet rs = db_helper.get_query(query);

        // JSONObject jObj = new JSONObject();
        JSONObject jTmp;
        JSONArray jArr = new JSONArray();
        String project_id, project_name, link;
        int admin;
        try {
            while (rs.next()) {
                // id = Integer.toString(i);
                jTmp = new JSONObject();
                project_id = rs.getString(s.PROJECT_ID);
                project_name = rs.getString(s.PROJECT_NAME);
                link = rs.getString("link");
                admin = rs.getInt("admin");
                System.out.println("project_id: " + project_id + " name: " + project_name);
                jTmp.put(s.PROJECT_ID, project_id);
                jTmp.put(s.PROJECT_NAME, project_name);
                jTmp.put("link", link);
                jTmp.put("admin", admin);
                jArr.put(jTmp);
                // jObj.put(Integer.toString(i), "{\"project_id\":\"" +
                // rs.getString(s.PROJECT_ID) +
                // "\", \"name\":\"" + rs.getString(s.PROJECT_NAME) + "\"}");
                // i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db_helper.closeConnection();
        return jArr.toString();
    }

    /// projenin id ve ismini, user id ye göre getirir
    public static String getUsers(int project_id) {
        db_helper.start_connection();
        String query = "select user_id, name, user_name from user_project, user where user_project.user_id=user.id and user_project.project_id = "
                + project_id;
        ResultSet rs = db_helper.get_query(query);
        System.out.println(query);
        JSONObject jTmp;
        JSONArray jArr = new JSONArray();
        int i = 0;
        String id, user_id, name, user_name;
        try {
            while (rs.next()) {
                jTmp = new JSONObject();
                user_id = rs.getString(s.USER_ID);
                name = rs.getString(s.PROJECT_NAME);
                user_name = rs.getString(s.USER_NAME);
                jTmp.put(s.USER_ID, user_id);
                jTmp.put("name", name);
                jTmp.put(s.USER_NAME, user_name);
                jArr.put(jTmp);
                // jObj.put(Integer.toString(i), "{\"user_id\":\"" + rs.getString(s.USER_ID) +
                // "\", \"name\":\"" + rs.getString(s.PROJECT_NAME) + "\"}");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db_helper.closeConnection();
        return jArr.toString();
    }

    public static String userValidation(String user_name, String password) {
        db_helper.start_connection();
        String query = "select id from user where user_name = '" + user_name + "' and password = '" + password + "'";
        System.out.println(query);
        ResultSet rs = db_helper.get_query(query);
        int user_id = -1;
        try {
            while (rs.next()) {
                user_id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db_helper.closeConnection();
        return user_id + "";
    }

    public static boolean are_there_any_new_msg(int user_id, int msg_count) {
        db_helper.start_connection();
        String query = "select count(*) from messages where receiver_id = " + user_id;
        ResultSet rs = db_helper.get_query(query);
        int count = 0;
        try {
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db_helper.closeConnection();
        return count > 0;
    }
}

package com.bismih.server_chat_app.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bismih.server_chat_app.constants.s;
import com.bismih.server_chat_app.utils.Db_process;

public class User {
    private int id;
    private String name;
    private String user_name;

    private User(int id, String name, String user_name) {
        this.id = id;
        this.name = name;
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUser_name() {
        return user_name;
    }

    public static User generate_user(int id, String name, String user_name) {
        return new User(id, name, user_name);
    }

    public static ArrayList<User> get_users(String json) {

        ArrayList<User> result = new ArrayList<>();
        JSONArray jArr = new JSONArray(json);
        JSONObject jObj2;
        int user_id;
        String user_name, name;
        for (int i = 0; i < jArr.length(); i++) {
            jObj2 = jArr.getJSONObject(i);
            user_id = jObj2.getInt(s.USER_ID);
            user_name = jObj2.getString(s.USER_NAME);
            name = jObj2.getString("name");

            result.add(new User(user_id, name, user_name));
        }
        return result;

    }

}

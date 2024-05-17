package com.bismih.server_chat_app.components;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bismih.server_chat_app.constants.s;

public class Messages {
    private int msg_id;
    private int sender_id;
    private int receiver_id;
    private int project_id;
    private String msg, type;

    private Messages(int msg_id, int sender_id, int receiver_id, int project_id, String msg, String type) {
        this.msg_id = msg_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.project_id = project_id;
        this.msg = msg;
        this.type = type;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

    public static Messages generate_message(int msg_id, int sender_id, int receiver_id, int project_id, String msg,
            String type) {
        return new Messages(msg_id, sender_id, receiver_id, project_id, msg, type);
    }

    public static ArrayList<Messages> getMessages(String json, int project_id) {
        ArrayList<Messages> result = new ArrayList<>();
        JSONArray jArr = new JSONArray(json);
        JSONObject jObj2;
        int msg_id, receiver_id, sender_id;
        String msg, type;
        for (int i = 0; i < jArr.length(); i++) {
            jObj2 = jArr.getJSONObject(i);
            msg_id = jObj2.getInt(s.MSG_ID);
            sender_id = jObj2.getInt(s.SENDER_ID);
            receiver_id = jObj2.getInt(s.RECEIVER_ID);
            msg = jObj2.getString("msg");
            jObj2 = new JSONObject(msg);
            msg = jObj2.getString("msg");
            type = jObj2.getString("type");
            result.add(new Messages(msg_id, sender_id, receiver_id, project_id, msg, type));
        }
        return result;
    }

    public static Messages get_message(String json) {
        JSONObject jObj = new JSONObject(json);
        return new Messages(-1, jObj.getInt(s.SENDER_ID), jObj.getInt(s.RECEIVER_ID),
                jObj.getInt(s.PROJECT_ID), jObj.getString("msg"), jObj.getString("type"));
    }

}

package com.bismih.server_chat_app.components;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bismih.server_chat_app.constants.s;
import com.bismih.server_chat_app.utils.Db_process;

public class Messages {
    private int msg_id;
    private int sender_id;
    private int receiver_id;
    private int project_id;
    private String msg;

    private Messages(int msg_id, int sender_id, int receiver_id, int project_id, String msg) {
        this.msg_id = msg_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.project_id = project_id;
        this.msg = msg;
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

    public static Messages generate_message(int msg_id, int sender_id, int receiver_id, int project_id, String msg) {
        return new Messages(msg_id, sender_id, receiver_id, project_id, msg);
    }

    public static ArrayList<Messages> getMessages(int project_id, int receiver_id, int sender_id) {
        ArrayList<Messages> result = new ArrayList<>();
        JSONArray jArr = new JSONArray(Db_process.getMsgs(project_id, receiver_id, sender_id));
        JSONObject jObj2;
        int msg_id;
        String msg;
        for (int i = 0; i < jArr.length(); i++) {
            jObj2 = jArr.getJSONObject(i);
            msg_id = jObj2.getInt("msg_id");
            sender_id = jObj2.getInt(s.SENDER_ID);
            receiver_id = jObj2.getInt(s.RECEIVER_ID);
            msg = jObj2.getString("msg");
            result.add(new Messages(msg_id, sender_id, receiver_id, project_id, msg));
        }
        return result;
    }

}

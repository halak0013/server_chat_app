package com.bismih.server_chat_app.components;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bismih.server_chat_app.constants.s;
import com.bismih.server_chat_app.utils.Db_process;

public class Project {
    private int project_id;
    private String name;
    private int admin;
    private String link;

    private Project(int project_id, String name, int admin, String link) {
        this.project_id = project_id;
        this.name = name;
        this.admin = admin;
        this.link = link;
    }

    public int getProject_id() {
        return project_id;
    }

    public String getName() {
        return name;
    }

    public int getAdmin() {
        return admin;
    }

    public String getLink() {
        return link;
    }

    public static Project generate_project(int project_id, String name, int admin, String link) {
        return new Project(project_id, name, admin, link);
    }

    public static ArrayList<Project> getProjects(String json) {
        ArrayList<Project> result = new ArrayList<>();
        JSONArray jArr = new JSONArray(json);
        JSONObject jObj2;
        int project_id, admin;
        String name, link;
        for (int i = 0; i < jArr.length(); i++) {
            jObj2 = jArr.getJSONObject(i);
            project_id = jObj2.getInt(s.PROJECT_ID);
            name = jObj2.getString(s.PROJECT_NAME);
            link = jObj2.getString("link");
            admin = jObj2.getInt("admin");

            result.add(Project.generate_project(project_id, name, admin, link));
        }
        return result;
    }
}

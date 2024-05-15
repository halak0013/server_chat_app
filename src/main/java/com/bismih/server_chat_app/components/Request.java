package com.bismih.server_chat_app.components;

import org.json.JSONObject;

public class Request {
    private String code;
    private String result;

    public Request(String code, String result) {
        this.code = code;
        this.result = result;
    }

    public static Request getRequest(String code, String result) {
        return new Request(code, result);
    }

    public static Request getRequest(String json) {
        JSONObject jObj = new JSONObject(json);
        return new Request(jObj.getString("code"), jObj.getString("result"));
    }

    public static String getJsonRequest(Request request) {
        JSONObject jObj = new JSONObject();
        jObj.put("code", request.getCode());
        jObj.put("result", request.getResult());
        return jObj.toString();
    }

    public String getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "Request{" +
                "code='" + code + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}

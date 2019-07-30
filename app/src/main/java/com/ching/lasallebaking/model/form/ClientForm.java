package com.ching.lasallebaking.model.form;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientForm implements ApiJson {

    private String email;
    private String password;
    private String name;

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/client/create";
    }

    @Override
    public JSONObject getJsonData() {

        JSONObject json = new JSONObject();
        try {
            json.put("email",this.email);
            json.put("password", this.password);
            json.put("name",this.name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

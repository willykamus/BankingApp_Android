package com.ching.lasallebaking.model.form;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientForm implements ApiJson {

    private String email;
    private String password;
    private String name;

    public ClientForm() {
    }

    public ClientForm(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/lasalle/client/create";
    }

    @Override
    public JSONObject getJsonObject() {

        JSONObject json = new JSONObject();
        try {
            json.put("email",this.email);
            json.put("password", this.password);
            json.put("name",this.name);
            Log.d("JSON", "getJsonData: " + json);
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

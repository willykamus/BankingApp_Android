package com.ching.lasallebaking.model.form;

import com.ching.lasallebaking.R;

import org.json.JSONObject;

import java.util.HashSet;

public class FindClientForm extends HashSet<ClientForm> implements ApiJson {

    private String email;

    public FindClientForm() {
    }

    public FindClientForm(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/lasalle/client?email=" + this.email;
    }

    @Override
    public JSONObject getJsonObject() {
        return null;
    }
}

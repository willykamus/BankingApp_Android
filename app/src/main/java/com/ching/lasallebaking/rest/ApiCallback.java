package com.ching.lasallebaking.rest;

import org.springframework.http.HttpStatus;

import java.util.Set;

public interface ApiCallback {

    void onGetRequestResult(HttpStatus response, Set<Object> data);
    void onPostRequestResult(HttpStatus response, Object data);

}

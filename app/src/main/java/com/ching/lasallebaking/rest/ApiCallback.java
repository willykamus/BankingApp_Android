package com.ching.lasallebaking.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ApiCallback<T> {

    void onGetRequestResult(HttpStatus response, ResponseEntity<T> entity);
    void onPostRequestResult(HttpStatus response, ResponseEntity<T> entity);

}

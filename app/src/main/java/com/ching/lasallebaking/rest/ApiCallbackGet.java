package com.ching.lasallebaking.rest;

import org.springframework.http.ResponseEntity;

public interface ApiCallbackGet<T> {

    void getResult(ResponseEntity<T> responseEntity);
}

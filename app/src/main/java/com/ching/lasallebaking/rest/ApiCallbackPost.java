package com.ching.lasallebaking.rest;

import org.springframework.http.ResponseEntity;

public interface ApiCallbackPost<T> {

    void postResult(ResponseEntity<T> responseEntity);
}

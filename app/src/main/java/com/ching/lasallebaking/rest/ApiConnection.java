package com.ching.lasallebaking.rest;

import android.os.AsyncTask;

import com.ching.lasallebaking.model.form.ApiJson;
import com.ching.lasallebaking.model.form.ClientForm;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class ApiConnection {

    private ApiCallback apiCallback;

    public ApiConnection(ApiCallback apiCallback) {
        this.apiCallback = apiCallback;
    }

    public void createClient(ClientForm form) {
        new PostTaskJson<>(ClientForm.class).execute(form);
    }

    public class GetTaskJson<T extends ApiJson> extends AsyncTask<T, Void, ResponseEntity<T>> {

        private Class<T> tClass;

        public GetTaskJson(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        protected ResponseEntity<T> doInBackground(T... ts) {

            String url = ts[0].getURL();

            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

            RestTemplate restTemplate = new RestTemplate(true);

            try {
                return restTemplate.exchange(url, HttpMethod.GET, entity, tClass);
            } catch (RestClientException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ResponseEntity<T> responseEntity) {
            apiCallback.onGetRequestResult(responseEntity.getStatusCode(),(Set<Object>) responseEntity.getBody());
        }
    }

    public class PostTaskJson<T extends ApiJson> extends AsyncTask<T, Void, ResponseEntity<T>> {

        private Class<T> tClass;
        private JSONObject jsonObject;

        public PostTaskJson(Class<T> tClass) {
            this.tClass = tClass;
        }


        @Override
        protected ResponseEntity<T> doInBackground(T... ts) {

            String url = ts[0].getURL();
            this.jsonObject = ts[0].getJsonData();

            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(this.jsonObject.toString(),httpHeaders);

            RestTemplate template = new RestTemplate(true);

            try {
                return template.exchange(url,HttpMethod.POST,httpEntity,tClass);
            } catch (RestClientException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ResponseEntity<T> responseEntity) {
            apiCallback.onPostRequestResult(responseEntity.getStatusCode(), responseEntity.getBody());
        }
    }
}

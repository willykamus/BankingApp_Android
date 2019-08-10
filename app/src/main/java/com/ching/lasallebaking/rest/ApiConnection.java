package com.ching.lasallebaking.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.ching.lasallebaking.model.form.ApiJson;
import com.ching.lasallebaking.model.form.ClientForm;
import com.ching.lasallebaking.model.form.FindClientForm;

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

    private ApiCallbackGet apiCallbackGet;
    private ApiCallbackPost apiCallbackPost;

    public ApiConnection(ApiCallbackGet apiCallbackGet) {
        this.apiCallbackGet = apiCallbackGet;
    }

    public ApiConnection(ApiCallbackPost apiCallbackPost) {
        this.apiCallbackPost = apiCallbackPost;
    }

    public void createClient(ClientForm form) {
        new PostTaskJson<>(ClientForm.class).execute(form);
    }

    public void findClientByEmail(FindClientForm form) {
        new GetJsonTask<>(FindClientForm.class).execute(form);
    }

    public class GetJsonTask<T extends ApiJson, E> extends AsyncTask<T, Void, ResponseEntity<E>> {

        private final Class<E> eClass;

        public GetJsonTask(Class<E> eClass) {
            this.eClass = eClass;
        }

        @Override
        protected ResponseEntity<E> doInBackground(T... ts) {

            String url = ts[0].getURL();

            Log.d("URL", "doInBackground: " + url);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Accept", "application/json");

            HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

            RestTemplate restTemplate = new RestTemplate(true);

            try {
                return restTemplate.exchange(url, HttpMethod.GET, httpEntity, eClass);
            } catch (RestClientException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseEntity<E> responseEntity) {
            apiCallbackGet.getResult(responseEntity);
        }
    }

    public class PostTaskJson<T extends ApiJson, E> extends AsyncTask<T, Void, ResponseEntity<E>> {

        private Class<E> eClass;

        private JSONObject jsonObject;

        public PostTaskJson(Class<E> eClass) {
            this.eClass = eClass;
        }

        @Override
        protected ResponseEntity<E> doInBackground(T... ts) {

            String url = ts[0].getURL();
            jsonObject = ts[0].getJsonObject();

            Log.d("URL", "doInBackground: " + url);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(this.jsonObject.toString(), httpHeaders);

            RestTemplate restTemplate = new RestTemplate(true);

            try {
                return restTemplate.exchange(url, HttpMethod.POST, httpEntity, eClass);
            } catch (RestClientException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseEntity<E> responseEntity) {
            apiCallbackPost.postResult(responseEntity);
        }
    }
}

package com.ching.lasallebaking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ching.lasallebaking.model.form.ClientForm;
import com.ching.lasallebaking.model.form.FindClientForm;
import com.ching.lasallebaking.rest.ApiCallback;
import com.ching.lasallebaking.rest.ApiCallbackGet;
import com.ching.lasallebaking.rest.ApiCallbackPost;
import com.ching.lasallebaking.rest.ApiConnection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Set;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.registerButton);
        name = findViewById(R.id.reg_nameET);
        email = findViewById(R.id.reg_emailET);
        password = findViewById(R.id.reg_passwordET);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ClientForm form = new ClientForm();

                form.setEmail(email.getText().toString());
                form.setName(name.getText().toString());
                form.setPassword(password.getText().toString());

                //Check if the user already exist
                new ApiConnection(new ApiCallbackGet<Set<ClientForm>>() {
                    @Override
                    public void getResult(ResponseEntity<Set<ClientForm>> responseEntity) {
                        Set<ClientForm> data = responseEntity.getBody();
                        ArrayList<ClientForm> list = new ArrayList<>(data);
                        Log.d("RESPONSE", "getResult: " + list.size());

                        // If the user exist, the set will contains at least 1 object
                        if(list.get(0) != null) {
                            Toast.makeText(getApplicationContext(),"Email already register",Toast.LENGTH_SHORT).show();
                        } else {
                            new ApiConnection(new ApiCallbackPost() {
                                @Override
                                public void postResult(ResponseEntity responseEntity) {
                                    if(responseEntity != null) {
                                        if (responseEntity.getStatusCode() == HttpStatus.OK) {
                                            Toast.makeText(getApplicationContext(),"Succesfully Registered",Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Unexpected Error!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).createClient(form);
                        }
                    }
                }).findClientByEmail(new FindClientForm(form.getEmail()));
            }
        });

    }

}

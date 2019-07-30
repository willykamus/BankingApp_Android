package com.ching.lasallebaking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ching.lasallebaking.model.form.ClientForm;
import com.ching.lasallebaking.rest.ApiCallback;
import com.ching.lasallebaking.rest.ApiConnection;

import org.springframework.http.HttpStatus;

import java.util.Set;

public class RegisterActivity extends AppCompatActivity implements ApiCallback {

    private EditText name;
    private EditText email;
    private EditText password;
    private ApiConnection apiConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        apiConnection = new ApiConnection(this);

        Button register = findViewById(R.id.registerButton);
        name = findViewById(R.id.reg_nameET);
        email = findViewById(R.id.reg_emailET);
        password = findViewById(R.id.reg_passwordET);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientForm form = new ClientForm();
                form.setEmail(email.getText().toString());
                form.setName(name.getText().toString());
                form.setPassword(password.getText().toString());
                apiConnection.createClient(form);
            }
        });

    }

    @Override
    public void onGetRequestResult(HttpStatus response, Set<Object> data) {

    }

    @Override
    public void onPostRequestResult(HttpStatus response, Object data) {
        if(response != null) {
            if(response == HttpStatus.OK) {
                Toast.makeText(getApplicationContext(),"Succesfully Registered",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Unexpected Error!", Toast.LENGTH_LONG).show();
        }
    }
}

package com.tribble.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tribble.R;
import com.tribble.util.query.LoginQuery;
import com.tribble.util.query.QueryTask;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends ActionBarActivity {

    private final String LOG_TAG = LoginActivity.class.getSimpleName();

    private EditText email;
    private EditText password;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(isLogged()){
            // redirect
            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
            startActivity(intent);
        }

        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        submitButton = (Button) findViewById(R.id.submit_login);

        setListener();
    }

    private void setListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText) findViewById(R.id.input_email);
                password = (EditText) findViewById(R.id.input_password);
                submitButton = (Button) findViewById(R.id.submit_login);

                LoginQuery query = new LoginQuery(email.getText().toString(), password.getText().toString());
                String url = query.build();

                Log.d(LOG_TAG, String.format("Authenticating user email=%s, password=%s",
                        email.getText(), password.getText()));

                QueryTask task = new QueryTask();
                try {
                    task.execute("POST", url).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                String message;
                if (task.getResponseCode() == 200) {
                    message = "OK credentials";
                    updatePrefs(email.getText().toString());

                    Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else {
                    message = "Wrong credentials";
                }

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePrefs(String userEmail){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        prefs.edit().putString(String.valueOf(R.string.user_email), userEmail);
    }

    private boolean isLogged(){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String userEmail = prefs.getString(String.valueOf(R.string.user_email), null);

        return userEmail != null;
    }
}

package com.tribble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tribble.R;
import com.tribble.TribbleApp;
import com.tribble.util.query.Query;
import com.tribble.util.query.QueryTask;
import com.tribble.util.query.TranslateQuery;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestActivity extends ActionBarActivity {

    private final String LOG_TAG = TestActivity.class.getSimpleName();

    private EditText input;
    private TextView output;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().show();

        input = (EditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);
        submitButton = (Button) findViewById(R.id.submit);

        setListener();
    }

    private void setListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = (EditText) findViewById(R.id.input);
                output = (TextView) findViewById(R.id.output);
                submitButton = (Button) findViewById(R.id.submit);

                Log.d(LOG_TAG, "to translate ==> " + input.getText());
                Query query = new TranslateQuery(input.getText().toString(), null, TribbleApp.getInstance().getDefaultLang());
                String url = query.build();
                QueryTask task = new QueryTask();
                try {
                    task.execute("GET", url).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                List<String> result = task.getResult();
                if (result != null) {
                    Log.d(LOG_TAG, result.toString());
                    output.setText(task.getResult().get(0));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.action_login:
                Toast.makeText(this, "Redirection to login", Toast.LENGTH_SHORT).show();
                intent = new Intent(TestActivity.this, LoginActivity.class);
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Redirection to settings", Toast.LENGTH_SHORT).show();
                intent = new Intent(TestActivity.this, SettingsActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return true;
    }
}

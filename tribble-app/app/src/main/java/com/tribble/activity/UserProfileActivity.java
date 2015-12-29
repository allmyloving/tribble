package com.tribble.activity;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tribble.R;
import com.tribble.entity.Translation;
import com.tribble.util.query.Query;
import com.tribble.util.query.QueryTask;
import com.tribble.util.query.UserTranslationsQuery;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserProfileActivity extends ActionBarActivity {

    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        textView = (TextView) findViewById(R.id.userProfileText);

    }

    private List<Translation> getAllTranslations(int userId) {
        Query query = new UserTranslationsQuery(userId);
        String url = query.build();

        QueryTask task = new QueryTask();
        try {
            task.execute("GET", url).get();
            return task.getTranslations(task.getResponse());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.action_login);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "Redirection to settings", Toast.LENGTH_SHORT).show();
                intent = new Intent(UserProfileActivity.this, SettingsActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return true;
    }

    public static class MyListFragment extends ListFragment implements AdapterView.OnItemClickListener {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.list_fragment, container, false);
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // MOCK !!!
            ArrayAdapter<Translation> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                    ((UserProfileActivity)getActivity()).getAllTranslations(1));
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        }
    }
}

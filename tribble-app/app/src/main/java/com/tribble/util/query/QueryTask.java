package com.tribble.util.query;

import android.os.AsyncTask;
import android.util.Log;

import com.tribble.entity.Translation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class QueryTask extends AsyncTask<String, String, String> {

    private static final String LOG_TAG = AsyncTask.class.getSimpleName();

    private static final String ENCODING = "utf-8";

    private Throwable throwable;

    private List<String> result;

    private int responseCode;

    public List<String> getResult() {
        return result;
    }

    public int getResponseCode() {
        return responseCode;
    }

    private String response;

    public String getResponse() {
        return response;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected String doInBackground(String... params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("param size should be 2");
        }
        try {
            String method = params[0];
            String path = params[1];

            URL url = new URL(path);
            Log.d(LOG_TAG, "executing " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            responseCode = conn.getResponseCode();
            Log.i(LOG_TAG, String.format("response code=%d", conn.getResponseCode()));
            conn.connect();

            String response = readInput(conn.getInputStream());
            Log.d(LOG_TAG, "response: " + response);
            this.response = response;

            if (!response.isEmpty()) {
                result = getResults(response);
            }

            conn.disconnect();
        } catch (IOException t) {
            Log.e(LOG_TAG, t.getMessage() + " " + t.toString());
            //throwable = t;
        }
        // return smth
        return null;
    }

    private String readInput(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append('\n');
        }
        br.close();

        return sb.toString();
    }

    private List<String> getResults(String input) throws IOException {
        List<String> result = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(input);
            Log.d(LOG_TAG, object.toString());
            JSONArray translations = object.getJSONObject("data").getJSONArray("translations");

            for (int i = 0; i < translations.length(); i++) {
                JSONObject text = translations.getJSONObject(i);
                result.add(text.getString("translatedText"));
                Log.d(LOG_TAG, text.getString("translatedText"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Translation> getTranslations(String response) {
        List<Translation> translationList = new ArrayList<>();
        try {
            JSONArray translations = new JSONArray(response);
            for (int i = 0; i < translations.length(); i++) {
                Translation t = new Translation();
                JSONObject item = translations.getJSONObject(i);
                t.source(item.getString("source"))
                        .target(item.getString("target"))
                        .sourceLang(item.getJSONObject("sourceLang").getString("code"))
                        .targetLang(item.getJSONObject("targetLang").getString("code"));
                translationList.add(t);
                Log.d(LOG_TAG, item.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return translationList;
    }
}

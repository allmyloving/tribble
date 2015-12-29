package com.tribble.util.query;

import android.util.Log;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class QueryBuilder {

    private final String scheme = "http";

    // home ip
    //private final String host = "192.168.0.100:8080";
    private final String host = "192.168.87.207:8080";

    private String path;

    private List<NameValuePair> parameters;

    public QueryBuilder() {
        parameters = new ArrayList<>();
    }

    public QueryBuilder path(String path) {
        this.path = path;
        return this;
    }

    public QueryBuilder addParameter(String key, String value) {
        parameters.add(new BasicNameValuePair(key, value));
        return this;
    }

    public String build() {
        URIBuilder builder = new URIBuilder();
        String result = null;

        // throw an exception ?
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }
        builder.setScheme(scheme).setHost(host).setPath(path).setParameters(parameters);
        try {
            result = builder.build().toString();
        } catch (URISyntaxException e) {
            Log.e("logs", "URISyntaxException occured " + e.getMessage());
        }
        return result;
    }
}

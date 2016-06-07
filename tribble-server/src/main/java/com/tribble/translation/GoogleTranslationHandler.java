package com.tribble.translation;

import com.tribble.translation.TranslationHandler;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public final class GoogleTranslationHandler implements TranslationHandler {


    private static final String KEY = "AIzaSyDc5z8zob0IqtuLBp49WI8I2ouj2V6AHgI";

    public URI buildURI(String source, String target, String q) {
        URIBuilder builder = new URIBuilder();
        builder.addParameter("key", KEY);
        if (source != null) {
            builder.addParameter("source", source);
        }
        builder.addParameter("target", target);
        builder.addParameter("q", q);
        builder.setScheme("https").setHost("www.googleapis.com").setPath("/language/translate/v2");
        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            System.out.println("URI exception occurred " + e.getMessage());
        }
        return uri;
    }
}

package com.tribble.translation;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class YandexTranslationHandler implements TranslationHandler {

    private static final String KEY = "trnsl.1.1.20160607T090854Z.cc3c8a9967efd89d.da1128f04a15e6950b729337fb097762c54e21e6";

    @Override
    public URI buildURI(String source, String target, String q) {
        URIBuilder builder = new URIBuilder();
        builder.addParameter("key", KEY);

        StringBuilder lang = new StringBuilder();
        if (source != null) {
            lang.append(source).append("-");
        }
        lang.append(target);
        builder.addParameter("lang", lang.toString());
        builder.addParameter("text", q);
        builder.setScheme("https").setHost("translate.yandex.net").setPath("/api/v1.5/tr.json/translate");
        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            System.out.println("URI exception occurred " + e.getMessage());
        }
        return uri;
    }
}

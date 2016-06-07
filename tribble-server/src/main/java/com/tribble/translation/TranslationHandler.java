package com.tribble.translation;

import java.net.URI;

public interface TranslationHandler {

    URI buildURI(String source, String target, String q);
}

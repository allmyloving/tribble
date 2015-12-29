package com.tribble;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TribbleApp {

    private static final TribbleApp INSTANCE = new TribbleApp();

    private TribbleApp() {
        languages = Arrays.asList("de", "en", "es", "ru");
        defaultLang = Locale.getDefault().getLanguage();
        if (!languages.contains(defaultLang)) {
            defaultLang = languages.get(0);
        }
    }

    public static TribbleApp getInstance() {
        return INSTANCE;
    }

    private String defaultLang;

    private List<String> languages;

    public String getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(String lang){
        if(languages.contains(defaultLang)){
            defaultLang = lang;
        }
    }

    public List<String> getLanguages() {
        return languages;
    }
}

package com.tribble.entity;

public class Translation {

    private String source;

    private String target;

    private String sourceLang;

    private String targetLang;

    public Translation(){}

    public Translation source(String source){
        this.source = source;
        return this;
    }

    public Translation target(String target){
        this.target = target;
        return this;
    }

    public Translation sourceLang(String sourceLang){
        this.sourceLang = sourceLang;
        return this;
    }

    public Translation targetLang(String targetLang){
        this.targetLang = targetLang;
        return this;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "target='" + target + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}

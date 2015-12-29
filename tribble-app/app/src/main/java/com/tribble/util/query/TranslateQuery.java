package com.tribble.util.query;

public class TranslateQuery implements Query {
    private String source;

    private String target;

    private String q;

    public TranslateQuery(String q, String source, String target) {
        this.q = q;
        this.source = source;
        this.target = target;
    }

    public String build() {
        QueryBuilder builder = new QueryBuilder();
        builder = builder.path(Parameter.TRANSLATE + Parameter.GET)
                .addParameter(Parameter.TARGET, target)
                .addParameter(Parameter.Q, q);
        if (source != null) {
            builder = builder.addParameter(Parameter.SOURCE, source);
        }

        return builder.build();
    }
}

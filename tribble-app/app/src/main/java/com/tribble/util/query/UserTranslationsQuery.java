package com.tribble.util.query;

public class UserTranslationsQuery implements  Query {

    private int userId;

    public UserTranslationsQuery(int userId) {
        this.userId = userId;
    }

    public String build() {
        QueryBuilder builder = new QueryBuilder();
        builder = builder.path(Parameter.TRANSLATE + Parameter.ALL + '/' + userId);
        return builder.build();
    }
}

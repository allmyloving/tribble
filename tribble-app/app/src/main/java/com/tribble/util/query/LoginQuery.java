package com.tribble.util.query;

public class LoginQuery  implements Query{

    private String email;

    private String password;

    public LoginQuery(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String build() {
        QueryBuilder builder = new QueryBuilder();
        builder = builder.path(Parameter.USER + Parameter.AUTH)
                .addParameter(Parameter.EMAIL, email)
                .addParameter(Parameter.PASSWORD, password);
        return builder.build();
    }
}

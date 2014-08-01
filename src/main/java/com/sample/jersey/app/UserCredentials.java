package com.sample.jersey.app;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserCredentials {

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

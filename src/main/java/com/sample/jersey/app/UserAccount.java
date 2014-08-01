package com.sample.jersey.app;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserAccount {

    @JsonProperty
    private String first_name;

    @JsonProperty
    private String last_name;

    @JsonProperty
    private String user_name;

    @JsonProperty
    private String email;

    @JsonProperty
    private String password;


    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getUserName() {
        return user_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

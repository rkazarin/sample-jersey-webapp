package com.sample.jersey.app;

public class CurrentUser {

    static String user_name;
    static boolean isAuthorized = false;

    public void setUserName(String userName){
        user_name = userName;
    }

    public String getUserName(){
        return user_name;
    }
}

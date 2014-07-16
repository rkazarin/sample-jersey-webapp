package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;

//Stores data for the current user

public class CurrentUser {

    static String user_name;
    static Account authenticated = null;
}

package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;

public class CurrentUser {

    static String user_name;
    static boolean isAuthorized = false;
    static Account authenticated = null;
}

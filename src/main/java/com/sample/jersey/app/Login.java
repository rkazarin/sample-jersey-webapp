package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeyList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.resource.ResourceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import java.util.Iterator;


@Path("/login")
public class Login {

    String applicationHref = "https://api.stormpath.com/v1/applications/5HRNSljrcYQax0oCQQovT9";

    @Context
    private HttpServletRequest servletRequest;
    @Context
    private HttpServletResponse servletResponse;

    @POST
    public String getDashboard() throws Exception{
        StormpathClient stormpathClient = new StormpathClient();
        Client myClient = stormpathClient.getClient();

        Application application = myClient.getResource(applicationHref, Application.class);

        String username = servletRequest.getHeader("userName");
        String password = servletRequest.getHeader("password");


        AuthenticationRequest request = new UsernamePasswordRequest(username, password);

        Account authenticated = null;

        try {
            authenticated = application.authenticateAccount(request).getAccount();

        } catch (ResourceException e) {
            System.out.println("Failed to auth user");
            System.out.println("Going to fresh login page...try again.");
            servletResponse.addHeader("MyResult", "Not Authorized");
            return "";
        }

        CurrentUser.authenticated = authenticated;


        System.out.println("Auth success, going to dashboard.");
        servletResponse.addHeader("MyResult", "Authorized");
        CurrentUser.user_name = username;
        CurrentUser.isAuthorized = true;
        return "";
    }
}

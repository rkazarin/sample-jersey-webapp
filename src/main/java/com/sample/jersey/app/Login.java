package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.resource.ResourceException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;


@Path("/login")
public class Login {

    String applicationHref = "https://api.stormpath.com/v1/applications/5HRNSljrcYQax0oCQQovT9";

    @Context
    private HttpServletResponse servletResponse;

    @POST
    public String getDashboard(String data) throws Exception{

        StormpathClient stormpathClient = new StormpathClient();
        Client myClient = stormpathClient.getClient();

        Application application = myClient.getResource(applicationHref, Application.class);

        //Get username and password from request data
        JSONObject json = new JSONObject(data);
        String username = json.getString("Username");
        String password = json.getString("Password");

        AuthenticationRequest request = new UsernamePasswordRequest(username, password);
        Account authenticated = null;

        //Try to authenticate the account
        try {
            authenticated = application.authenticateAccount(request).getAccount();

        } catch (ResourceException e) {
            System.out.println("Failed to auth user");
            System.out.println("Going to fresh login page...try again.");
            servletResponse.sendError(401);
            return "";
        }

        //Set the CurrentUser's account to the authenticated Account
        CurrentUser.authenticated = authenticated;
        CurrentUser.user_name = username;

        return "";
    }
}

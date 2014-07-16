package com.sample.jersey.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiAuthenticationResult;
import com.stormpath.sdk.resource.ResourceException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import com.stormpath.sdk.client.*;
import com.stormpath.sdk.application.Application;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.*;
import java.io.*;

@Path("/makeStormpathAccount")
public class StormpathAccount {

    String applicationHref = "https://api.stormpath.com/v1/applications/5HRNSljrcYQax0oCQQovT9";
    @Context
    private HttpServletRequest servletRequest;

    @POST
    public String createAccount(String data) throws Exception {


        StormpathClient stormpathClient = new StormpathClient();
        Client myClient = stormpathClient.getClient();
        Application application = myClient.getResource(applicationHref, Application.class);
        Account account = myClient.instantiate(Account.class);

        //Get data from request
        JSONObject json = new JSONObject(data);

        String firstName = json.getString("first_name");
        String lastName = json.getString("last_name");
        String userName = json.getString("user_name");
        String email = json.getString("email");
        String password = json.getString("password");

        //Set account info and create the account
        account.setGivenName(firstName);
        account.setSurname(lastName);
        account.setUsername(userName);
        account.setEmail(email);
        account.setPassword(password);

        application.createAccount(account);

        return "";
    }

}

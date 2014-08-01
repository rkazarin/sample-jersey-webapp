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

    @POST
    public void createAccount(UserAccount userAccount) throws Exception {


        Application application = StormpathUtils.myClient.getResource(StormpathUtils.applicationHref, Application.class);
        Account account = StormpathUtils.myClient.instantiate(Account.class);

        //Set account info and create the account
        account.setGivenName(userAccount.getFirstName());
        account.setSurname(userAccount.getLastName());
        account.setUsername(userAccount.getUserName());
        account.setEmail(userAccount.getEmail());
        account.setPassword(userAccount.getPassword());

        application.createAccount(account);

    }

}

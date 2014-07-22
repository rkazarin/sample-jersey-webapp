package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.resource.ResourceException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import javax.servlet.http.Cookie;


@Path("/login")
public class Login {

    @Context
    private HttpServletResponse servletResponse;

    @POST
    public void getDashboard(String data) throws Exception{

        Application application = StormpathUtils.myClient.getResource(StormpathUtils.applicationHref, Application.class);

        //Get username and password from request data
        JSONObject json = new JSONObject(data);
        String username = json.getString("Username");
        String password = json.getString("Password");

        AuthenticationRequest request = new UsernamePasswordRequest(username, password);
        Account authenticated;

        //Try to authenticate the account
        try {
            authenticated = application.authenticateAccount(request).getAccount();

        } catch (ResourceException e) {
            System.out.println("Failed to auth user");
            System.out.println("Going to fresh login page...try again.");
            servletResponse.sendError(401);
            return;
        }

        Cookie myCookie = new Cookie("accountHref", authenticated.getHref());
        myCookie.setMaxAge(60 * 60);
        myCookie.setPath("/");
        myCookie.setHttpOnly(true);
        servletResponse.addCookie(myCookie);

    }
}
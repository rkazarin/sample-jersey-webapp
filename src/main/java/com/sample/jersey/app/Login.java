package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.resource.ResourceException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/login")
public class Login {

    @Context
    private HttpServletResponse servletResponse;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void getDashboard(UserCredentials userCredentials) throws Exception {

        Application application = StormpathUtils.myClient.getResource(StormpathUtils.applicationHref, Application.class);

        AuthenticationRequest request = new UsernamePasswordRequest(userCredentials.getUsername(), userCredentials.getPassword());
        Account authenticated;

        //Try to authenticate the account
        try {
            authenticated = application.authenticateAccount(request).getAccount();

        } catch (ResourceException e) {
            System.out.println("Failed to authenticate user");
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
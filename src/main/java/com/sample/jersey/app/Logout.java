package com.sample.jersey.app;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("/logout")
public class Logout {

    @Context
    private HttpServletRequest servletRequest;

    @Context
    private HttpServletResponse servletResponse;

    @GET
    public void logout() throws Exception {
        Cookie[] myCookies = servletRequest.getCookies();
        String accountHref = "";
        for(int i = 0; i < myCookies.length; i++) {
            if(myCookies[i].getName().equals("accountHref")) {
                myCookies[i].setHttpOnly(true);
                myCookies[i].setPath("/");
                myCookies[i].setMaxAge(0);
                servletResponse.addCookie(myCookies[i]);
                return;
            }
        }
    }
}

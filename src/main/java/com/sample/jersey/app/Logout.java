package com.sample.jersey.app;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.CookieParam;
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
    public void logout(@CookieParam("accountHref") String accountHref) throws Exception {

        Cookie myCookie = new Cookie("accountHref", accountHref);
        myCookie.setMaxAge(0);
        myCookie.setPath("/");
        myCookie.setHttpOnly(true);
        servletResponse.addCookie(myCookie);

    }
}

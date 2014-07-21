package com.sample.jersey.app;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

@Provider
public class JerseyFilter implements ContainerRequestFilter {

    @Context
    private HttpServletResponse servletResponse;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        URI myURI =  requestContext.getUriInfo().getAbsolutePath();
        String myPath = myURI.getPath();

            if(myPath.equals("/rest/getApiKey")) {
                System.out.println("Account from cookie: " + requestContext.getCookies());
                Iterator it = requestContext.getCookies().entrySet().iterator();
                String accountHref = "";
                while(it.hasNext()) {
                    Map.Entry pairs = (Map.Entry)it.next();
                    if(pairs.getKey().equals("accountHref")) {
                        System.out.println(pairs.getKey() + " = " + pairs.getValue());
                        String hrefCookie = pairs.getValue().toString();
                        accountHref = hrefCookie.substring(hrefCookie.indexOf("https://"));
                    }
                }
                if(!accountHref.equals("")) {
                    System.out.println("Cookie for this account exists");
                }
                else {
                    System.out.println("Not logged in");
                    servletResponse.sendError(403);
                }

        }

        //find cookie for this request

        //Cehck if account is valid, if the cookie is valid

        //continue if everything passes
        //if error send user to login


    }
}

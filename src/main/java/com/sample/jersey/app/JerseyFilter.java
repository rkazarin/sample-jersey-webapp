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
                Iterator it = requestContext.getCookies().entrySet().iterator();
                String accountHref = "";
                while(it.hasNext()) {
                    Map.Entry pairs = (Map.Entry)it.next();
                    if(pairs.getKey().equals("accountHref")) {
                        String hrefCookie = pairs.getValue().toString();
                        accountHref = hrefCookie.substring(hrefCookie.indexOf("https://"));
                    }
                }
                if(!accountHref.equals("")) {
                    //Cookie exists, continue.
                    return;
                }
                else {
                    System.out.println("Not logged in");
                    servletResponse.sendError(403);
                }

        }

    }
}

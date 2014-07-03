package com.sample.jersey.app;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.servlet.*;

import com.stormpath.sdk.api.ApiAuthenticationResult;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import com.stormpath.sdk.client.*;
import com.stormpath.sdk.application.Application;


import java.net.*;
import java.io.*;


@Path("/weather/{city}")
public class Weather {

    @Context
    private HttpServletRequest servletRequest;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWeather(@PathParam("city") String myCity) throws Exception{

        StormpathClient stormpathClient = new StormpathClient();
        Client myClient = stormpathClient.getClient();

        System.out.println(myClient.getCurrentTenant().getName());

        String applicationHref = "https://api.stormpath.com/v1/applications/5HRNSljrcYQax0oCQQovT9";
        //Application application = myClient.getResource(applicationHref, Application.class);


        //ApiAuthenticationResult authResult = application.authenticateApiRequest()

        System.out.println(servletRequest.toString());

        URL weatherURL = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + myCity);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        City city = null;

        try {
            InputStream in = weatherURL.openStream();
            city = mapper.readValue(in, City.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = city.toString();

        return result;
    }


}

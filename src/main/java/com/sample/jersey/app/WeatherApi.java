package com.sample.jersey.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;

import com.stormpath.sdk.api.ApiAuthenticationResult;
import com.stormpath.sdk.authc.AuthenticationResultVisitorAdapter;
import com.stormpath.sdk.oauth.OauthAuthenticationResult;
import com.stormpath.sdk.resource.ResourceException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import com.stormpath.sdk.client.*;
import com.stormpath.sdk.application.Application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.*;
import java.io.*;

@Path("/api/weather/{city}")
public class WeatherApi {

    @Context
    private HttpServletRequest servletRequest;
    @Context
    private HttpServletResponse servletResponse;

    private String weatherResult;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWeatherApi(@PathParam("city") final String myCity) throws Exception {

        Application application = StormpathUtils.myClient.getResource(StormpathUtils.applicationHref, Application.class);

        System.out.println(servletRequest.getHeader("Authorization"));

        //Make sure this use is allowed to target is endpoint
        try {
            ApiAuthenticationResult authenticationResult = application.authenticateApiRequest(servletRequest);


            authenticationResult.accept(new AuthenticationResultVisitorAdapter() {

                public void visit(ApiAuthenticationResult result) {
                    System.out.println("Basic request");

                    URL weatherURL = getURL(myCity);

                    //Parse weather data into our POJO
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    City city = null;

                    try {
                        InputStream in = weatherURL.openStream();
                        city = mapper.readValue(in, City.class);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    weatherResult = city.toString() + " °F";
                }

                public void visit(OauthAuthenticationResult result) {

                    //Check scopes
                    if(result.getScope().contains("London") && myCity.equals("London")){

                        weatherResult = getWeather(myCity) + " °F";;
                    }
                    else if(result.getScope().contains("Berlin") && myCity.equals("Berlin")){

                        weatherResult = getWeather(myCity) + " °F";;
                    }
                    else if(result.getScope().contains("SanMateo") && myCity.equals("San Mateo")){

                        weatherResult = getWeather(myCity) + " °F";;
                    }
                    else if(result.getScope().contains("SanFrancisco") && myCity.equals("San Francisco")){

                        weatherResult = getWeather(myCity) + " °F";;
                    }
                    else {
                        try {
                            servletResponse.sendError(403);
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                    }
                }

            });

            return weatherResult;

        } catch (ResourceException e) {
            System.out.println(e.getMessage());
            servletResponse.sendError(403);
            return "Cannot authenticate user.";
        }
    }

    private URL getURL(String city) {

        URL weatherURL = null;
        try {
            weatherURL = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("City is invalid");
        }

        return weatherURL;

    }

    private String getWeather(String city) {
        URL weatherURL = getURL(city);

        //Parse weather data into our POJO
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        City myCity = null;

        try {
            InputStream in = weatherURL.openStream();
            myCity = mapper.readValue(in, City.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return myCity.toString();
    }

}

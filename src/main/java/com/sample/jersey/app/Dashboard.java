package com.sample.jersey.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/dashboard")
public class Dashboard {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDashboard() throws Exception{

        return "Welcome to your dashboard " + CurrentUser.user_name;


    }
}

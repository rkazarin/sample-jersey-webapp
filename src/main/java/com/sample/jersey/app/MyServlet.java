package com.sample.jersey.app;

import com.stormpath.sdk.client.Client;
import javax.servlet.*;

public class MyServlet implements ServletContextListener {

    public void contextInitialized(ServletContextEvent e) {
        System.out.println("Context Initialized");

        StormpathClient stormpathClient = new StormpathClient();
        Client myClient = stormpathClient.getClient();
        StormpathUtils.myClient = myClient;
    }

    public void contextDestroyed(ServletContextEvent e) {
        System.out.println("Context Destroyed");

    }

}

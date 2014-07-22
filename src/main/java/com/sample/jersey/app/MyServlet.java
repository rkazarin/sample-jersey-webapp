package com.sample.jersey.app;

import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.impl.cache.DefaultCacheManager;

import javax.servlet.*;
import java.io.File;

public class MyServlet implements ServletContextListener {

    public void contextInitialized(ServletContextEvent e) {
        System.out.println("Context Initialized");

        createClient();
        setApplicationHref();

    }

    public void contextDestroyed(ServletContextEvent e) {
        System.out.println("Context Destroyed");

    }

    public void createClient() {

        //check current dir for apiKey property
        File f = new File(System.getProperty("user.home") + "/.stormpath/apiKey.properties");
        if(f.exists()){
            System.out.println("File exists!");
            String apiKeyPath =  System.getProperty("user.home") + "/.stormpath/apiKey.properties";

            ApiKey apiKey = ApiKeys.builder().setFileLocation(apiKeyPath).build();
            Client client = Clients.builder().setApiKey(apiKey).setCacheManager(new DefaultCacheManager()).build();
            StormpathUtils.myClient = client;

            return;
        }

        else {
            throw new IllegalStateException("Unable to locate API Key and Secret. Cannot initialize application. " +
                    "Please make sure your API Key and Secret is stored in ~/.stormpath/apiKey.properties");
        }

    }

    public void setApplicationHref() {
        try {
            if (StormpathUtils.applicationHref.equals("")) {
                throw new IllegalStateException("Unable to get href for this application. Cannot initialize application. " +
                        "Please set the 'applicationHref' property of the StrompathUtils class.");
            }
            else {
                return;
            }

        }
        catch (NullPointerException e) {
            throw new IllegalStateException("Unable get href for this application. Cannot initialize application. " +
                    "Please set the 'applicationHref' property of the StrompathUtils class.");
        }
    }

}

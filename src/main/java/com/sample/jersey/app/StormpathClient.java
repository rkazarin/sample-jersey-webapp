package com.sample.jersey.app;

import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.*;

public class StormpathClient {


    private String path = "/Users/rkazarin/.stormpath/apiKey.properties";

    public Client getClient() {

        ApiKey apiKey = ApiKeys.builder().setFileLocation(path).build();
        com.stormpath.sdk.client.Client client = Clients.builder().setApiKey(apiKey).build();
        System.out.println(client.getCurrentTenant().getName());

        return client;

    }


}

package com.sample.jersey.app;

import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.impl.cache.DefaultCacheManager;

public class StormpathClient {

    private String path = "/Users/rkazarin/.stormpath/apiKey.properties";

    public Client getClient() {

        ApiKey apiKey = ApiKeys.builder().setFileLocation(path).build();
        Client client = Clients.builder().setApiKey(apiKey).setCacheManager(new DefaultCacheManager()).build();

        return client;

    }


}

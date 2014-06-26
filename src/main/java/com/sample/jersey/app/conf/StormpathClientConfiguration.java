package com.sample.jersey.app.conf;

import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;

public class StormpathClientConfiguration {

    public Client getClient() {
        ApiKey apiKey = ApiKeys.builder().setFileLocation("~/.stormpath/apiKey.properties").build();
        Client client = Clients.builder().setApiKey(apiKey).build();

        return client;
    }

}

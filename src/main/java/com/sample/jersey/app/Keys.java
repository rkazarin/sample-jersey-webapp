package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeyList;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Path("/getApiKey")
public class Keys {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map getApiKey(@CookieParam("accountHref") String accountHref) throws Exception {

        Account account = StormpathUtils.myClient.getResource(accountHref, Account.class);

        ApiKeyList apiKeyList = account.getApiKeys();

        boolean hasApiKey = false;
        String apiKeyId = "";
        String apiSecret = "";

        //If account already has an API Key
        for(Iterator<ApiKey> iter = apiKeyList.iterator(); iter.hasNext();) {
            hasApiKey = true;
            ApiKey element = iter.next();
            apiKeyId = element.getId();
            apiSecret = element.getSecret();
        }

        //If account doesn't have an API Key, generate one
        if(hasApiKey == false) {
            ApiKey newApiKey = account.createApiKey();
            apiKeyId = newApiKey.getId();
            apiSecret = newApiKey.getSecret();
        }

        //Get the username of the account
        String username = account.getUsername();

        //Make a JSON object with the key and secret to send back to the client

        Map<String, String> response = new HashMap<>();

        response.put("api_key", apiKeyId);
        response.put("api_secret", apiSecret);
        response.put("username", username);

        return response;
    }
}

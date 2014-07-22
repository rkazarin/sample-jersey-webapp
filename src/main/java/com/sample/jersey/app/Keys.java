package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeyList;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Iterator;

@Path("/getApiKey")
public class Keys {

    @Context
    private HttpServletRequest servletRequest;

    @Context
    private HttpServletResponse servletResponse;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getApiKey(@CookieParam("accountHref") String accountHref) throws Exception {

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
        JSONObject json = new JSONObject();
        json.put("api_key", apiKeyId);
        json.put("api_secret", apiSecret);
        json.put("username", username);

        return json.toString();
    }
}

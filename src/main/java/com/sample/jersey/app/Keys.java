package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeyList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.resource.ResourceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import java.util.Iterator;

@Path("/getApiKey")
public class Keys {

    String applicationHref = "https://api.stormpath.com/v1/applications/5HRNSljrcYQax0oCQQovT9";

    @Context
    private HttpServletRequest servletRequest;
    @Context
    private HttpServletResponse servletResponse;

    @GET
    public String getApiKey() throws Exception {
        ApiKeyList apiKeyList = CurrentUser.authenticated.getApiKeys();

        boolean hasApiKey = false;
        String apiKeyId = "";
        String apiSecret = "";

        for(Iterator<ApiKey> iter = apiKeyList.iterator(); iter.hasNext();) {
            hasApiKey = true;
            ApiKey element = iter.next();
            apiKeyId = element.getId();
            apiSecret = element.getSecret();
            System.out.println("The api key ID is: " + apiKeyId);
            System.out.println("The api Secret is: " + apiSecret);

        }

        if(hasApiKey == false) {
            ApiKey newApiKey = CurrentUser.authenticated.createApiKey();
            apiKeyId = newApiKey.getId();
            apiSecret = newApiKey.getSecret();
        }

        servletResponse.addHeader("APIKeyId", apiKeyId);
        servletResponse.addHeader("APIKeySecret", apiSecret);

        return "";
    }
}

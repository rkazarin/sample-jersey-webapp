package com.sample.jersey.app;

import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeyList;
import org.codehaus.jettison.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.Iterator;

@Path("/getApiKey")
public class Keys {

    String applicationHref = "https://api.stormpath.com/v1/applications/5HRNSljrcYQax0oCQQovT9";

    @Context
    private HttpServletResponse servletResponse;

    @GET
    public String getApiKey() throws Exception {
        ApiKeyList apiKeyList = CurrentUser.authenticated.getApiKeys();

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
            ApiKey newApiKey = CurrentUser.authenticated.createApiKey();
            apiKeyId = newApiKey.getId();
            apiSecret = newApiKey.getSecret();
        }

        //Make a JSON object with the key and secret to send back to the client
        JSONObject json = new JSONObject();
        json.put("api_key", apiKeyId);
        json.put("api_secret", apiSecret);

        //Send key and secret in the response
        servletResponse.getWriter().print(json.toString());
        servletResponse.getWriter().flush();

        return "";
    }
}

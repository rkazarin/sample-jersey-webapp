package com.sample.jersey.app;

import com.stormpath.sdk.api.ApiAuthenticationResult;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationResultVisitorAdapter;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.http.HttpMethod;
import com.stormpath.sdk.http.HttpRequest;
import com.stormpath.sdk.http.HttpRequestBuilder;
import com.stormpath.sdk.http.HttpRequests;
import com.stormpath.sdk.oauth.AccessTokenResult;
import com.stormpath.sdk.oauth.OauthAuthenticationResult;
import com.stormpath.sdk.oauth.TokenResponse;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/oauthToken")
public class OauthToken {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String getToken(@Context HttpHeaders httpHeaders,
                           @Context HttpServletRequest myRequest,
                           @Context final HttpServletResponse servletResponse,
                           @FormParam("grant_type") String grantType) throws Exception {

        /*Jersey's request.getParameter() always returns null, so we have to reconstruct the entire request ourselves in order to keep data
          See: https://java.net/jira/browse/JERSEY-766
         */

        //HttpRequestBuilder newBuilder = HttpRequests.method(HttpMethod.POST);

        Map<String, String[]> headers = new HashMap<String, String[]>();

        for(String httpHeaderName : httpHeaders.getRequestHeaders().keySet()) {

            //newBuilder.header(String, String[]);

            List<String> values = httpHeaders.getRequestHeader(httpHeaderName);
            String[] valueArray = new String[values.size()];
            httpHeaders.getRequestHeader(httpHeaderName).toArray(valueArray);
            headers.put(httpHeaderName, valueArray);
        }

        Map<String, String[]> body = new HashMap<String, String[]>();
        String[] bodyArray = {grantType};
        body.put("grant_type", bodyArray );

        HttpRequest request = HttpRequests.method(HttpMethod.POST).headers(headers).parameters(body).build();

        Application application = StormpathUtils.myClient.getResource(StormpathUtils.applicationHref, Application.class);

        AccessTokenResult oauthResult = (AccessTokenResult) application.authenticateOauthRequest(request).execute();
        TokenResponse tokenResponse = oauthResult.getTokenResponse();

        return tokenResponse.toJson();

    }

}

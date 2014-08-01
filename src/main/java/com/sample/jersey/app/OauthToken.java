package com.sample.jersey.app;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;

import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.http.HttpMethod;
import com.stormpath.sdk.http.HttpRequest;
import com.stormpath.sdk.http.HttpRequests;
import com.stormpath.sdk.oauth.AccessTokenResult;
import com.stormpath.sdk.oauth.ScopeFactory;
import com.stormpath.sdk.oauth.TokenResponse;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/oauthToken")
public class OauthToken {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String getToken(@Context HttpHeaders httpHeaders,
                           @Context HttpServletRequest myRequest,
                           @Context final HttpServletResponse servletResponse,
                           @FormParam("grant_type") String grantType,
                           @FormParam("scope") String scope) throws Exception {

        /*Jersey's request.getParameter() always returns null, so we have to reconstruct the entire request ourselves in order to keep data
          See: https://java.net/jira/browse/JERSEY-766
         */

        Map<String, String[]> headers = new HashMap<String, String[]>();

        for(String httpHeaderName : httpHeaders.getRequestHeaders().keySet()) {

            //newBuilder.header(String, String[]);

            List<String> values = httpHeaders.getRequestHeader(httpHeaderName);
            String[] valueArray = new String[values.size()];
            httpHeaders.getRequestHeader(httpHeaderName).toArray(valueArray);
            headers.put(httpHeaderName, valueArray);
        }

        Map<String, String[]> body = new HashMap<String, String[]>();
        String[] grantTypeArray = {grantType};
        String[] scopeArray = {scope};


        body.put("grant_type", grantTypeArray );
        body.put("scope", scopeArray);

        HttpRequest request = HttpRequests.method(HttpMethod.POST).headers(headers).parameters(body).build();

        Application application = StormpathUtils.myClient.getResource(StormpathUtils.applicationHref, Application.class);

        //Build a scope factory
        ScopeFactory scopeFactory = new ScopeFactory(){
            public Set createScope(AuthenticationResult result, Set requestedScopes) {

                //Initialize an empty set, and get the account
                HashSet returnedScopes = new HashSet();
                Account account = result.getAccount();

                /***
                 *  In this simple web application, the scopes that were sent in the body of the request
                 *  are exactly the ones we want to return. If however we were building something more complex, and
                 *  only wanted to allow a scope to be added if it was verified on the server side then we would do something
                 *  as shown in this for loop. The 'allowScopeForAccount()' method would contain the logic which would
                 *  check if the scope is truly allowed for the given account.
                    for(String scope: requestedScopes){
                        if(allowScopeForAccount(account, scope)){
                            returnedScopes.add(scope);
                        }
                    }
                ****/

                return requestedScopes;
            }
        };


        AccessTokenResult oauthResult = application.authenticateOauthRequest(request).using(scopeFactory).execute();
        TokenResponse tokenResponse = oauthResult.getTokenResponse();

        String json = tokenResponse.toJson();

        return json;

    }
}

package com.sample.jersey.app;

import com.stormpath.sdk.api.ApiAuthenticationResult;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationResultVisitorAdapter;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.http.HttpMethod;
import com.stormpath.sdk.http.HttpRequest;
import com.stormpath.sdk.http.HttpRequests;
import com.stormpath.sdk.oauth.AccessTokenResult;
import com.stormpath.sdk.oauth.TokenResponse;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    String applicationHref = "https://api.stormpath.com/v1/applications/5HRNSljrcYQax0oCQQovT9";


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String getToken(@Context HttpHeaders httpHeaders,
                           @Context final HttpServletResponse servletResponse,
                           @FormParam("grant_type") String grantType) throws Exception {

        /*Jersey's request.getParameter() always returns null, so we have to reconstruct the entire request ourselves in order to keep data
          See: https://java.net/jira/browse/JERSEY-766
         */
        Map<String, String[]> headers = new HashMap<String, String[]>();

        for(String httpHeaderName : httpHeaders.getRequestHeaders().keySet()) {

            List<String> values = httpHeaders.getRequestHeader(httpHeaderName);
            String[] valueArray = new String[values.size()];
            httpHeaders.getRequestHeader(httpHeaderName).toArray(valueArray);
            headers.put(httpHeaderName, valueArray);
        }

        Map<String, String[]> body = new HashMap<String, String[]>();
        String[] bodyArray = {grantType};
        body.put("grant_type", bodyArray );

        HttpRequest request = HttpRequests.method(HttpMethod.POST).headers(headers).parameters(body).build();


        StormpathClient stormpathClient = new StormpathClient();
        Client myClient = stormpathClient.getClient();

        Application application = myClient.getResource(applicationHref, Application.class);
        ApiAuthenticationResult apiResult = null;



        //Get a token, send it back to the client
        try {

            final HttpServletResponse httpRespnse = servletResponse;

            apiResult = application.authenticateApiRequest(request);

            apiResult.accept(new AuthenticationResultVisitorAdapter() {
                @Override
                public void visit(AccessTokenResult result) {
                    TokenResponse token = result.getTokenResponse();

                    httpRespnse.setStatus(HttpServletResponse.SC_OK);
                    httpRespnse.setContentType("application/json");

                    System.out.println("Token type: " + token.getTokenType());
                    System.out.println("Token: " + token.getAccessToken());


                    try {
                        httpRespnse.getWriter().print(token.toJson());
                        httpRespnse.getWriter().flush();

                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                }
            });


        } catch (Exception e) {
            servletResponse.sendError(403);
            e.printStackTrace();
        }

        return "";

    }


}

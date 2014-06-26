package com.sample.jersey.app.conf;

import com.stormpath.sdk.tenant.*;
import com.stormpath.sdk.application.*;
import com.stormpath.sdk.client.Client;


public class ApplicationConfiguration {

    public Application getApplication() {


        StormpathClientConfiguration clientConfiguration = new StormpathClientConfiguration();
        Client myClient = clientConfiguration.getClient();
        Application application = myClient.instantiate(Application.class);
        application.setName("Sample Jersey App");
        application = myClient.getCurrentTenant().createApplication(Applications.newCreateRequestFor(application).createDirectory().build());

        return application;
    }
}

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */
package org.mule.connectors.nest;

import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Category;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.ConnectionException;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Processor;
import org.mule.connectors.nest.model.Login;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.client.impl.ClientRequestImpl;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Cloud Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name="nest", schemaVersion="1.0", friendlyName = "Nest Thermostat", minMuleVersion = "3.4.0")
@Category(name = "org.mule.tooling.ui.modules.core.miscellaneous", description = "Miscellaneous")
public class NestConnector
{
    /**
     * The nest username, usually your email address
     */
    @Configurable
    private String username;

    /**
     * The nest password
     */
    @Configurable
    private String password;

    private String baseUri;
    private Client httpClient = Client.create();
    private ObjectMapper mapper = new ObjectMapper();
    private Login login;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Logs in into the NEst service and captures session information
     *
     * @throws ConnectionException if the credentials are incorrect
     * @throws IOException if the response date cannot be parsed
     */
    @Start
    public void login() throws ConnectionException, IOException
    {
        httpClient.addFilter(new LoggingFilter());
        MultivaluedMap<String, String> map = new MultivaluedMapImpl();
        map.putSingle("username", getUsername());
        map.putSingle("password", getPassword());


        WebResource webResource = httpClient.resource("https://home.nest.com/user/login");

        ClientResponse response = webResource.type("application/x-www-form-urlencoded")
                .post(ClientResponse.class, map);

        if (response.getStatus()> 399)
        {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, "unauthorized user", response.toString());
        }
        String res = response.getEntity(String.class);
        System.out.println("Login: " + res);
        login = mapper.readValue(res, Login.class);
        baseUri = login.getUrls().getTransportUrl();

        getMetaData();
    }

    public void getMetaData()
    {
        JsonNode res  = httpClient.resource(getBaseUri() +"/v2/mobile/" + login.getUser())
        .header("X-nl-protocol-version", "1")
        .header("X-nl-user-id", login.getUserId())
        .header("Authorization", "Basic " + login.getAccessToken())
        .header("Host", getBaseUri().substring(getBaseUri().indexOf("//") + 2))
        .get(JsonNode.class);

        for (Iterator<Map.Entry<String, JsonNode>> i = res.get("metadata").getFields(); i.hasNext(); ){

            login.addDeviceId(i.next().getKey());
        }


        System.out.println(res);
        //return res;
    }

    /**
     * Custom processor
     *
     * {@sample.xml ../../../doc/nest-connector.xml.sample nest:my-processor}
     *
     * @param content Content to be processed
     * @return Some string
     */
    @Processor
    public String myProcessor(String content)
    {
        /*
         * MESSAGE PROCESSOR CODE GOES HERE
         */

        return content;
    }

    public String getBaseUri()
    {
        return baseUri;
    }
}

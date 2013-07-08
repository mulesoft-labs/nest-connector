/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */
package org.mule.connectors.nest.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * TODO
 */
public class Login
{
    @JsonProperty("is_superuser")
    private boolean superUser;

    @JsonProperty("is_staff")
    private boolean staff;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("userid")
    private String userId;

    private String user;
    private String email;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty
    private URLs urls;

    @JsonProperty
    private Limits limits;

    @JsonIgnore
    private List<String> deviceIds = new ArrayList<String>();

    public boolean isSuperUser()
    {
        return superUser;
    }

    public boolean isStaff()
    {
        return staff;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getUser()
    {
        return user;
    }

    public String getEmail()
    {
        return email;
    }

    public String getExpiresIn()
    {
        return expiresIn;
    }

    public URLs getUrls()
    {
        return urls;
    }

    public Limits getLimits()
    {
        return limits;
    }

    public List<String> getDeviceIds()
    {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds)
    {
        this.deviceIds = deviceIds;
    }

    public void addDeviceId(String deviceId) {
        this.deviceIds.add(deviceId);
    }
}

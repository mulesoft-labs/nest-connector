/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */
package org.mule.connectors.nest.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * TODO
 */
public class URLs
{
    @JsonProperty("transport_url")
    private String transportUrl;
    @JsonProperty("rubyapi_url")
    private String rubyapiUrl;
    @JsonProperty("weather_url")
    private String weatherUrl;
    @JsonProperty("support_url")
    private String supportUrl;

    @JsonProperty("log_upload_url")
    private String logUploadUrl;

    public String getLogUploadUrl()
    {
        return logUploadUrl;
    }

    public String getTransportUrl()
    {
        return transportUrl;
    }

    public String getRubyapiUrl()
    {
        return rubyapiUrl;
    }

    public String getWeatherUrl()
    {
        return weatherUrl;
    }

    public String getSupportUrl()
    {
        return supportUrl;
    }
}

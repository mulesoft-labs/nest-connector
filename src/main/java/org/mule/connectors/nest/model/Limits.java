/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */
package org.mule.connectors.nest.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * TODO
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Limits
{
    @JsonProperty("thermostats_per_structure")
    private int thermostatsPerStructure;

    private int structures;
    private int thermostats;

    public int getThermostatsPerStructure()
    {
        return thermostatsPerStructure;
    }

    public int getStructures()
    {
        return structures;
    }

    public int getThermostats()
    {
        return thermostats;
    }
}

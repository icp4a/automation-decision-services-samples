/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2021. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package ads.samples.geoLibrary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ilog.rules.bom.annotations.BeanConstructor;
import ilog.rules.bom.annotations.NotBusiness;

public class Country {
    private String name;
    private ads.samples.geoLibrary.Location capital;

    @BeanConstructor
    @JsonCreator
    public Country(@JsonProperty("name")String name, @JsonProperty("capital")ads.samples.geoLibrary.Location capital) {
        this.name = name;
        this.capital = capital;
        capital.setCountry(this);
    }

    public String getName() {
        return name;
    }

    public Location getCapital() {
        return capital;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapital(ads.samples.geoLibrary.Location capital) {
        this.capital = capital;
    }

}

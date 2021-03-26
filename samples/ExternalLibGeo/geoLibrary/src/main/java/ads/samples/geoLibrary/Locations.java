/*
 * Licensed Materials - Property of IBM
 * 5737-I23
 * Copyright IBM Corp. 2018 - 2021. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */

package ads.samples.geoLibrary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.rules.engine.annotations.PureFunction;
import ilog.rules.bom.annotations.BeanConstructor;
import ilog.rules.bom.annotations.NotBusiness;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Java class not visible for business users.
 */
@NotBusiness
public class Locations {
    private static final int CAPITAL_INDEX = 1;
    private static final int COUNTRY_INDEX = 0;
    private static final int LONGITUDE_INDEX = 3;
    private static final int LATITUDE_INDEX = 2;
    private static final String COMMA_DELIMITER = ",";

    private HashMap<String, Location> locations;
    private HashMap<String, Country> countries;

    @BeanConstructor
    @JsonCreator
    public Locations(String fileName) {
        locations = new HashMap<String, Location>();
        countries = new HashMap<String, Country>();
        readLocations(fileName);
    }

    public void readLocations(String fileName) {
        Location capital;
        Country country;
        try {
            InputStream is = Locations.class.getResourceAsStream("/" + fileName);
            String sCurrentLine;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                capital = new Location(values[CAPITAL_INDEX],
                        Double.parseDouble(values[LONGITUDE_INDEX]),
                        Double.parseDouble(values[LATITUDE_INDEX]));
                country = new Country(values[COUNTRY_INDEX], capital);
                locations.put(values[CAPITAL_INDEX], capital);
                countries.put(values[COUNTRY_INDEX], country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PureFunction
    @JsonIgnore
    public Location getLocation(String name) {
        return locations.get(name);
     }

    @PureFunction
    @JsonIgnore
    public Country getCountry(String name) {
        return countries.get(name);
    }

}

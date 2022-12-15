/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2021. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */

package ads.samples.geoLibrary;
import com.ibm.rules.engine.annotations.PureFunction;
import ilog.rules.bom.annotations.NotBusiness;

/**
 * Expose static methods about Location.
 */
public class LocationsUtilities {
    // Initialization: the known locations are read from a csv file taken into the jar file.
    static private Locations  LOCATIONS  = new Locations("cities.csv");

    @NotBusiness
    private LocationsUtilities() {}

    @PureFunction
    static public boolean knownCity(String name){
        return (LOCATIONS.getLocation(name) != null);
    }

    @PureFunction
    static public boolean unknownCity(String name){
        return (LOCATIONS.getLocation(name) == null);
    }

    @PureFunction
    static public boolean knownCountry(String name){
        return (LOCATIONS.getCountry(name) != null);
    }

    @PureFunction
    static public boolean unknownCountry(String name){
        return (LOCATIONS.getCountry(name) == null);
    }

    @PureFunction
    static public int getDistance(String from, String to){
        Location fromLocation = LOCATIONS.getLocation(from);
        Location toLocation = LOCATIONS.getLocation(to);
        float distance = -1;
        if (fromLocation != null && toLocation != null)
            distance = fromLocation.getDistance(toLocation);
        return (int)distance;
    }

    @PureFunction
    static public Country getCountry(String name){
        return LOCATIONS.getCountry(name);
    }

    @PureFunction
    static public Location getLocation(String name){
        return LOCATIONS.getLocation(name);
    }
}

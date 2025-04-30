/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2021. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */

package ads.samples.geolibrary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.rules.engine.annotations.PureFunction;
import ilog.rules.bom.annotations.BeanConstructor;
import ilog.rules.bom.annotations.NotBusiness;

import java.util.ArrayList;
import java.util.List;

public class Location {

   private String name;
    @JsonIgnore
    private Country country;
    private double longitude;
    private double latitude;

    public Location(){}

    @BeanConstructor
    @JsonCreator
    public Location(@JsonProperty("name") String name, @JsonProperty("longitude") double longitude,
                    @JsonProperty("latitude") double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @PureFunction
    @JsonIgnore
    public float getDistance(Location location) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(location.getLatitude()- latitude);
        double dLng = Math.toRadians(location.getLongitude()-longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(location.getLatitude())) *
                            Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist/1000; // kilometers
    }

    // Getter setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Country getCountry() {
        return country;
    }

    //Setter
    @NotBusiness
    public void setCountry(Country country) {
        this.country = country;
    }

    @NotBusiness
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Location {" +
                "name='" + name + '\'' +
                "longitude='" + longitude + '\'' +
                "latitude='" + latitude + '\'' +
            '}';
    }


}

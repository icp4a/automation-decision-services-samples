/*
 * Licensed Materials - Property of IBM
 * 5737-I23
 * Copyright IBM Corp. 2018 - 2020. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.package com.ibm.ads.samples;
 */

package ads.samples.externalLibrary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.rules.engine.annotations.PureFunction;
import ilog.rules.bom.annotations.NotBusiness;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String inputName;
    private String firstName;
    private String lastName;
    private List<String> middleNames;
    private Country country;

    @JsonCreator
    public Person(@JsonProperty("inputName") String inputName, @JsonProperty("country") Country country) {
        this.inputName = inputName;
        this.country = country;
        parseName();
    }

    private void parseName() {
        String[] names = inputName.trim().split("\\s+");
        firstName = "";
        lastName = "";
        middleNames = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String capitalName = capitalize(names[i]);
           if (i == 0)
            firstName = capitalName;
           else if (i == names.length - 1)
            lastName = capitalName;
           else
            middleNames.add(capitalName);
        }
    }

    @PureFunction
    public String capitalize(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @PureFunction
    @JsonIgnore
    public String fullName() {
        if (middleNames.size() == 0)
            return firstName() + " " + lastName();
        StringBuilder middle = new StringBuilder(firstName());
        middle.append(' ');
        for (String middleName : middleNames) middle.append(middleName).append(' ');
        middle.append(lastName());
        return middle.toString();
    }

    @PureFunction
    @JsonIgnore
    public String initials() {
        String initials = firstName().substring(0,1);
        if (lastName().isEmpty())
            return initials;
        return initials + lastName().substring(0,1);
    }

    @PureFunction
    @JsonIgnore
    public String greeting() {
        if (country == Country.FRANCE)
            return "Salut";
        return "Hello";
    }

    @PureFunction
    @JsonIgnore
    public String shortName() {
        if (middleNames.size() == 0)
            return firstName() + " " + lastName();
        StringBuilder middle = new StringBuilder(firstName());
        middle.append(' ');
        for (String middleName : middleNames) middle.append(middleName, 0, 1).append(' ');
        middle.append(lastName());
        return middle.toString();
    }

    // Getter
    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    @PureFunction
    @JsonIgnore
    public String firstName() {
        return firstName;
    }

    @PureFunction
    @JsonIgnore
    public String lastName() {
        return lastName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return inputName.equals(person.inputName);
    }

    @Override
    @NotBusiness
    public int hashCode() {
        return inputName.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
            "inputName='" + inputName + '\'' +
            '}';
    }
}

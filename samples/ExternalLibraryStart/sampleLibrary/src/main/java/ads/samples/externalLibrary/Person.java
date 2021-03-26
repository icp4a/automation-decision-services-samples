/*
 * Licensed Materials - Property of IBM
 * 5737-I23
 * Copyright IBM Corp. 2018 - 2020. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */

package ads.samples.externalLibrary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.rules.engine.annotations.PureFunction;
import ilog.rules.bom.annotations.BeanConstructor;
import ilog.rules.bom.annotations.NotBusiness;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String inputName;
    private String firstName;
    private String lastName;
    private List<String> middleNames;
    private Country country;

    @BeanConstructor
    @JsonCreator
    public Person(@JsonProperty("inputName") String inputName, @JsonProperty("country") Country country) {
        this.inputName = inputName;
        this.country = country;
        parseName();
    }

    @PureFunction
    @JsonIgnore
    public String getFullName() {
        if (middleNames.size() == 0)
            return getFirstName() + " " + getLastName();
        StringBuilder middle = new StringBuilder(getFirstName());
        middle.append(' ');
        for (String middleName : middleNames) middle.append(middleName).append(' ');
        middle.append(getLastName());
        return middle.toString();
    }

    @PureFunction
    @JsonIgnore
    public String getInitials() {
        String initials = StringUtilities.firstLetter(getFirstName());
        if (getLastName().isEmpty())
            return initials;
        return StringUtilities.firstLetter(getFirstName()) + StringUtilities.firstLetter(getLastName());
    }

    @PureFunction
    @JsonIgnore
    public String getGreeting() {
        if (country == Country.FRANCE)
            return "Salut";
        if (country == Country.GERMANY)
            return "Hallo";
        if (country == Country.ITALY)
            return "Ciao";
        return "Hello";
    }

    @PureFunction
    @JsonIgnore
    public String getShortName() {
        if (middleNames.size() == 0)
            return getFirstName() + " " + getLastName();
        StringBuilder middle = new StringBuilder(getFirstName());
        middle.append(' ');
        for (String middleName : middleNames) middle.append(middleName, 0, 1).append(' ');
        middle.append(getLastName());
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
    public String getFirstName() {
        return firstName;
    }

    @PureFunction
    @JsonIgnore
    public String getLastName() {
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

    // Utilities

    private void parseName() {
        String[] names = inputName.trim().split("\\s+");
        firstName = "";
        lastName = "";
        middleNames = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String capitalName = StringUtilities.capitalize(names[i]);
            if (i == 0)
                firstName = capitalName;
            else if (i == names.length - 1)
                lastName = capitalName;
            else
                middleNames.add(capitalName);
        }
    }
}

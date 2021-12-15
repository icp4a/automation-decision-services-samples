/*
 * Licensed Materials - Property of IBM
 * 5737-I23
 * Copyright IBM Corp. 2018 - 2020. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package ads.samples.externalLibrary;

import com.ibm.rules.engine.annotations.PureFunction;
import ilog.rules.bom.annotations.NotBusiness;

public class StringUtilities {
    private StringUtilities() {}

    @PureFunction
    static public String capitalize(String name) {
        return name.toUpperCase();
    }

    @NotBusiness
    static public String capitalizeFirstLetter(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @PureFunction
    static public String firstLetter(String name) {
        if (name == null | name.isEmpty()) return "";
        return name.substring(0,1);
    }

}

/*
 * Licensed Materials - Property of IBM
 * 5737-I23
 * Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package com.ibm.ads.samples.quarkus.withML;

public class JsonUtils {

    private JsonUtils() { /* leave me blank */ }

    /**
     * Replace all quotes (') but double-quote (")
     * @param str the string to replace
     */
    public static String toString(String str) {
        return str.replaceAll("'", "\"");
    }

    /**
     * Format the specified string,
     * and replace all quotes (') by double-quote (")
     * @param str the specified string
     * @param args the format arguments to use
     * @return
     */
    public static String toString(String str, Object... args) {
        return toString(String.format(str, args));
    }
}
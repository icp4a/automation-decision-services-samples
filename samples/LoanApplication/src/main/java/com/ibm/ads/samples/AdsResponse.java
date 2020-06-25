/*
 * Licensed Materials - Property of IBM
 * 5737-I23
 * Copyright IBM Corp. 2018 - 2020. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.package com.ibm.ads.samples;
*/
package com.ibm.ads.samples;

public class AdsResponse {
    public int status;
    public String payload;

    public static final int SUCCESS = 200;
    public static final int NOTFOUND = 404;
    public static final int BADREQUEST = 400;

}

/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2021. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */

package ads.samples.geolibrary;

import ads.samples.geolibrary.Country;
import ads.samples.geolibrary.Locations;
import ads.samples.geolibrary.LocationsUtilities;
import junit.framework.TestCase;

public class LocationTest extends TestCase {
  private static final double PARIS_LATITUDE = 48.856614;
  private static final double PARIS_LONGITUDE = 2.352222;
  private static final double WASHINGTON_LATITUDE = 38.907192;
  private static final double WASHINGTON_LONGITUDE = -77.036871;
  private static final float WASHINGTON_PARIS = (float)6164.81;

  public void testDistanceParisNice() {
    ads.samples.geolibrary.Location paris = new ads.samples.geolibrary.Location("Paris", PARIS_LONGITUDE, PARIS_LATITUDE);
    ads.samples.geolibrary.Location nice = new ads.samples.geolibrary.Location("Nice", 7.2661, 43.7031);
    ads.samples.geolibrary.Location washington = new ads.samples.geolibrary.Location("Washington", WASHINGTON_LONGITUDE, WASHINGTON_LATITUDE);
    assertEquals((float)685.953, paris.getDistance(nice));
    assertEquals(WASHINGTON_PARIS, paris.getDistance(washington));
  }

  public void testReadCsv() {
    Locations locations = new Locations("cities.csv");
    ads.samples.geolibrary.Location paris = locations.getLocation("Paris");
    assertEquals(paris.getLatitude(), PARIS_LATITUDE);
    assertEquals(paris.getLongitude(), PARIS_LONGITUDE);
    ads.samples.geolibrary.Location washington = locations.getLocation("Washington");
    assertEquals(washington.getLatitude(), WASHINGTON_LATITUDE);
    assertEquals(washington.getLongitude(), WASHINGTON_LONGITUDE);
  }

  public void testLocationsUtilities() {
    assertTrue(LocationsUtilities.knownCity("Paris"));
    assertFalse(LocationsUtilities.knownCity("Nice"));
    assertEquals((int)WASHINGTON_PARIS, LocationsUtilities.getDistance("Paris", "Washington"));
    assertEquals(-1, LocationsUtilities.getDistance("Paris", "Nice"));
    Country france = LocationsUtilities.getCountry("France");
    assertEquals("Paris", france.getCapital().getName());
  }

}

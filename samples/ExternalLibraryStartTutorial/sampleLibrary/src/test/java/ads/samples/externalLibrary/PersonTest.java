package ads.samples.externalLibrary;

import junit.framework.TestCase;
import ads.samples.externalLibrary.Country;

public class PersonTest extends TestCase {
  public void testPersonWithMiddleName() {
    Person person = new Person("Aretha Louise Franklin", Country.US, 56);
    assertEquals("Aretha Louise Franklin", person.getInputName());
    assertEquals("Aretha", person.getFirstName());
    assertEquals("Franklin", person.getLastName());
    assertEquals("AF", person.getInitials());
    assertEquals("Aretha L Franklin", person.getShortName());
  }

  public void testPersonWithoutMiddleName() {
    Person person = new Person("Marie Curie", Country.FRANCE, 33);
    assertEquals("Marie Curie", person.getInputName());
    assertEquals("Marie", person.getFirstName());
    assertEquals("Curie", person.getLastName());
    assertEquals("MC", person.getInitials());
    assertEquals("Marie Curie", person.getShortName());

  }
}

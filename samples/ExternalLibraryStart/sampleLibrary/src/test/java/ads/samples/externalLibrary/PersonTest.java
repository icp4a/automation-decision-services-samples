package ads.samples.externalLibrary;

import junit.framework.TestCase;
import ads.samples.externalLibrary.Country;

public class PersonTest extends TestCase {
  public void testPersonWithMiddleName() {
    Person person = new Person("Aretha Louise Franklin", Country.US);
    assertEquals("Aretha Louise Franklin", person.getInputName());
    assertEquals("Aretha", person.firstName());
    assertEquals("Franklin", person.lastName());
    assertEquals("AF", person.initials());
    assertEquals("Aretha L Franklin", person.shortName());
  }

  public void testPersonWithoutMiddleName() {
    Person person = new Person("Marie Curie", Country.FRANCE);
    assertEquals("Marie Curie", person.getInputName());
    assertEquals("Marie", person.firstName());
    assertEquals("Curie", person.lastName());
    assertEquals("MC", person.initials());
    assertEquals("Marie Curie", person.shortName());

  }
}

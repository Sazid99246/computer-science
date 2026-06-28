package cs3500.hw01.publication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConferenceProceedingsTest {
  private ConferenceProceedings proceedings;

  @Before
  public void setUp() {
    // Sample data matching a realistic conference proceeding setup
    proceedings = new ConferenceProceedings(
            "Smith",
            "John",
            "Proceedings of the International Conference on Computer Science",
            "Austin, TX",
            2026,
            "ACM Press"
    );
  }

  @Test
  public void testGetMlaCitation() {
    String expectedMla = "Smith, John, editor. Proceedings of the International Conference on Computer Science. Austin, TX, ACM Press, 2026.";
    assertEquals("The MLA citation does not match the expected format.", expectedMla, proceedings.citeMla());
  }

  @Test
  public void testGetApaCitation() {
    String expectedApa = "Smith, J. (Ed.). (2026). Proceedings of the International Conference on Computer Science. ACM Press.";
    assertEquals("The APA citation does not match the expected format.", expectedApa, proceedings.citeApa());
  }
}
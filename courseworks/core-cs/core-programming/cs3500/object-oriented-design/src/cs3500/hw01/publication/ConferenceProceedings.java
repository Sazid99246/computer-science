package cs3500.hw01.publication;

public class ConferenceProceedings implements Publication {
  private final String editorLastName, editorFirstName, title, location, publisher;
  private final int year;

  /**
   * Constructs a new ConferenceProceedings instance with the specified editor details,
   * title, location, publication year, and publisher.
   *
   * @param editorLastName  the last name of the conference proceedings editor
   * @param editorFirstName the first name of the conference proceedings editor
   * @param title           the title of the conference proceedings
   * @param location        the geographic location where the conference was held
   * @param year            the four-digit year the proceedings were published
   * @param publisher       the name of the publishing company or organization
   */
  public ConferenceProceedings(String editorLastName, String editorFirstName, String title,
                               String location, int year, String publisher) {
    this.editorLastName = editorLastName;
    this.editorFirstName = editorFirstName;
    this.title = title;
    this.location = location;
    this.year = year;
    this.publisher = publisher;
  }

  @Override
  public String citeApa() {
    // Extract the first initial from the first name
    char firstInitial = (editorFirstName != null && !editorFirstName.isEmpty()) ? editorFirstName.charAt(0) : ' ';
    return editorLastName + ", " + firstInitial + ". (Ed.). (" + year + "). " + title + ". " + publisher + ".";
  }

  @Override
  public String citeMla() {
    return editorLastName + ", " + editorFirstName + ", editor. " + title + ". " + location + ", " + publisher + ", " + year + ".";
  }
}
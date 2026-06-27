package cs3500.lec02;

/**
 * Specifies operations for formatting citations from bibliographic data.
 */
public interface Publication {
    /**
     * Formats a citation in APA style.
     *
     * @return the formatted citation
     */
    String citeApa();

    /**
     * Formats a citation in MLA style.
     *
     * @return the formatted citation
     */
    String citeMla();
}

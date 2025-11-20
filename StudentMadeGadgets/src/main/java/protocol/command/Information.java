package protocol.command;

/**
 * Command representing information.
 */
public class Information implements Command {
  String information;

  /**
   * Constructor for Information.
   *
   * @param information the information string
   */
  public Information(String information) {
    this.information = information;
  }

  /**
   * Gets the information string.
   *
   * @return the information string
   */
  public String getInformation() {
    return this.information;
  }
}

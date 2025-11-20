package protocol.command;

/**
 * Command to create a new greenhouse with a specified name.
 */
public class CreateGreenhouse implements Command {
  private String name;

  /**
   * Constructor for CreateGreenhouse.
   *
   * @param name the name of the new greenhouse
   */
  public CreateGreenhouse(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the new greenhouse.
   *
   * @return the name of the greenhouse
   */
  public String getName() {
    return this.name;
  }
}

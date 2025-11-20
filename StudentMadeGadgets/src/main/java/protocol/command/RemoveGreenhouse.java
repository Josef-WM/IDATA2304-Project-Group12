package protocol.command;

/**
 * Command to remove a greenhouse by its ID.
 */
public class RemoveGreenhouse implements Command {
  private int id;

  /**
   * Constructor for RemoveGreenhouse.
   *
   * @param id the ID of the greenhouse to be removed
   */
  public RemoveGreenhouse(int id) {
    this.id = id;
  }

  /**
   * Gets the ID of the greenhouse to be removed.
   *
   * @return the ID of the greenhouse
   */
  public int getId() {
    return this.id;
  }
}

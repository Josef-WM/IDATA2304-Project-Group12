package protocol.command;

/**
 * Command to add an actuator to a greenhouse.
 */
public class AddActuator implements Command {
  int greenhouseId;
  String actuatorType;

  /**
   * Constructor for AddActuator.
   *
   * @param greenhouseId the ID of the greenhouse
   * @param actuatorType the type of actuator to be added
   */
  public AddActuator(int greenhouseId, String actuatorType) {
    this.greenhouseId = greenhouseId;
    this.actuatorType = actuatorType;
  }

  /**
   * Gets the greenhouse ID.
   *
   * @return the greenhouse ID
   */
  public int getGreenhouseId() {
    return this.greenhouseId;
  }

  /**
   * Gets the actuator type.
   *
   * @return the actuator type
   */
  public String getActuatorType() {
    return this.actuatorType;
  }
}

package protocol.command;

/**
 * Command to add a sensor to a greenhouse.
 */
public class AddSensor implements Command {
  int greenhouseId;
  String sensorType;

  /**
   * Constructor for AddSensor.
   *
   * @param greenhouseId the ID of the greenhouse
   * @param sensorType   the type of sensor to be added
   */
  public AddSensor(int greenhouseId, String sensorType) {
    this.greenhouseId = greenhouseId;
    this.sensorType = sensorType;
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
   * Gets the sensor type.
   *
   * @return the sensor type
   */
  public String getSensorType() {
    return this.sensorType;
  }
}

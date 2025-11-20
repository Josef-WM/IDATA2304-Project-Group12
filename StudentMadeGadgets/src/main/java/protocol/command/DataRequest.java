package protocol.command;

/**
 * Command representing a data request for a specific device in a greenhouse.
 */
public class DataRequest implements Command {
  private int greenhouseID;
  private String deviceID;
  private String deviceType;

  /**
   * Constructor for DataRequest.
   *
   * @param greenhouseID the ID of the greenhouse
   * @param deviceID     the ID of the device
   * @param deviceType   the type of the device
   */
  public DataRequest(int greenhouseID, String deviceID, String deviceType) {
    this.greenhouseID = greenhouseID;
    this.deviceID = deviceID;
    this.deviceType = deviceType;
  }

  /**
   * Gets the greenhouse ID.
   *
   * @return the greenhouse ID
   */
  public int getGreenhouseID() {
    return this.greenhouseID;
  }

  /**
   * Gets the device type.
   *
   * @return the device type
   */
  public String getdeviceType() {
    return this.deviceType;
  }

  /**
   * Gets the device ID.
   *
   * @return the device ID
   */
  public String getDeviceID() {
    return this.deviceID;
  }
}

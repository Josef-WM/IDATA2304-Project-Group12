package protocol.command;

public class DataRequest implements Command {
  private int greenhouseID;
  private String deviceID;
  private String deviceType;

  public DataRequest(int greenhouseID, String deviceID, String deviceType) {
    this.greenhouseID = greenhouseID;
    this.deviceID = deviceID;
    this.deviceType = deviceType;
  }

  public int getGreenhouseID() {
    return this.greenhouseID;
  }

  public String getdeviceType() {
    return this.deviceType;
  }

  public String getDeviceID() {
    return this.deviceID;
  }
}

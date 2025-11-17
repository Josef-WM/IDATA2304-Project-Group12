package protocol.command;

public class DataRequest implements Command {
  private String deviceID;
  private String dataType;

  public DataRequest(String deviceID, String dataType) {
    this.deviceID = deviceID;
    this.dataType = dataType;
  }

  public DataRequest(String deviceID) {
    this.deviceID = deviceID;
  }

  public String getDataType() {
    return this.dataType;
  }

  public String getDeviceID() {
    return this.deviceID;
  }
}

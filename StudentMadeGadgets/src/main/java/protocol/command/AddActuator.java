package protocol.command;

public class AddActuator implements Command {
  int greenhouseId;
  String actuatorType;

  public AddActuator(int greenhouseId, String actuatorType) {
    this.greenhouseId = greenhouseId;
    this.actuatorType = actuatorType;
  }

  public int getGreenhouseId() {
    return this.greenhouseId;
  }

  public String getActuatorType() {
    return this.actuatorType;
  }
}

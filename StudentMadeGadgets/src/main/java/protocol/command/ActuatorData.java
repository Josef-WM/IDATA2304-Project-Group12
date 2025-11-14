package protocol.command;

public class ActuatorData extends Command {
  private String actuatorType;
  private int power;
  private boolean isOn;

  public ActuatorData(String actuatorType, boolean isOn) {
    this.actuatorType = actuatorType;
    this.isOn = isOn;
  }

  public String getActuatorType() {
    return this.actuatorType;
  }

  public int getPower() {
    return this.power;
  }

  public boolean isOn() {
    return this.isOn;
  }
}

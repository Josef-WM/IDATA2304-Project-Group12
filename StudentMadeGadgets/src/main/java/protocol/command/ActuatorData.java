package protocol.command;

public class ActuatorData extends Command {
  private String actuatorType;
  private boolean isOn;
  private int power;

  public ActuatorData(String actuatorType, boolean isOn, int power) {
    this.actuatorType = actuatorType;
    this.isOn = isOn;
    this.power = power;
  }

  public String getActuatorType() {
    return this.actuatorType;
  }

  public boolean isOn() {
    return this.isOn;
  }

  public int getPower() {
    return this.power;
  }
}

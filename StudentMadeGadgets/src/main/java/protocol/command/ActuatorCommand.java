package protocol.command;

import actuator.Actuator;

import java.util.ArrayList;

public class ActuatorCommand implements Command {
  private int greenhouseId;
  private String actuatorId;
  private int power = -1;
  private boolean turnOn;

  public ActuatorCommand(int greenhouseId, String actuatorId, boolean turnOn) {
    this.greenhouseId = greenhouseId;
    this.actuatorId = actuatorId;
    this.turnOn = turnOn;
  }

  public ActuatorCommand(int greenhouseId, String actuatorId, int power) {
    this.greenhouseId = greenhouseId;
    this.actuatorId = actuatorId;
    this.power = power;
  }

  public int getGreenhouseId() {
    return this.greenhouseId;
  }

  public String getActuatorId() {
    return this.actuatorId;
  }

  public int getPower() {
    return this.power;
  }

  public boolean isTurnOn() {
    return this.turnOn;
  }
}

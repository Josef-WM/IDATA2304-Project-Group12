package protocol.command;

import actuator.Actuator;

import java.util.ArrayList;

/**
 * Command to control an actuator in a greenhouse.
 */
public class ActuatorCommand implements Command {
  private int greenhouseId;
  private String actuatorId;
  private int power = -1;
  private boolean turnOn;

  /**
   * Constructor for ActuatorCommand to turn on/off an actuator.
   *
   * @param greenhouseId the ID of the greenhouse
   * @param actuatorId   the ID of the actuator
   * @param turnOn       true to turn on, false to turn off
   */
  public ActuatorCommand(int greenhouseId, String actuatorId, boolean turnOn) {
    this.greenhouseId = greenhouseId;
    this.actuatorId = actuatorId;
    this.turnOn = turnOn;
  }

  /**
   * Constructor for ActuatorCommand to set power of an actuator.
   *
   * @param greenhouseId the ID of the greenhouse
   * @param actuatorId  the ID of the actuator
   * @param power      the power level to set
   */
  public ActuatorCommand(int greenhouseId, String actuatorId, int power) {
    this.greenhouseId = greenhouseId;
    this.actuatorId = actuatorId;
    this.power = power;
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
   * Gets the actuator ID.
   *
   * @return the actuator ID
   */
  public String getActuatorId() {
    return this.actuatorId;
  }

  /**
   * Gets the power level.
   *
   * @return the power level
   */
  public int getPower() {
    return this.power;
  }

  /**
   * Checks if the command is to turn on the actuator.
   *
   * @return true if the command is to turn on, false otherwise
   */
  public boolean isTurnOn() {
    return this.turnOn;
  }
}

package actuator;

import greenhouse.Greenhouse;

/**
 * Heater actuator class that implements the Actuator interface.
 * will be used to control the heater in the greenhouse.
 */
public class HeaterActuator implements Actuator {
  private String ID;
  private boolean isOn;

  /**
   * constructor for the HeatActuator class.
   */
  public HeaterActuator() {
    this.isOn = false;
  }

  /**
   * Returns the id of the heater.
   */
  @Override
  public String getID() {
    return this.ID;
  }

  /**
   * Sets the id of the heater.
   * @param ID
   */
  @Override
  public void setID(String ID) {
    this.ID = ID;
  }

  /**
   * Returns the type of the actuator
   */
  @Override
  public String getType() {
    return "Heater";
  }

  /**
   * Returns whether the actuator is on.
   */
  @Override
  public boolean isOn() {
    return this.isOn;
  }

  /**
   * turns on the actuator.
   */
  public void turnOn(Greenhouse greenhouse) {
    isOn = true;
  }

  /**
   * turns off the actuator.
   */
  public void turnOff(Greenhouse greenhouse) {
    isOn = false;
  }

  /**
   * Toggles the actuator
   * Turning it on if off
   * And off if on.
   */
  public boolean toggle(Greenhouse greenhouse) {
    if (isOn) {
      turnOff(greenhouse);
      return false;
    } else {
      turnOn(greenhouse);
      return true;
    }
  }

  /**
   * Sets the state of the actuator.
   */
  public void setState(boolean state, Greenhouse greenhouse) {
    if (state) {
      turnOn(greenhouse);
    } else {
      turnOff(greenhouse);
    }
  }
}

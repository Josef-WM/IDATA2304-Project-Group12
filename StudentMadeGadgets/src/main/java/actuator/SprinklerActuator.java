package actuator;

import greenhouse.Greenhouse;

public class SprinklerActuator implements Actuator {
  private String ID;
  private boolean isOn;

  /**
   * constructor for the SprinklerActuator class.
   */
  public SprinklerActuator() {
    this.isOn = false;
  }

  @Override
  public String getID() {
    return this.ID;
  }

  @Override
  public void setID(String ID) {
    this.ID = ID;
  }

  @Override
  public String getType() {
    return "Light";
  }

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
}

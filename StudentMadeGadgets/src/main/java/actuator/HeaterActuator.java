package actuator;

import greenhouse.Greenhouse;

/**
 * Represents a heater, acts as an actuator in a greenhouse.
 */
public class HeaterActuator implements Actuator {
  private String ID;
  private boolean isOn;
  private int power;

  /**
   * constructor for the HeaterActuator class.
   */
  public HeaterActuator() {
    this.isOn = false;
    this.power = 0;
  }

  /**
   * Returns the type of the actuator
   */
  public String getType() {
    return "Heater";
  }

  /**
   * Returns the id of the actuator.
   */
  public String getID() {
    return this.ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  /**
   * turns on the actuator.
   */
  public void turnOn(Greenhouse greenhouse) {
    if (!isOn) {
      greenhouse.changeTemperature(getEffect());
      isOn = true;
    }
  }

  /**
   * turns off the actuator.
   */
  public void turnOff(Greenhouse greenhouse) {
    if (isOn) {
      greenhouse.changeTemperature(-getEffect());
      isOn = false;
    }
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

  public void setState(boolean state, Greenhouse greenhouse) {
    if (state) {
      turnOn(greenhouse);
    } else {
      turnOff(greenhouse);
    }
  }

  /**
   * Returns the isOn() state.
   *
   * @return boolean isOn() state
   */
  public boolean isOn() {
    return this.isOn;
  }

  /**
   * Gets the power of the actuator.
   */
  public int getPower() {
    return this.power;
  }

  /**
   * sets the speed of the actuator.
   */
  public void setPower(int power, Greenhouse greenhouse) {
    double oldEffect = getEffect();
    this.power = power;
    double newEffect = getEffect();

    if (isOn) {
      greenhouse.changeTemperature(newEffect - oldEffect);
    }
  }

  public double getEffect() {
    return (3 + (2*this.power));
  }
}
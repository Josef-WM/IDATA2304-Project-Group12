package actuator;

import greenhouse.Greenhouse;

/**
 * Represents a heater, acts as an actuator in a greenhouse.
 */
public class LightActuator implements Actuator {
  private String ID;
  private boolean isOn;
  private int power;

  /**
   * constructor for the HeaterActuator class.
   */
  public LightActuator() {
    this.isOn = false;
    this.power = 0;
  }

  /**
   * Returns the type of the actuator.
   */
  public String getType() {
    return "Light";
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
      greenhouse.changeLight(getEffect());
      isOn = true;
    }
  }

  /**
   * turns off the actuator.
   */
  public void turnOff(Greenhouse greenhouse) {
    if (isOn) {
      greenhouse.changeLight(-getEffect());
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

  /**
   * Sets the state of the actuator.
   *
   * @param state desired state
   * @param greenhouse the greenhouse the actuator is in
   */
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
    int oldEffect = getEffect();
    this.power = power;
    int newEffect = getEffect();

    if (isOn) {
      greenhouse.changeLight(newEffect - oldEffect);
    }
  }

  /**
   * Calculates the effect of the actuator based on its power.
   *
   * @return the effect of the actuator
   */
  public int getEffect() {
    return (500 + (300*this.power));
  }
}
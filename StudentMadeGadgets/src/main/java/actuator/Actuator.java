package actuator;

import greenhouse.Greenhouse;

/**
 * Actuator interface for all actuators in the greenhouse system.
 */
public interface Actuator {
  String getID();
  void setID(String ID);
  String getType();
  boolean isOn();
  void setState(boolean state, Greenhouse greenhouse);
  boolean toggle(Greenhouse greenhouse);
  void setPower(int power, Greenhouse greenhouse);
  default int getPower() { return -1; }

  default String format() {
    return getType() + ": isOn-" + isOn() + " power-" + getPower();
  }
}

package actuator;

import greenhouse.Greenhouse;

public interface Actuator {
  String getID();
  void setID(String ID);
  String getType();
  boolean isOn();
  boolean toggle(Greenhouse greenhouse);
  default void setPower(int power) {};
  default int getPower() { return -1; }

  default String format() {
    return getType() + ": isOn-" + isOn() + " power-" + getPower();
  }
}

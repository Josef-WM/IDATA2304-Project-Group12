package Actuator;

public interface Actuator {
  String getID();
  default String getType() {
    return null;
  }
  boolean isOn();
  boolean toggle();
}

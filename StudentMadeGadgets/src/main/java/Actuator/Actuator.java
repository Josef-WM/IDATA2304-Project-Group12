package Actuator;

public interface Actuator {
  String getID();
  String getType();
  boolean isOn();
  void toggle();
}

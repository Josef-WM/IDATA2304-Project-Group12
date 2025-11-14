package actuator;

public interface Actuator {
  String getID();
  String getType();
  boolean isOn();
  boolean toggle();
  default void setPower(int power) {};
  default int getPower() { return -1; }

  default String format() {
    return getType() + ": isOn-" + isOn() + " power-" + getPower();
  }
}

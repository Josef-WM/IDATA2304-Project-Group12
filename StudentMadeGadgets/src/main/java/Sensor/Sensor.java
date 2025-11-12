package Sensor;

public interface Sensor {
  String getId();
  String getType();
  String getUnit();
  double read();

  default String format() {
    return getType() + ": " + read() + " " + getUnit();
  }
}

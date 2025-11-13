package sensor;

public interface Sensor {
  String getID();
  String getType();
  String getUnit();
  double read();

  default String format() {
    return getType() + ": " + read() + " " + getUnit();
  }
}

package sensor;

public interface Sensor {
  String getID();
  void setID(String ID);
  String getType();
  String getUnit();
  double read();

  default String format() {
    return getType() + ": " + read() + " " + getUnit();
  }
}

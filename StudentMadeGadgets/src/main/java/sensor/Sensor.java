package sensor;
/**
 * This interface represents a generic sensor in a greenhouse system.
 * It defines methods to get the sensor's ID, type, unit of measurement,
 * and to read the sensor's value. It also includes a default method
 * to format the sensor's reading as a string.
 */
public interface Sensor {
  String getID();
  void setID(String ID);
  String getType();
  String getUnit();
  double read();

  /**
   * Formats the sensor's reading as a string.
   * @return a formatted string representing the sensor's type, reading, and unit
   */
  default String format() {
    return getType() + ": " + read() + " " + getUnit();
  }
}

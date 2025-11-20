package protocol.command;

import java.util.HashMap;
import javafx.util.Pair;

/**
 * Command representing sensor data.
 */
public class SensorData implements Command {
  private HashMap<String, Pair<Double, String>> sensorDataHashMap;
  private String sensorID;
  private double data;
  private String unit;

  /**
   * Constructor for SensorData with individual sensor details.
   *
   * @param sensorID the ID of the sensor
   * @param data     the data value from the sensor
   * @param unit     the unit of the data
   */
  public SensorData(String sensorID, double data, String unit) {
    this.sensorID = sensorID;
    this.data = data;
    this.unit = unit;
  }

  /**
   * Constructor for SensorData with a hashmap of sensor data.
   *
   * @param sensorDataHashMap the hashmap containing sensor data
   */
  public SensorData(HashMap<String, Pair<Double, String>> sensorDataHashMap) {
    this.sensorDataHashMap = sensorDataHashMap;
  }

  /**
   * Gets the sensor ID.
   *
   * @return the sensor ID
   */
  public String getSensorID() {
    return this.sensorID;
  }

  /**
   * Gets the data value.
   *
   * @return the data value
   */
  public double getData() {
    return this.data;
  }

  /**
   * Gets the unit of the data.
   *
   * @return the unit of the data
   */
  public String getUnit() {
    return this.unit;
  }

  /**
   * Gets the hashmap of sensor data.
   *
   * @return the hashmap of sensor data
   */
  public HashMap<String, Pair<Double, String>> getSensorDataHashMap() {
    return this.sensorDataHashMap;
  }
}

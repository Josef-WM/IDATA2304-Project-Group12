package protocol.command;

import javafx.util.Pair;

import java.util.HashMap;

public class SensorData implements Command {
  private HashMap<String, Pair<Double, String>> sensorDataHashMap;
  private String sensorID;
  private double data;
  private String unit;

  public SensorData(String sensorID, double data, String unit) {
    this.sensorID = sensorID;
    this.data = data;
    this.unit = unit;
  }

  public SensorData(HashMap<String, Pair<Double, String>> sensorDataHashMap) {
    this.sensorDataHashMap = sensorDataHashMap;
  }

  public String getSensorID() {
    return this.sensorID;
  }

  public double getData() {
    return this.data;
  }

  public String getUnit() {
    return this.unit;
  }

  public HashMap<String, Pair<Double, String>> getSensorDataHashMap() {
    return this.sensorDataHashMap;
  }
}

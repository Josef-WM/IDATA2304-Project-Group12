package Greenhouse;

import Client.SensorNode;
import java.util.ArrayList;

/**
 * Javadoc placeholder.
 */
public class Greenhouse {
  private String greenhouseName;
  private ArrayList<SensorNode> sensorNodes;

  private double temperature;
  private int light;
  private int humidity;

  /**
   * Constructor for the Greenhouse class.
   */
  public Greenhouse(String name) {
    this.greenhouseName = name;
    this.temperature = 14;
    this.humidity = 60;
    this.light = 1000;
    this.sensorNodes = new ArrayList<>();
  }

  /**
   * Javadoc placeholder.
   */
  public void addSensorNodeToGreenhouse(SensorNode sensorNode) {
    this.sensorNodes.add(sensorNode);
  }

  /**
   * Javadoc placeholder.
   */
  public void removeSensorNodeFromGreenhouse(SensorNode sensorNode) {
    this.sensorNodes.remove(sensorNode);
  }

  /**
   * Gets the name of the greenhouse.
   */
  public String getGreenhouseName() {
    return this.greenhouseName;
  }

  /**
   * Sets the name of the greenhouse.
   * @param greenhouseName name of the greenhouse
   */
  public void setGreenhouseName(String greenhouseName) {
    this.greenhouseName = greenhouseName;
  }

  /**
   * Gets the temperature of the greenhouse.
   */
  public double getTemperature() {
    return this.temperature;
  }

  /**
   * Gets the humidity of the greenhouse.
   */
  public int getHumidity() {
    return this.humidity;
  }

  /**
   * Gets the light of the greenhouse.
   */
  public int getLight() {
    return this.light;
  }

  /**
   * Changes the temperature of the greenhouse.
   * @param temperature temperature to change
   */
  public void changeTemperature(double temperature) {
    this.temperature = this.temperature + temperature;
  }

  /**
   * Changes the humidity of the greenhouse.
   * @param humidity humidity to change
   */
  public void changeHumidity(int humidity) {
    if (this.humidity + humidity > 100) {
      humidity = 100;
      return;
    }
    if (this.humidity + humidity < 0) {
      humidity = 0;
      return;
    }
    this.humidity = this.humidity + humidity;
  }

  /**
   * Changes the light of the greenhouse.
   * @param light light to change
   */
  public void changeLight(int light) {
    this.light = this.light + light;
  }
}

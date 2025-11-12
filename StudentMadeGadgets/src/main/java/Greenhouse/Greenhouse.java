package Greenhouse;

import Client.SensorNode;

import java.util.ArrayList;

public class Greenhouse {
  private String greenhouseName;
  private ArrayList<SensorNode> sensorNodes;

  private double temperature;
  private int light;
  private int humidity;

  public Greenhouse(String name) {
    this.greenhouseName = name;
    this.temperature = 14;
    this.humidity = 60;
    this.light = 1000;
    this.sensorNodes = new ArrayList<>();
  }

  public void addSensorNodeToGreenhouse(SensorNode sensorNode) {
    this.sensorNodes.add(sensorNode);
  }

  public void removeSensorNodeFromGreenhouse(SensorNode sensorNode) {
    this.sensorNodes.remove(sensorNode);
  }

  public String getGreenhouseName() {
    return this.greenhouseName;
  }

  public void setGreenhouseName(String greenhouseName) {
    this.greenhouseName = greenhouseName;
  }

  public double getTemperature() {
    return this.temperature;
  }

  public int getHumidity() {
    return this.humidity;
  }

  public int getLight() {
    return this.light;
  }

  public void changeTemperature(double temperature) {
    this.temperature = this.temperature+temperature;
  }

  public void changeHumidity(int humidity) {
    if (this.humidity+humidity > 100) {
      humidity = 100;
      return;
    }
    if (this.humidity+humidity < 0) {
      humidity = 0;
      return;
    }
    this.humidity = this.humidity+humidity;
  }

  public void changeLight(int light) {
    this.light = this.light+light;
  }
}

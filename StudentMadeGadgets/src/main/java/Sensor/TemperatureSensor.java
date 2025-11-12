package Sensor;

import Greenhouse.Greenhouse;

public class TemperatureSensor implements Sensor {
  private final String id;
  private final Greenhouse greenhouse;

  public TemperatureSensor(String id, Greenhouse greenhouse) {
    this.id = id;
    this.greenhouse = greenhouse;
  }

  @Override public String getId(){
    return id;
  }

  @Override public String getType(){
    return "Temperature";
  }

  @Override public String getUnit(){
    return "Celsius";
  }

  @Override public double read(){
    return greenhouse.getTemperature();
  }
}

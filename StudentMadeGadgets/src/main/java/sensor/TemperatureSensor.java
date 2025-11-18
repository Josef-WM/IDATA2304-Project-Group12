package sensor;

import greenhouse.Greenhouse;

/**
 * Javadoc placeholder.
 */
public class TemperatureSensor implements Sensor {
  private String ID;
  private final Greenhouse greenhouse;

  public TemperatureSensor(String ID, Greenhouse greenhouse) {
    this.ID = ID;
    this.greenhouse = greenhouse;
  }

  @Override public String getID() {
    return ID;
  }

  @Override
  public void setID(String ID) {
    this.ID = ID;
  }

  @Override public String getType() {
    return "Temperature";
  }

  @Override public String getUnit() {
    return "Celsius";
  }

  @Override public double read() {
    return greenhouse.getTemperature();
  }
}

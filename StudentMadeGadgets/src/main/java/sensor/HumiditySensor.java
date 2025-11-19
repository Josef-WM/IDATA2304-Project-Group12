package sensor;

import greenhouse.Greenhouse;

public class HumiditySensor implements Sensor {
  private String ID;
  private final Greenhouse greenhouse;

  public HumiditySensor(Greenhouse greenhouse) {
    this.greenhouse = greenhouse;
  }

  @Override
  public String getID() {
    return ID;
  }

  @Override
  public void setID(String ID) {
    this.ID = ID;
  }

  @Override
  public String getType() {
    return "Humidity";
  }

  @Override
  public String getUnit() {
    return "Percentage";
  }

  @Override
  public double read() {
    return (double) greenhouse.getHumidity();
  }
}

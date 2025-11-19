package sensor;


import greenhouse.Greenhouse;

public class LightSensor implements Sensor {
  private String ID;
  private final Greenhouse greenhouse;

  public LightSensor(Greenhouse greenhouse) {
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
    return "Light";
  }

  @Override
  public String getUnit() {
    return "Lux";
  }

  @Override
  public double read() {
    return (double)  greenhouse.getLight();
  }

}

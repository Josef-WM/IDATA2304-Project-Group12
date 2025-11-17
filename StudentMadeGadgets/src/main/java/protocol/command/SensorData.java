package protocol.command;

public class SensorData implements Command {
  private String sensorType;
  private double data;
  private String unit;

  public SensorData(String sensorType, double data, String unit) {
    this.sensorType = sensorType;
    this.data = data;
    this.unit = unit;
  }

  public String getSensorType() {
    return this.sensorType;
  }

  public double getData() {
    return this.data;
  }

  public String getUnit() {
    return this.unit;
  }
}

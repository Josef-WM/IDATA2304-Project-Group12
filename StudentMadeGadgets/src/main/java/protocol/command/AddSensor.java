package protocol.command;

public class AddSensor implements Command {
    int greenhouseId;
    String sensorType;

    public AddSensor(int greenhouseId, String sensorType) {
      this.greenhouseId = greenhouseId;
      this.sensorType = sensorType;
    }

    public int getGreenhouseId() {
      return this.greenhouseId;
    }

    public String getSensorType() {
      return this.sensorType;
    }
}

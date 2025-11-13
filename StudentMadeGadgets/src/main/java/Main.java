import Actuator.FanActuator;
import Client.SensorNode;
import Greenhouse.Greenhouse;
import Sensor.TemperatureSensor;

/**
 * Javadoc placeholder.
 */
public class Main {

  /**
   * Javadoc placeholder.
   */
  public static void main(String[] args) {
    Greenhouse greenhouseA = new Greenhouse("Greenhouse A");
    SensorNode sensorNode = new SensorNode(greenhouseA);
    TemperatureSensor temperatureSensor = new TemperatureSensor("TEMP-A", greenhouseA);


    System.out.println(temperatureSensor.read());
    greenhouseA.changeTemperature(2.2);
    System.out.println(temperatureSensor.read());
  }
}

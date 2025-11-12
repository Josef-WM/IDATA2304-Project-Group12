package Client;

import Actuator.Actuator;
import Greenhouse.Greenhouse;
import Sensor.Sensor;

import java.util.ArrayList;

public class SensorNode {
  Greenhouse greenhouse;
  ArrayList<Actuator> actuators;
  ArrayList<Sensor> sensors;

  public SensorNode(Greenhouse greenhouse) {
    this.greenhouse = greenhouse;
  }

  public void addSensorToNode(Sensor sensor) {
    this.sensors.add(sensor);
  }

  public void removeSensorFromNode(Sensor sensor) {
    this.sensors.remove(sensor);
  }

  public void addActuatorToNode(Actuator actuator) {
    this.actuators.add(actuator);
  }

  public void removeActuatorFromNode(Actuator actuator) {
    this.actuators.remove(actuator);
  }
}

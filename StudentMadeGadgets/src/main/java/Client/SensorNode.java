package Client;

import Actuator.Actuator;
import Greenhouse.Greenhouse;
import Sensor.Sensor;

import java.util.Dictionary;
import java.util.HashMap;

public class SensorNode {
  Greenhouse greenhouse;
  HashMap<String, Actuator> actuators;
  HashMap<String, Sensor> sensors;

  public SensorNode(Greenhouse greenhouse) {
    this.greenhouse = greenhouse;
    this.actuators = new HashMap<String, Actuator>();
    this.sensors = new HashMap<String, Sensor>();
  }

  public void addSensorToNode(Sensor sensor) {
    this.sensors.put(sensor.getID(), sensor);
  }

  public void removeSensorFromNode(Sensor sensor) {
    this.sensors.remove(sensor.getID());
  }

  public void addActuatorToNode(Actuator actuator) {
    this.actuators.put(actuator.getID(), actuator);
  }

  public void removeActuatorFromNode(Actuator actuator) {
    this.actuators.remove(actuator.getID());
  }

  public Actuator getActuator(String deviceID) {
    return actuators.get(deviceID);
  }

  public Sensor getSensor(String deviceID) {
    return sensors.get(deviceID);
  }

  public boolean toggleActuator(String deviceID) {
    return getActuator(deviceID).toggle();
  }
}
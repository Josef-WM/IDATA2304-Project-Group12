package Client;

import Actuator.Actuator;
import Greenhouse.Greenhouse;
import Sensor.Sensor;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Javadoc placeholder.
 */
public class SensorNode {
  Greenhouse greenhouse;
  HashMap<String, Actuator> actuators;
  HashMap<String, Sensor> sensors;

  /**
   * Constructor for the SensorNode class.
   */
  public SensorNode(Greenhouse greenhouse) {
    this.greenhouse = greenhouse;
    this.actuators = new HashMap<String, Actuator>();
    this.sensors = new HashMap<String, Sensor>();
  }

  /**
   * Adds a sensor to the node.
   * @param sensor the sensor to add
   */
  public void addSensorToNode(Sensor sensor) {
    this.sensors.put(sensor.getID(), sensor);
  }

  /**
   * Removes a sensor from the node.
   * @param sensor the sensor to remove
   */
  public void removeSensorFromNode(Sensor sensor) {
    this.sensors.remove(sensor.getID());
  }

  /**
   * Adds an actuator to the node.
   * @param actuator the actuator to add to the node
   */
  public void addActuatorToNode(Actuator actuator) {
    this.actuators.put(actuator.getID(), actuator);
  }

  /**
   * Removes an actuator from the node.
   * @param actuator the actuator to remove from the node
   */
  public void removeActuatorFromNode(Actuator actuator) {
    this.actuators.remove(actuator.getID());
  }

  public Actuator getActuator(String deviceID) {
    return actuators.get(deviceID);
  }

  public Sensor getSensor(String deviceID) {
    return sensors.get(deviceID);
  }
}

package client;

import actuator.Actuator;
import greenhouse.Greenhouse;
import sensor.Sensor;

import java.util.HashMap;

/**
 * Javadoc placeholder.
 */
public class SensorNode {
  private Greenhouse greenhouse;
  private HashMap<String, Actuator> actuators;
  private HashMap<String, Sensor> sensors;

  /**
   * Constructor for the SensorNode class.
   */
  public SensorNode(Greenhouse greenhouse) {
    this.greenhouse = greenhouse;
    this.actuators = new HashMap<>();
    this.sensors = new HashMap<>();
  }

  /**
   * Adds a sensor to the node.
   * @param sensor the sensor to add
   */
  public String addSensorToNode(Sensor sensor) {
    String sensorType = sensor.getType();

    int count = 1;
    String uniqueID;
    do {
      uniqueID = sensorType + "-" + count;
      count++;
    } while (sensors.containsKey(uniqueID));

    sensor.setID(uniqueID);
    this.sensors.put(uniqueID, sensor);
    return uniqueID;
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

  public HashMap<String, Actuator> getActuators() {
    return this.actuators;
  }

  public HashMap<String, Sensor> getSensors() {
    return this.sensors;
  }

  public boolean toggleActuator(String deviceID) {
    return getActuator(deviceID).toggle();
  }

  public void setActuatorPower(String deviceID, int power) {
    getActuator(deviceID).setPower(power);
  }
}
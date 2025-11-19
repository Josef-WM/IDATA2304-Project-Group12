package client;

import actuator.Actuator;
import greenhouse.Greenhouse;
import sensor.Sensor;

import java.util.HashMap;

/**
 * This class represents a sensor node that can hold multiple sensors and actuators.
 */
public class SensorNode {
  private HashMap<String, Actuator> actuators;
  private HashMap<String, Sensor> sensors;

  /**
   * Constructor for the SensorNode class.
   */
  public SensorNode() {
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
  public String addActuatorToNode(Actuator actuator) {
    String actuatorType = actuator.getType();

    int count = 1;
    String uniqueID;
    do {
      uniqueID = actuatorType + "-" + count;
      count++;
    } while (actuators.containsKey(uniqueID));

    actuator.setID(uniqueID);
    this.actuators.put(uniqueID, actuator);
    return uniqueID;
  }

  /**
   * Removes an actuator from the node.
   * @param actuator the actuator to remove from the node
   */
  public void removeActuatorFromNode(Actuator actuator) {
    this.actuators.remove(actuator.getID());
  }

  /**
   * Gets an actuator by its device ID.
   * @param deviceID the device ID of the actuator
   * @return the actuator with the given device ID
   */
  public Actuator getActuator(String deviceID) {
    return actuators.get(deviceID);
  }

  /**
   * Gets a sensor by its device ID.
   * @param deviceID the device ID of the sensor
   * @return the sensor with the given device ID
   */
  public Sensor getSensor(String deviceID) {
    return sensors.get(deviceID);
  }

  /**
   * Returns the actuators in the sensor node.
   * @return
   */
  public HashMap<String, Actuator> getActuators() {
    return this.actuators;
  }

  /**
   * Returns the sensors in the sensor node.
   * @return
   */
  public HashMap<String, Sensor> getSensors() {
    return this.sensors;
  }

  /**
   * Toggles an actuator on or off.
   * @param deviceID the device ID of the actuator
   * @param greenhouse the greenhouse the actuator is in
   * @return the new state of the actuator
   */
  public boolean toggleActuator(String deviceID, Greenhouse greenhouse) {
    return getActuator(deviceID).toggle(greenhouse);
  }

  public void setActuatorState(String deviceId, Greenhouse greenhouse, boolean state) {
   this.actuators.get(deviceId).setState(state, greenhouse);
  }

  /**
   * Sets the power of an actuator.
   * @param deviceID the device ID of the actuator
   * @param power the power to set the actuator to
   */
  public void setActuatorPower(String deviceID, int power, Greenhouse greenhouse) {
    getActuator(deviceID).setPower(power, greenhouse);
  }
}
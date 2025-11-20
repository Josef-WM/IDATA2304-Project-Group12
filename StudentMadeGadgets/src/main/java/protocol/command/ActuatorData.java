package protocol.command;

import java.util.HashMap;
import javafx.util.Pair;

/**
 * Command representing actuator data.
 */
public class ActuatorData implements Command {
  private HashMap<String, Pair<Boolean, Integer>> actuatorDataHashMap;
  private String actuatorID;
  private boolean isOn;
  private int power;

  /**
   * Constructor for ActuatorData with individual actuator details.
   *
   * @param actuatorID the ID of the actuator
   * @param isOn       the state of the actuator (on/off)
   * @param power      the power level of the actuator
   */
  public ActuatorData(String actuatorID, boolean isOn, int power) {
    this.actuatorID = actuatorID;
    this.isOn = isOn;
    this.power = power;
  }

  /**
   * Constructor for ActuatorData with a hashmap of actuator data.
   *
   * @param actuatorDataHashMap the hashmap containing actuator data
   */
  public ActuatorData(HashMap<String, Pair<Boolean, Integer>> actuatorDataHashMap) {
    this.actuatorDataHashMap = actuatorDataHashMap;
  }

  /**
   * Gets the actuator ID.
   *
   * @return the actuator ID
   */
  public String getActuatorID() {
    return this.actuatorID;
  }

  /**
   * Gets the state of the actuator.
   *
   * @return true if the actuator is on, false otherwise
   */
  public boolean isOn() {
    return this.isOn;
  }

  /**
   * Gets the power level of the actuator.
   *
   * @return the power level
   */
  public int getPower() {
    return this.power;
  }

  /**
   * Gets the hashmap of actuator data.
   *
   * @return the hashmap of actuator data
   */
  public HashMap<String, Pair<Boolean, Integer>> getActuatorDataHashMap() {
    return this.actuatorDataHashMap;
  }
}

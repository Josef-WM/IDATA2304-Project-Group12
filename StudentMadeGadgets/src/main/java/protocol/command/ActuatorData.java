package protocol.command;

import javafx.util.Pair;

import java.util.HashMap;

public class ActuatorData implements Command {
  private HashMap<String, Pair<Boolean, Integer>> actuatorDataHashMap;
  private String actuatorID;
  private boolean isOn;
  private int power;

  public ActuatorData(String actuatorID, boolean isOn, int power) {
    this.actuatorID = actuatorID;
    this.isOn = isOn;
    this.power = power;
  }

  public ActuatorData(HashMap<String, Pair<Boolean, Integer>> actuatorDataHashMap) {
    this.actuatorDataHashMap = actuatorDataHashMap;
  }

  public String getActuatorID() {
    return this.actuatorID;
  }

  public boolean isOn() {
    return this.isOn;
  }

  public int getPower() {
    return this.power;
  }

  public HashMap<String, Pair<Boolean, Integer>> getActuatorDataHashMap() {
    return this.actuatorDataHashMap;
  }
}

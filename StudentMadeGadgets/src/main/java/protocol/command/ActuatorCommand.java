package protocol.command;

import actuator.Actuator;

import java.util.ArrayList;

public class ActuatorCommand extends Command{
  private ArrayList<Actuator> actuators;
  private int power;
  private boolean turnOn;

  public ActuatorCommand(ArrayList<Actuator> actuators, int power, boolean turnOn) {
    this.actuators = actuators;
    this.power = power;
    this.turnOn = turnOn;
  }

  public ActuatorCommand(ArrayList<Actuator> actuators, int power) {
    this.actuators = actuators;
    this.power = power;
  }

  public ArrayList<Actuator> getActuators() {
    return this.actuators;
  }

  public int getPower() {
    return this.power;
  }

  public boolean isTurnOn() {
    return this.turnOn;
  }
}

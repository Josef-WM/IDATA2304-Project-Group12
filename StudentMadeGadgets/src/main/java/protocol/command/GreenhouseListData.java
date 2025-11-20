package protocol.command;

import greenhouse.Greenhouse;
import java.util.ArrayList;

/**
 * Command representing a list of greenhouses.
 */
public class GreenhouseListData implements Command {
  ArrayList<Greenhouse> greenhouses;

  /**
   * Constructor for GreenhouseListData.
   *
   * @param greenhouses the list of greenhouses
   */
  public GreenhouseListData(ArrayList<Greenhouse> greenhouses) {
    this.greenhouses = greenhouses;
  }

  /**
   * Gets the list of greenhouses.
   *
   * @return the list of greenhouses
   */
  public ArrayList<Greenhouse> getGreenhouses() {
    return greenhouses;
  }
}

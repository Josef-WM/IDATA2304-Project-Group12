package protocol.command;

import greenhouse.Greenhouse;

import java.util.ArrayList;

public class GreenhouseListData implements Command {
  ArrayList<Greenhouse> greenhouses;

  public GreenhouseListData(ArrayList<Greenhouse> greenhouses) {
   this.greenhouses = greenhouses;
  }

  public ArrayList<Greenhouse> getGreenhouses() {
    return greenhouses;
  }
}

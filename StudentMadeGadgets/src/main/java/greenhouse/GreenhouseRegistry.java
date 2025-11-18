package greenhouse;

import java.util.ArrayList;

public class GreenhouseRegistry {
  private final ArrayList<Greenhouse> greenhouses = new ArrayList<>();

  public synchronized int addGreenhouse(String name) {
    Greenhouse greenhouse = new Greenhouse(name);
    this.greenhouses.add(greenhouse);
    updateList();
    return greenhouse.getGreenhouseId();
  }

  private synchronized void updateList() {
    for (int i = 0; i < greenhouses.size(); i++) {
      greenhouses.get(i).setGreenhouseId(i+1);
    }
  }

  public synchronized boolean removeGreenhouse(int id) {
    boolean result = greenhouses.removeIf(greenhouse -> greenhouse.getGreenhouseId() == id);
    updateList();
    return result;
  }

  public synchronized ArrayList<Greenhouse> getAllGreenhouses() {
    return this.greenhouses;
  }
}

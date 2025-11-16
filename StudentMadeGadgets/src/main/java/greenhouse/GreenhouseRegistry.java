package greenhouse;

import java.util.ArrayList;

public class GreenhouseRegistry {
  private final ArrayList<Greenhouse> greenhouses = new ArrayList<>();
  private int nextId = 1;

  public synchronized int addGreenhouse(String name) {
    Greenhouse greenhouse = new Greenhouse(nextId++, name);
    this.greenhouses.add(greenhouse);
    return greenhouse.getGreenhouseId();
  }

  public synchronized boolean removeGreenhouse(int id) {
    return greenhouses.removeIf(greenhouse -> greenhouse.getGreenhouseId() == id);
  }

  public synchronized ArrayList<Greenhouse> getAllGreenhouses() {
    return this.greenhouses;
  }
}

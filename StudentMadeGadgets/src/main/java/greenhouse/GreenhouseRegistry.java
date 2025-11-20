package greenhouse;

import java.util.ArrayList;

/**
 * Registry for managing multiple greenhouses.
 */
public class GreenhouseRegistry {
  private final ArrayList<Greenhouse> greenhouses = new ArrayList<>();

  /**
   * Adds a new greenhouse to the registry.
   *
   * @param name the name of the new greenhouse
   * @return the ID of the newly added greenhouse
   */
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

  /**
   * Gets a greenhouse by its ID.
   *
   * @param ID the ID of the greenhouse
   * @return the greenhouse with the specified ID
   */
  public Greenhouse getGreenhouse(int ID) {
    return this.greenhouses.get(ID - 1);
  }

  /**
   * Removes a greenhouse by its ID.
   *
   * @param id the ID of the greenhouse to be removed
   * @return true if the greenhouse was removed, false otherwise
   */
  public synchronized boolean removeGreenhouse(int id) {
    boolean result = greenhouses.removeIf(greenhouse -> greenhouse.getGreenhouseId() == id);
    updateList();
    return result;
  }

  /**
   * Gets all greenhouses in the registry.
   *
   * @return a list of all greenhouses
   */
  public synchronized ArrayList<Greenhouse> getAllGreenhouses() {
    return this.greenhouses;
  }
}

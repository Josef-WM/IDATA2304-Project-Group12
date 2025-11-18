package protocol.command;

public class RemoveGreenhouse implements Command {
  private int id;

  public RemoveGreenhouse(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}

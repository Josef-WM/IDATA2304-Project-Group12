package protocol.command;

public class CreateGreenhouse implements Command {
  private String name;

  public CreateGreenhouse(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}

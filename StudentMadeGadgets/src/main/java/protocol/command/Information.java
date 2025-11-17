package protocol.command;

public class Information implements Command {
  String information;

  public Information(String information) {
    this.information = information;
  }

  public String getInformation() {
    return this.information;
  }
}

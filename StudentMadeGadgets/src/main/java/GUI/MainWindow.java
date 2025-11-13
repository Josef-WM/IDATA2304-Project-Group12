package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main window for the control panel UI.
 */
public class MainWindow extends Application {
  private Label info;

  @Override
  public void start(Stage stage) throws Exception {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("SMG Control Panel GUI");
    stage.show();
  }
}
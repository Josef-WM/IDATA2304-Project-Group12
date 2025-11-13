package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main window for the control panel UI.
 */
public class MainWindow extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root);

    Label info = new Label("Control Panel UI");

    root.setCenter(info);

    stage.setScene(scene);
    stage.setTitle("SMG Control Panel GUI");
    stage.show();
  }

  /**
   * The main method of the control panel application.
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
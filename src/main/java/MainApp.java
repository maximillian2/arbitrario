
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main GUI class
 *
 * @author Maksym Tymoshyk
 * @see Application
 */
public class MainApp extends Application {

  private Stage primaryStage;
  private BorderPane rootLayout;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("MagmaApp");

    initRootLayout();
    showMainLayout();
  }

  private void initRootLayout() {
    try {
      // Загружаем корневой макет из fxml файла.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("rootLayout.fxml"));
      rootLayout = loader.load();

      // Отображаем сцену, содержащую корневой макет.
      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void showMainLayout() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("mainWindow.fxml"));
      AnchorPane personOverview = loader.load();

      rootLayout.setCenter(personOverview);

      // Give the controller access to the main app
      DataController controller = loader.getController();
      controller.setMainApp(this);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public MainApp() {
  }

}

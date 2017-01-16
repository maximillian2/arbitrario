/*
 * MIT License
 *
 * Copyright (c) 2016 Maksym Tymoshyk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package nau.magma.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main GUI class
 *
 * @author Maksym Tymoshyk
 * @version 1.0
 * @see Application
 */
public class MagmaGUI extends Application {

  private Stage primaryStage;
  private HBox rootLayout;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("MagmaApp");

    initRootLayout();
//    showMainLayout();
  }

  private void initRootLayout() {
    try {
//       Load root layout from fxml file
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MagmaGUI.class.getResource("/fxml/new_gui.fxml"));
      rootLayout = loader.load();

//       Display scene that contains root layout
      Scene scene = new Scene(rootLayout);

      primaryStage.setScene(scene);
      primaryStage.setMinWidth(600);
      primaryStage.setMinHeight(430);

      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

//  private void showMainLayout() {
//    try {
//      FXMLLoader loader = new FXMLLoader();
//      loader.setLocation(MagmaGUI.class.getResource("/fxml/new_gui.fxml"));
//      StackPane personOverview = loader.load();
//
////      rootLayout.setCenter(personOverview);
////      rootLayout.setLeft(personOverview);
//
////       Give the controller access to the main app
//      DataController controller = loader.getController();
//      controller.setMainApp(this);
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  public MagmaGUI() {
  }

}

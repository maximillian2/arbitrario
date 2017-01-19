/*
 * MIT License
 *
 * Copyright (c) 2017 Maksym Tymoshyk
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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maksym Tymoshyk on 1/18/17.
 */
public class HelpController implements Initializable {
//  private MagmaGUI app;

  @FXML // fx:id="neonProjectLink"
  private Hyperlink neonProjectLink; // Value injected by FXMLLoader

  @FXML // fx:id="githubRealizationLink"
  private Hyperlink githubRealizationLink; // Value injected by FXMLLoader

  @FXML // fx:id="authorLink"
  private Hyperlink authorLink; // Value injected by FXMLLoader

  public HelpController(MagmaGUI app) {
//    this.app = app;
  }

  // This method is called by the FXMLLoader when initialization is complete
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    assert neonProjectLink != null : "fx:id=\"neonProjectLink\" was not injected: check your FXML file 'helpWindow.fxml'.";
    assert githubRealizationLink != null : "fx:id=\"githubRealizationLink\" was not injected: check your FXML file 'helpWindow.fxml'.";
    assert authorLink != null : "fx:id=\"authorLink\" was not injected: check your FXML file 'helpWindow.fxml'.";
  }

  @FXML
  private void handleGithubRealizationLink() {
//    app.getHostServices().showDocument("https://github.com/dag4202/TravelingSalesman");
  }

  @FXML
  private void handleNeonProjectLink() {
//    app.getHostServices().showDocument("https://thenounproject.com/lch121/");
  }

  @FXML
  private void handleAuthorLink() {
//    app.getHostServices().showDocument("https://github.com/maximillian2");
  }

  public void showHelp() {
    try {
      // Load root layout from fxml file
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MagmaGUI.class.getResource("/fxml/helpWindow.fxml"));
      loader.setController(this);
      BorderPane helpWindow = loader.load();

      // Display scene that contains root layout
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
//       stage.initOwner(authorLink.getScene().getWindow());

      stage.setTitle("About");
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setScene(new Scene(helpWindow));
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

package app.archive.kv.ndpi.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by konstr on 23.12.2015.
 */
public class RootViewController implements Initializable {

    @FXML
    private MenuItem closeApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeApplication(ActionEvent event){
        System.exit(1);
    }

    public void showAboutInfo(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutWindow.fxml"));
        Parent root = (Parent)loader.load();
        Stage stage = new Stage();
        stage.setTitle("About KV NDPI Archive App");
        stage.setScene(new Scene(root));
        stage.show();
    }

}

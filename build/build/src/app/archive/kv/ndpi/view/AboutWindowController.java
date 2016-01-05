package app.archive.kv.ndpi.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by konstr on 23.12.2015.
 */
public class AboutWindowController implements Initializable {

    @FXML
    private Button closeWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeWindow(ActionEvent event){
        Stage stage = (Stage)closeWindow.getScene().getWindow();
        stage.close();
    }

}

package app.archive.kv.ndpi.view;

import app.archive.kv.ndpi.domain.Document;
import app.archive.kv.ndpi.domain.Placing;
import app.archive.kv.ndpi.service.ArchiveDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by konstr on 18.12.2015.
 */
public class DetailedViewController implements Initializable {
    
    private ObservableList<String> docTypeList = FXCollections.observableArrayList("оригінали", "копії");
    private ObservableList<String> docList = FXCollections.observableArrayList("креслення", "текстова", "комплект", "ТУ");
    private ObservableList<String> places = FXCollections.observableArrayList("шафа підвал", "ліва шафа", "права шафа",
            "Стелаж");    

    private Document doc=new Document();
    private ArchiveDataSource archiveDBSource = new ArchiveDataSource();

    @FXML
    private ChoiceBox docType;
    @FXML
    private ChoiceBox type;
    @FXML
    private ChoiceBox place;

    @FXML
    Button backButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    TextArea documentName;
    @FXML
    private Label isConnected;
    @FXML
    private Label docId;
    @FXML
    private TextField row;
    @FXML
    private TextField number;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(archiveDBSource.dbConnected()){
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    public void getDocument(Document document){
        this.doc=document;
        docId.setText(String.valueOf(doc.getId()));
        place.setValue(doc.getPlace().getRoom());
        type.setValue(doc.getType());
        docType.setValue(doc.getKind());
        row.setText(String.valueOf(doc.getPlace().getLevel()));
        number.setText(String.valueOf(doc.getPlace().getNumber()));

        documentName.setText(doc.getName());

        place.setItems(places);
        docType.setItems(docTypeList);
        type.setItems(docList);
    }

    public void backToMainPage(ActionEvent event){
        try {
            //get current Stage resource
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();

            //Loading BorderPane root layout
            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("RootView.fxml").openStream());
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();

            //loading current content of a page
            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(getClass().getResource("MainView.fxml"));
            AnchorPane personOverview = (AnchorPane) loader2.load();
            // Set person overview into the center of root layout.
            root.setCenter(personOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editDocument(ActionEvent event){
        Document editDoc=null;
        try {
            editDoc=getEditedDoc();
        } catch (Exception e){
            isConnected.setText("Wrong Input Data! Check input fields");
            e.printStackTrace();
        }
        boolean result = archiveDBSource.editDocument(editDoc);

        if(result){
            isConnected.setText("Document edited Successfully!");
        } else {
            isConnected.setText("Error! Edit error");
        }
    }

    public void deleteDocument(ActionEvent event){
        int id = Integer.parseInt(docId.getText());
        boolean result = archiveDBSource.deleteDocument(id);
        if(result){
            isConnected.setText("Document delete Successfully!");
        } else {
            isConnected.setText("Error! Delete error.");
        }

    }

    private Document getEditedDoc() throws Exception {
        String name = documentName.getText();
        String typeDoc = type.getValue().toString();
        String kind = docType.getValue().toString();
        String room = place.getValue().toString();
        String level = row.getText();
        String numer = number.getText();        
        Placing place = new Placing();
        place.setRoom(room);
        place.setLevel(String.valueOf(level));
        place.setNumber(String.valueOf(numer));
        Document editDoc = new Document(name, typeDoc, kind, place);
        int id = Integer.parseInt(docId.getText());
        editDoc.setId(id);
        return editDoc;
    }
}

package app.archive.kv.ndpi.view;

import app.archive.kv.ndpi.domain.Document;
import app.archive.kv.ndpi.domain.Placing;
import app.archive.kv.ndpi.service.ArchiveDataSource;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by konstr on 17.12.2015.
 */
public class MainViewController implements Initializable {

    private ObservableList<String> docTypeList = FXCollections.observableArrayList("оригінали", "копії");
    private ObservableList<String> docList = FXCollections.observableArrayList("креслення", "текстова", "комплект", "ТУ");
    private ObservableList<String> places = FXCollections.observableArrayList("шафа підвал", "ліва шафа", "права шафа",
            "Стелаж");

    private ArchiveDataSource archiveDBSource = new ArchiveDataSource();

    @FXML
    private Label isConnected;
    @FXML
    private TextArea docNameAdd;
    @FXML
    private TextField docNameFind;
    @FXML
    private TextField row;
    @FXML
    private TextField number;
    @FXML
    private Button findDoc;
    @FXML
    private Button addDoc;
    @FXML
    private Button findAllDocs;
    @FXML
    private Button detailedInfo;
    @FXML
    private ChoiceBox docTypeFind;
    @FXML
    private ChoiceBox docTypeAdd;
    @FXML
    private ChoiceBox typeAdd;
    @FXML
    private ChoiceBox typeFind;
    @FXML
    private ChoiceBox place;

    @FXML
    private TableView<Document> tableView;
    @FXML
    private TableColumn<Document, String> idColumn;
    @FXML
    private TableColumn<Document, String> nameColumn;
    @FXML
    private TableColumn<Document, String> typeColumn;
    @FXML
    private TableColumn<Document, String> kindColumn;
    @FXML
    private TableColumn<Document, String> placeColumn;
    @FXML
    private TableColumn<Document, String> levelColumn;
    @FXML
    private TableColumn<Document, String> numberColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(archiveDBSource.dbConnected()){
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }

        place.setValue(places.get(0));
        docTypeAdd.setValue(docTypeList.get(0));
        docTypeFind.setValue(docTypeList.get(0));
        typeAdd.setValue(docList.get(0));
        typeFind.setValue(docList.get(0));

        place.setItems(places);
        docTypeAdd.setItems(docTypeList);
        docTypeFind.setItems(docTypeList);
        typeAdd.setItems(docList);
        typeFind.setItems(docList);

        idColumn.setCellValueFactory(new PropertyValueFactory<Document, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Document, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Document, String>("type"));
        kindColumn.setCellValueFactory(new PropertyValueFactory<Document, String>("kind"));

        placeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Document, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Document, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getPlace().getRoom());
            }
        });

        levelColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Document, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Document, String> param) {
                return new ReadOnlyStringWrapper(String.valueOf(param.getValue().getPlace().getLevel()));
            }
        });

        numberColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Document, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Document, String> param) {
                return new ReadOnlyStringWrapper(String.valueOf(param.getValue().getPlace().getNumber()));
            }
        });
    }

    public void findDoc(ActionEvent event){
        try {
            ArrayList<Document> list = archiveDBSource.findDocInDB(docNameFind.getText(),
                    typeFind.getValue().toString(), docTypeFind.getValue().toString());
            if(list!=null){
                isConnected.setText("book found! Success");
                populateTableView(list);
            } else {
                isConnected.setText("book not found!");
                tableView.setItems(null);
            }
        } catch (SQLException e) {
            isConnected.setText("Error");
            e.printStackTrace();
        }
    }

    public void addDoc(ActionEvent event){
        Document doc=null;
        try {
            doc = getDocument();
        } catch (Exception e){
            isConnected.setText("Wrong Input Data! Check input fields");
            e.printStackTrace();
        }
        Document addedDoc = archiveDBSource.addDoc(doc);

        if(addedDoc!=null){
            isConnected.setText("book added! Success");
            ObservableList<Document> addedDocList = FXCollections.observableArrayList(doc);
            tableView.setItems(addedDocList);
        } else {
            isConnected.setText("book not added! Error");
        }

    }

    public void findAllDocs(ActionEvent event){
        try {
            ArrayList<Document> list = archiveDBSource.findAllDocs();

            if(!list.isEmpty()){
                isConnected.setText("Found " + list.size() + " books");
            } else {
                isConnected.setText("List is empty");
            }
            populateTableView(list);

        } catch (Exception e){
            isConnected.setText("Error");
            e.printStackTrace();
        }
    }

    public void detailedView(ActionEvent event){
        //String selectedBook = listView.getSelectionModel().getSelectedItem();
        Document selectedDoc = tableView.getSelectionModel().getSelectedItem();
        if(selectedDoc!=null){
            try {
                //hide current window
                //((Node)event.getSource()).getScene().getWindow().hide();

                //get current Stage resource
                Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                //primaryStage.setTitle("AddressApp");
                FXMLLoader loader = new FXMLLoader();
                BorderPane root = loader.load(getClass().getResource("RootView.fxml").openStream());
                Scene scene = new Scene(root);
                ////Pane root = loader.load(getClass().getResource("detailedView.fxml").openStream());

                //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();

                FXMLLoader loader2 = new FXMLLoader();
                loader2.setLocation(getClass().getResource("DetailedView.fxml"));
                AnchorPane personOverview = (AnchorPane) loader2.load();

                //passing book value to new page
                DetailedViewController detailedViewController =
                        (DetailedViewController)loader2.getController();
                detailedViewController.getDocument(selectedDoc);

                // Set person overview into the center of root layout.
                root.setCenter(personOverview);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Nothing selected");
        }

    }

    private void populateTableView(ArrayList<Document> list){
        ObservableList<Document> documentData = FXCollections.observableArrayList();
        for (Document doc:list){
            documentData.add(doc);
        }
        tableView.setItems(documentData);
    }

    private Document getDocument() throws Exception {
        String name = docNameAdd.getText();
        String type = typeAdd.getValue().toString();
        String kind = docTypeAdd.getValue().toString();
        String room = place.getValue().toString();
        int level= Integer.parseInt(row.getText());
        int numer= Integer.parseInt(number.getText());
        Placing place = new Placing();
        place.setRoom(room);
        place.setLevel(String.valueOf(level));
        place.setNumber(String.valueOf(number));
        Document doc = new Document(name, type, kind, place);
        return doc;
    }

}

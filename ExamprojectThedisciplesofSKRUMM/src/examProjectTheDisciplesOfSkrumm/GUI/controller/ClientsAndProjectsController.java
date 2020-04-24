/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author anton
 */
public class ClientsAndProjectsController implements Initializable {

    @FXML
    private JFXButton taskBtn;
    @FXML
    private JFXButton AdminBtn;
    @FXML
    private TableView<Project> ClientList;
    @FXML
    private JFXButton mainBtn;
    @FXML
    private TableColumn<Project, String> projectNameColumn;
    @FXML
    private TableColumn<Project, String> clientNameColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("clientName"));
        
        ObservableList<Project> getData = FXCollections.observableArrayList();
        
        
        Project project1 = new Project("Take over the world", "Doofensmirtz");
        Project project2 = new Project("do something fun", "Phineas and Ferb");
        Project project3 = new Project("bust brothers", "Candace");
        
        getData.add(project1);
        getData.add(project2);
        getData.add(project3);
        
        
       ClientList.setItems(getData);
    }   
    
    

    @FXML
    private void handletaskView(ActionEvent event) throws IOException
    {
         Stage clientsAndProjects = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/TaskView.fxml"));
        Parent root = loader.load();
        TaskViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show();
        clientsAndProjects.close();
    }

    @FXML
    private void handlecChartView(ActionEvent event) throws IOException
    {
        Stage clientsAndProjects = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ChartView.fxml"));
        Parent root = loader.load();
        ChartViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show();
        clientsAndProjects.close();
    }

    @FXML
    private void handlecAdminView(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminView.fxml"));
        Parent root = loader.load();
        AdminViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show(); 
    }
   @FXML
    private void handleMain(ActionEvent event) throws IOException
    {
        Stage clientsAndProjects = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/MainView.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show();
        clientsAndProjects.close();
    }
    
}

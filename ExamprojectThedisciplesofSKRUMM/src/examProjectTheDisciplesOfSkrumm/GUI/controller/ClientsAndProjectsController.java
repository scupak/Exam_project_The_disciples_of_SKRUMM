/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author anton
 */
public class ClientsAndProjectsController implements Initializable {

    ModelFacadeInterface modelfacade;
    @FXML
    private TableView<Project> ClientList;
    @FXML
    private TableColumn<Project, String> projectNameColumn;
    @FXML
    private TableColumn<Project, String> clientNameColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //set up the columns in the table
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("clientName"));
        
        /**ObservableList<Project> getData = FXCollections.observableArrayList();
        
        
       Project project1 = new Project("Take over the world", new Client("Doofensmirtz"));
        Project project2 = new Project("do something fun",new Client("Phineas and Ferb"));
        Project project3 = new Project("bust brothers", new Client("Candace"));
        
        getData.add(project1);
        getData.add(project2);
        getData.add(project3);
        */
        refreshTableview();
       
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
        stage.setMinHeight(525);
        stage.setMinWidth(726);
        stage.setTitle("Main menu");
        stage.show();
        clientsAndProjects.close();
    }
    
    private void refreshTableview()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> 
        {
            ObservableList<Project> projects = FXCollections.observableArrayList();
            projects.addAll(modelfacade.getProjects());
            
            Platform.runLater( () -> 
            {
                ClientList.setItems(projects);
            });
        });
        
        executor.shutdown();
       
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lumby
 */
public class AdminClientsAndProjectsController implements Initializable
{

    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableView<?> projectTableView;
    @FXML
    private JFXButton createClientBtn;
    @FXML
    private JFXButton editClientBtn;
    @FXML
    private JFXButton deleteClientBtn;
    @FXML
    private JFXButton createProjectBtn;
    @FXML
    private JFXButton editProjectBtn;
    @FXML
    private JFXButton deleteProjectBtn;
    @FXML
    private JFXButton backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleCreateClient(ActionEvent event) throws IOException
    {
     FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddClient.fxml"));
        Parent root = loader.load();
        AddClientController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Create Client");
        stage.show();   
    }

    @FXML
    private void handleEditClient(ActionEvent event)
    {
        
    }

    @FXML
    private void handleDeleteClient(ActionEvent event)
    {
    }

    @FXML
    private void handleCreateProject(ActionEvent event)
    {
    }

    @FXML
    private void handleEditProject(ActionEvent event)
    {
    }

    @FXML
    private void handleDeleteProject(ActionEvent event)
    {
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        Stage adminClientsAndProjectsView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminMainView.fxml"));
        Parent root = loader.load();
        AdminMainViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(523);
        stage.setMinWidth(721);
        stage.setTitle("Main Menu");
        stage.show();
        adminClientsAndProjectsView.close();
    }
    
}

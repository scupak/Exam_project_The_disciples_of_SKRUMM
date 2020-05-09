/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author lumby
 */
public class AdminClientsAndProjectsController implements Initializable
{

    @FXML
    private TableView<?> clientTableView;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleCreateClient(ActionEvent event)
    {
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
    
}

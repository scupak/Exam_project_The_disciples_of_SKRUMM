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

/**
 * FXML Controller class
 *
 * @author lumby
 */
public class AdminMainViewController implements Initializable
{

    @FXML
    private JFXButton clientsAndProjectsbtn;
    @FXML
    private JFXButton tasksBtn;
    @FXML
    private JFXButton usersbtn;
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
    private void handleClientsAndProjects(ActionEvent event)
    {
    }

    @FXML
    private void handleTaks(ActionEvent event)
    {
    }

    @FXML
    private void handleUsers(ActionEvent event)
    {
    }

    @FXML
    private void handleBackToMain(ActionEvent event)
    {
    }
    
}

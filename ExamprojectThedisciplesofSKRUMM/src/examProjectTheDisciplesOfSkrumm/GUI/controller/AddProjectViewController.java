/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Christina
 */
public class AddProjectViewController implements Initializable
{

    @FXML
    private JFXButton AddProjectOkBtn;
    @FXML
    private JFXButton AddProjectCancelBtn;
    @FXML
    private JFXTextField ProjectNameTextField;
    @FXML
    private JFXTextField ProjectRateTextField;
    @FXML
    private JFXButton addClientButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void HandleAddProjectOkBtn(ActionEvent event) {
    }

    @FXML
    private void HandleAddProjectCancelBtn(ActionEvent event) {
    }

    @FXML
    private void handleAddClient(ActionEvent event) {
    }
    
}

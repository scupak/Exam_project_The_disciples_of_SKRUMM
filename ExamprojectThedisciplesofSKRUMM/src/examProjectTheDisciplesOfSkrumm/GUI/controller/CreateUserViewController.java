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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christina
 */
public class CreateUserViewController implements Initializable
{

    @FXML
    private JFXButton createBtn;
    @FXML
    private JFXButton cancelBtn;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleCreateUser(ActionEvent event)
    {
        Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
         createUserView.close();
    }

    @FXML
    private void handleCancel(ActionEvent event)
    {
        Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
         createUserView.close();
    }
    
}

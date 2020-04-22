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
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class EditClientController implements Initializable
{

    @FXML
    private JFXTextField EditClientTextField;
    @FXML
    private JFXButton EditOkButton;
    @FXML
    private JFXButton EditCancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void HandleEditOkButton(ActionEvent event)
    {
    }

    @FXML
    private void HandleEditCancelButton(ActionEvent event)
    {
    }
    
}

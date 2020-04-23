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
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class EditClientController implements Initializable
{

    @FXML
    private JFXButton EditOkButton;
    @FXML
    private JFXButton EditCancelButton;
    @FXML
    private JFXTextField EditClientNameTextField;
    @FXML
    private JFXTextField ClientEditRateTextField;

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
        Stage editClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editClientView.close();
    }

    @FXML
    private void HandleEditCancelButton(ActionEvent event)
    {
        Stage editClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editClientView.close();
    }

}

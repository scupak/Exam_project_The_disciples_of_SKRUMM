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
public class AdminTasksViewController implements Initializable
{

    @FXML
    private JFXButton backBtn;
    @FXML
    private TableView<?> taskTableView;
    @FXML
    private JFXButton deleteBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleBack(ActionEvent event)
    {
    }

    @FXML
    private void handleDeleteTask(ActionEvent event)
    {
    }
    
}

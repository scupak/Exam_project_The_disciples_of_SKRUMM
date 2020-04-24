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
 * @author anton
 */
public class ClientsAndProjectsController implements Initializable {

    @FXML
    private JFXButton taskBtn;
    @FXML
    private JFXButton AdminBtn;
    @FXML
    private TableView<?> ClientList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handletaskView(ActionEvent event) {
    }

    @FXML
    private void handlecChartView(ActionEvent event) {
    }

    @FXML
    private void handlecAdminView(ActionEvent event) {
    }
    
}

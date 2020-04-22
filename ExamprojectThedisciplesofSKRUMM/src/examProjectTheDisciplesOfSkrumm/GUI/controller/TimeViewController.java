/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

/**
 * FXML Controller class
 *
 * @author kacpe
 */
public class TimeViewController implements Initializable {

    @FXML
    private TreeTableView<?> TaskTable;
    @FXML
    private TreeTableColumn<?, ?> TaskColumn;
    @FXML
    private TreeTableColumn<?, ?> ProjectColumn;
    @FXML
    private TreeTableColumn<?, ?> ClientColumn;
    @FXML
    private TreeTableColumn<?, ?> TimeColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class LogWindowController implements Initializable
{

    @FXML
    private JFXButton RefreshAdminLogBtn;
    @FXML
    private JFXButton ExportLogBtn;
    @FXML
    private ListView<String> AdminLog;
    
    private ModelFacadeInterface  modelfacade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            modelfacade = ModelFacade.getInstance();
            refreshAdminLog();
            /*
            ObservableList logitems = FXCollections.observableArrayList();
            logitems.add("Timestamp (DD-MM-YYYY - HH-MM-SS), Username, Action, Project, Task");
            logitems.add("Timestamp (DD-MM-YYYY - HH-MM-SS), Username, Action, Project, Task");
            logitems.add("Timestamp (DD-MM-YYYY - HH-MM-SS), Username, Action, Project, Task");
            logitems.add("Timestamp (DD-MM-YYYY - HH-MM-SS), Username, Action, Project, Task");
            */
           
        } catch (Exception ex) {
            Logger.getLogger(LogWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    

    @FXML
    private void HandleLogRefresh(ActionEvent event)
    {
        refreshAdminLog();
    }

    @FXML
    private void HandleLogExportTXT(ActionEvent event)
    {
    }

    private void refreshAdminLog() {
           AdminLog.getItems().clear();
        try {
            AdminLog.setItems(modelfacade.getAllLogs());
        } catch (SQLException ex) {
            Logger.getLogger(LogWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

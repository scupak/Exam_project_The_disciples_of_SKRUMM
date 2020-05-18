
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
 * @author Zanaxdk SKRUMM
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

    /**
     * Refreshes the log when the button is pressed 
     * @param event 
     */
    @FXML
    private void HandleLogRefresh(ActionEvent event)
    {
        refreshAdminLog();
    }

    /**
     * Would export the log as a txt file if that feautre wasn't scrapped
     * @param event 
     */
    @FXML
    private void HandleLogExportTXT(ActionEvent event)
    {
    }

    /**
     * Refreshes the log 
     */
    private void refreshAdminLog() {
           AdminLog.getItems().clear();
        try {
            AdminLog.setItems(modelfacade.getAllLogs());
        } catch (SQLException ex) {
            Logger.getLogger(LogWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

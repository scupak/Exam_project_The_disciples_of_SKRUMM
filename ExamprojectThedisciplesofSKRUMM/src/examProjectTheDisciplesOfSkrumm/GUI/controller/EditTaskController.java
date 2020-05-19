
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SKRUMM
 */
public class EditTaskController implements Initializable
{
    private boolean blank = true;
    private Task editTask;
    private ModelFacadeInterface modelfacade;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXButton editTaskBtn;
    @FXML
    private JFXTextField editTitleTextField;
    @FXML
    private JFXComboBox<Project> editProjectCombobox;
    @FXML
    private JFXDatePicker creationDatePicker;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try 
        {
            modelfacade = ModelFacade.getInstance();
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(EditTaskController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }
    }    


    /**
     * Closes the view 
     * @param event 
     */
    @FXML
    private void cancel(ActionEvent event)
    {
        Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createTaskView.close();
    }

    /**
     * Creates a new instance of task and then gives it to the update task method. 
     * @param event 
     */
    @FXML
    private void editTask(ActionEvent event)
    {
        if(!editTitleTextField.getText().isEmpty() && creationDatePicker.getValue() != null && editProjectCombobox.getValue() != null)
        {
            try
            {
                editTask.setTitle(editTitleTextField.getText());
                editTask.setProject(editProjectCombobox.getValue());
                editTask.setCreationDate(creationDatePicker.getValue());
            
                modelfacade.updateTask(editTask);
                Stage stage = (Stage) editTaskBtn.getScene().getWindow();
                stage.close();
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(EditTaskController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Missing input","ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Gets the edit task variable 
     * @return 
     */
    public Task getEditTask()
    {
        return editTask;
    }

    /**
     * Sets the combobox, datepicker and field to the information of the task being edited
     * @param editTask 
     */
    public void setEditTask(Task editTask)
    {
        this.editTask = editTask;
        
        editProjectCombobox.getItems().addAll(modelfacade.getProjects());
        editTitleTextField.setText(editTask.getTitle()); 
        creationDatePicker.setValue(editTask.getCreationDate());
        editProjectCombobox.getSelectionModel().select(editTask.getProject());
        editProjectCombobox.setValue(editTask.getProject());
    }
    
   
}

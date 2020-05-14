/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Zanaxdk <https://github.com/zanaxdk>
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Couldn't get model");
            alert.setContentText("" + ex);
            alert.showAndWait();   
        }
      
       
        

        
    }    


    @FXML
    private void cancel(ActionEvent event)
    {
        Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createTaskView.close();
    }

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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could contact the database");
                alert.setContentText("Something went wrong with the databe" + ex);
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing input");
            alert.setContentText("The input fields can't be blank, provide input.");
            alert.showAndWait();
        }
    }
    
    public Task getEditTask()
    {
        return editTask;
    }

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

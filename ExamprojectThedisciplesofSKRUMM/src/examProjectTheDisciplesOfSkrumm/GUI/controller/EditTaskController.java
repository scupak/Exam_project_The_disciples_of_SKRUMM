/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class EditTaskController implements Initializable
{
    private String editTitle;
    private String editTime;
    private int intTime;
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
    private JFXTextField editTimeTextField;
    @FXML
    private JFXComboBox<Project> editProjectCombobox;

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
            catch (Exception ex) {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
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
    editTitle = editTitleTextField.getText(); 
        
    if(editTitle != null && !editTitle.isEmpty()){      
        blank = false;
        System.out.println(blank);
        editTime = editTimeTextField.getText();

    if(editTime != null && !editTime.isEmpty()){
    try{
        blank = false;
        System.out.println(blank);
        intTime = Integer.parseInt(editTime);
                                            
    }
                                            
    catch(NumberFormatException e){
        JOptionPane.showMessageDialog(null, "New task duration has to be a whole number!");
        editTimeTextField.setText("EDIT ME");
        blank = true;
        System.out.println(blank);
        }                                     
    }

    else{
        JOptionPane.showMessageDialog(null, "New task duration can not be blank!\n Task duration must be an integer!");
        editTimeTextField.setText("EDIT ME");
        blank = true;
        System.out.println(blank);
    }
                                            
   if(blank == false){
        editTask.setTitle(editTitle);
        editTask.setDuration(intTime);
        editTask.setProject(editProjectCombobox.getValue());
   
   try
   {
        System.out.println(modelfacade.updateTask(editTask) + "  " +"update");
   }
   catch (SQLException ex)
   {
        JOptionPane.showMessageDialog(null, "Unknown SQL exception ocurred!");
   }

   }   
        }
            else{
            JOptionPane.showMessageDialog(null, "New task title can not be blank!");
            editTitleTextField.setText("EDIT ME");
            blank = true;
            System.out.println(blank);
        }
            
          /*  editTime = editTimeTextField.getText();
            
            if(editTime != null && !editTime.isEmpty()){
    
            try{
            intTime = Integer.parseInt(editTime);
            }
            catch(NumberFormatException e){
                
                JOptionPane.showMessageDialog(null, "New task duration has to be a whole number!");
                editTimeTextField.setText("EDIT ME");
                blank = true;
                System.out.println(blank);
                
            
            }
            
            blank = false;
            System.out.println(blank);
        }
            
            else{
            JOptionPane.showMessageDialog(null, "New task duration can not be blank!\n Task duration must be an integer!");
            editTimeTextField.setText("EDIT ME");
            blank = true;
            System.out.println(blank);
        }
            if(blank == false){
            editTask.setTitle(editTitle);
            editTask.setDuration(intTime);
            editTask.setProject(editProjectCombobox.getValue());
                try
                {
                    System.out.println(modelfacade.updateTask(editTask) + "  " +"update");
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null, "Unknown SQL exception ocurred!");
                }
                
            }*/
          
          Stage stage = (Stage) editTaskBtn.getScene().getWindow();
          stage.close();
    
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
        editTimeTextField.setText(Integer.toString(editTask.getDuration()));
        editProjectCombobox.getSelectionModel().select(editTask.getProject());
    }
}

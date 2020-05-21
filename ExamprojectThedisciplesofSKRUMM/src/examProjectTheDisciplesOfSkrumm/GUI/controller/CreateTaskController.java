
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author SKRUMM
 */
public class CreateTaskController implements Initializable 
{
    
    private ModelFacadeInterface modelfacade;
    @FXML
    private JFXButton createTaskBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXTextField titleTextField;
    @FXML
    private JFXComboBox<Project> projectCombobox;
    @FXML
    private JFXRadioButton paid;
    @FXML
    private JFXRadioButton notPaid;
    

   
    
    
    /**
     * Initialises the controller class
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
        
        try 
        {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) 
        {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        projectCombobox.getItems().addAll(modelfacade.getProjects());
        final ToggleGroup group = new ToggleGroup();
        paid.setToggleGroup(group);
        notPaid.setToggleGroup(group);
        paid.setSelected(true);
        paid.selectedColorProperty().set(Color.rgb(67, 90, 154));
        paid.setUnSelectedColor(Color.rgb(67, 90, 154));
        notPaid.selectedColorProperty().set(Color.rgb(67, 90, 154));
        notPaid.setUnSelectedColor(Color.rgb(67, 90, 154));
        
        
      
    }    

    /**
     * Creates a new task instance and gives it to the createTask method.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void createTask(ActionEvent event)
    {
        if(!titleTextField.getText().isEmpty() && !(projectCombobox.getValue() == null))
        {
            String title = titleTextField.getText();
            Project project = projectCombobox.getValue();
            int duration = 0;
            int isPaid = getIsPaid();
            LocalDateTime lastUsed = LocalDateTime.now();
            LocalDate creationDate = LocalDate.now();
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime stopTime = LocalDateTime.now();
            ArrayList<Interval> intervals = new ArrayList<Interval>();
            User user = modelfacade.getCurrentuser();
       
        
            Task newtask = new Task(1, title, project, isPaid, duration, lastUsed, creationDate, startTime, stopTime, user, intervals);
                
            //Create task should return the task it created
            modelfacade.createTask(newtask);
            Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            createTaskView.close();
            
             
            
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "Incorrect input","ERROR!", JOptionPane.ERROR_MESSAGE);
        }
       
    }

    /**
     * Closes the window
     * @param event 
     */
    @FXML
    private void cancel(ActionEvent event)
    {
        Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createTaskView.close();
    }

    /**
     * Sends the user to the add project view.
     * @param event
     * @throws IOException 
     */
    private void handleCreateNewProject(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADDPROJECT);
            Parent root = loader.load();
            AddProjectViewController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("TimeTracker");
            stage.showAndWait();
            projectCombobox.getItems().addAll(modelfacade.getProjects());
            paid.selectedColorProperty().set(Color.rgb(67, 90, 154));
        paid.setUnSelectedColor(Color.rgb(67, 90, 154));
        notPaid.selectedColorProperty().set(Color.rgb(67, 90, 154));
        notPaid.setUnSelectedColor(Color.rgb(67, 90, 154));
        }
        catch (IOException ex)
        {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to load in the addproject view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Recived the wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     /**
     * Gives the value of the radiobuttons
     * @return int
     */
    private int getIsPaid()
    {
        try
        {
            if(paid.isSelected())
            {
                return 1;
            }
            else if(notPaid.isSelected())
            {
                return 0;
            }
            else
            {
                throw new Exception();
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Something went wrong with the radiobuttons" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
         
    }

    /**
     * Makes sure the radiobuttons get set to the projets isPaid value
     * @param event 
     */
    @FXML
    private void handleCombobox(ActionEvent event)
    {
        if (projectCombobox.getValue().getIsPaid() == 0)
        {
            notPaid.setSelected(true);
            
        } else if (projectCombobox.getValue().getIsPaid() == 1)
        {
            paid.setSelected(true);
        }
        
    }
}


package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SKRUMM
 */
public class EditProjectViewController implements Initializable
{

    @FXML
    private JFXButton AddProjectOkBtn;
    @FXML
    private JFXButton AddProjectCancelBtn;
    @FXML
    private JFXTextField ProjectNameTextField;
    @FXML
    private JFXTextField ProjectRateTextField;
    @FXML
    private JFXButton addClientButton;
    @FXML
    private JFXComboBox<Client> clientComboBox;
    
    private ModelFacadeInterface modelfacade;
     
    private Project project;
    @FXML
    private JFXRadioButton paid;
    @FXML
    private JFXRadioButton notPaid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        clientComboBox.getItems().addAll(modelfacade.getClients());
        paid.selectedColorProperty().set(Color.rgb(67, 90, 154));
        paid.setUnSelectedColor(Color.rgb(67, 90, 154));
        notPaid.selectedColorProperty().set(Color.rgb(67, 90, 154));
        notPaid.setUnSelectedColor(Color.rgb(67, 90, 154));
    }    

    /**
     * Creates a new project instance and then gives it to the update project method. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void HandleEditProjectOkBtn(ActionEvent event)
    {
        Project sameProject = new Project(project.getId(), project.getProjectName(), project.getClient(), project.getProjectRate(), project.getIsPaid());
        
        if(project == sameProject)
        {
         Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addClientView.close();   
        }
     if (!ProjectNameTextField.getText().isEmpty() && !ProjectRateTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && getIsPaid() == 1)
        {
            try
            {
                int id = project.getId();
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = Integer.parseInt(ProjectRateTextField.getText());
                int isPaid = getIsPaid();
                Project newproject = new Project(id, projectName, client, projectRate, isPaid);
                modelfacade.updateProject(newproject);
                Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                createUserView.close();
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "You wrote a letter in project rate it needs a number" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex)
            {
                Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (!ProjectNameTextField.getText().isEmpty() && ProjectRateTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && getIsPaid() == 1)
        {
            try
            {
                int id = project.getId();
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = clientComboBox.getValue().getClientRate();
                int isPaid = getIsPaid();
                Project newproject = new Project(id, projectName, client, projectRate, isPaid);
                modelfacade.updateProject(newproject);
                Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                createUserView.close();
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "You wrote a letter in project rate it needs a number" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex)
            {
                Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (!ProjectNameTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && getIsPaid() == 0)
        {
            try
            {
                int id = project.getId();
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = 0;
                int isPaid = getIsPaid();
                Project newproject = new Project(id, projectName, client, projectRate, isPaid);
                modelfacade.updateProject(newproject);
                Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                createUserView.close();
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "You wrote a letter in project rate it needs a number" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex)
            {
                Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Missing input","ERROR!", JOptionPane.ERROR_MESSAGE);
        }   
    }

    /**
     * Closes the window 
     * @param event 
     */
    @FXML
    private void HandleAddProjectCancelBtn(ActionEvent event)
    {
        Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addClientView.close();
    }

    /**
     * Opens the add client view
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleAddClient(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADDCLIENT);
            Parent root = loader.load();
            AddClientController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(200);
            stage.setMinWidth(300);
            stage.setTitle("TimeTracker");
            stage.showAndWait();
            refreshClientComboBox();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to load in the addclient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Method was given the wrong viewtype" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Disables and enables the Project rate field based on the selected clients isPaid status
     * @param event 
     */
    @FXML
    private void handleCombobox(ActionEvent event)
    {
        if(clientComboBox.getValue() != null){
        if (clientComboBox.getValue().getIsPaid() == 0)
        {
            notPaid.setSelected(true);
            ProjectRateTextField.clear();
            ProjectRateTextField.setDisable(true);
        } else if (clientComboBox.getValue().getIsPaid() == 1)
        {
            paid.setSelected(true);
            ProjectRateTextField.setDisable(false);
        }
        }
    }

    

    /**
     * Refreshes the client combobox
     */
    public void refreshClientComboBox()
    {
        clientComboBox.getItems().clear();
        clientComboBox.getItems().addAll(modelfacade.getClients());
    }

    /**
     * Gets the project variable
     * @return 
     */
    public Project getProject()
    {
        return project;
    }

    /**
     * sets the fields and combobox to the information of the project being edited.
     * @param project 
     */
    public void setProject(Project project)
    {
        this.project = project;
        
        ProjectNameTextField.setText(project.getProjectName());
        ProjectRateTextField.setText(project.getProjectRate() + "");
        clientComboBox.setValue(project.getClient());
        final ToggleGroup group = new ToggleGroup();
        paid.setToggleGroup(group);
        notPaid.setToggleGroup(group);
        
        if (project.getIsPaid() == 0)
        {
            notPaid.setSelected(true);
        } else if (project.getIsPaid() == 1)
        {
            paid.setSelected(true);
        }
        
        for (Client item : clientComboBox.getItems())
        {
            if(item.getId() == project.getClient().getId())
            {
                int index = clientComboBox.getItems().indexOf(item);
                clientComboBox.getSelectionModel().select(index);
                clientComboBox.setValue(clientComboBox.getItems().get(index));
            }
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
            Logger.getLogger(EditProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Something went wrong with the radiobuttons" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        
        
    }

    @FXML
    private void handlePaidRadioButton(ActionEvent event)
    {
        if(notPaid.isSelected())
        {
            ProjectRateTextField.clear();
            ProjectRateTextField.setDisable(true);
        }
    }

    @FXML
    private void handleNotPaidRadiobutton(ActionEvent event)
    {
        if(paid.isSelected())
        {
            ProjectRateTextField.setDisable(false);
        }
    }
    
    
}

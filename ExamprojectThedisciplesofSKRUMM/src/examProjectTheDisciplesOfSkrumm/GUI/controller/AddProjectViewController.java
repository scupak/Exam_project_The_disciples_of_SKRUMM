
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * This class controls the addProject view
 * FXML Controller class
 *
 * @author SKRUMM
 */
public class AddProjectViewController implements Initializable
{

    private ModelFacadeInterface modelfacade;

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
    private ComboBox<Client> clientComboBox;

    
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            //The modelfacade gets instantieted here.
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) //This exception happens if for some reason the program is unable to get the instance of modelfacade, this is probably unnecessary
        {
            Logger.getLogger(AddProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't get the instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        clientComboBox.getItems().addAll(modelfacade.getClients());
    }

    /**
     * This method detects when a user presses the ok button and then attempts to make a new projects based on given parameters.
     * @param event 
     */
    @FXML
    private void HandleAddProjectOkBtn(ActionEvent event)
    {

        //First the method checks if all the input fields are filled in and that the choosen client is paid.
        if (!ProjectNameTextField.getText().isEmpty() && !ProjectRateTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && clientComboBox.getValue().getIsPaid() == 1)
        {
            try
            {
                //Here the input is taken from the fields and put into a new projects instance whitch is then sent to the database 
                int id = 1;
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = Integer.parseInt(ProjectRateTextField.getText());
                Project newproject = new Project(id, projectName, client, projectRate);
                modelfacade.CreateProject(newproject);
                Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                createUserView.close();
            } catch (NumberFormatException ex) //This error is triggered by the user writing a letter in the rate field.
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input");
                alert.setContentText("You wrote a letter in project rate, it needs a number.");
                alert.showAndWait();
            }
            //Then if the user has not overwritten the rate then the project will simply inherit the clients rate. 
        } else if (!ProjectNameTextField.getText().isEmpty() && ProjectRateTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && clientComboBox.getValue().getIsPaid() == 1)
        {
            try
            {
                int id = 1;
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = clientComboBox.getValue().getClientRate();
                Project newproject = new Project(id, projectName, client, projectRate);
                modelfacade.CreateProject(newproject);
                Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                createUserView.close();
            } catch (NumberFormatException ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input");
                alert.setContentText("You wrote a letter in project rate, it needs a number.");
                alert.showAndWait();
            }
        } else if (!ProjectNameTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && clientComboBox.getValue().getIsPaid() == 0)
        {
            try
            {
                int id = 1;
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = 0;
                Project newproject = new Project(id, projectName, client, projectRate);
                modelfacade.CreateProject(newproject);
                Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                createUserView.close();
            } catch (NumberFormatException ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input");
                alert.setContentText("You wrote a letter in project rate, it needs a number.");
                alert.showAndWait();
            }
        } else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Incorrect input");
            alert.setContentText("You didnt write a correct project name or didnt select a client");
            alert.showAndWait();
        }
    }

    /**
     * Checks if the user doeast want to be here anymore, then helps them out.
     * @param event 
     */
    @FXML
    private void HandleAddProjectCancelBtn(ActionEvent event)
    {
        Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createUserView.close();
    }

    /**
     * Checks if the user has clicked the addCLient button and then opens the addclient view if the have. 
     * @param event 
     */
    @FXML
    private void handleAddClient(ActionEvent event)
    {
        
        try {
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
        } catch (IOException ex) {
            Logger.getLogger(AddProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the addClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(AddProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the addClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    

    /**
     * This method refreshes the client combobox.
     */
    public void refreshClientComboBox()
    {
        clientComboBox.getItems().clear();
        clientComboBox.getItems().addAll(modelfacade.getClients());
    }

    /**
     * This method disables the project rate textfield if the client is not paid.
     * @param event 
     */
    @FXML
    private void handleCombobox(ActionEvent event)
    {
        if (clientComboBox.getValue().getIsPaid() == 0)
        {
            ProjectRateTextField.setDisable(true);
        } else if (clientComboBox.getValue().getIsPaid() == 1)
        {
            ProjectRateTextField.setDisable(false);
        }
    }

    
}

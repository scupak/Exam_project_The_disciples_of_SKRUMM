
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
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
    private JFXComboBox<Client> clientComboBox;
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
            //The modelfacade gets instantieted here.
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) //This exception happens if for some reason the program is unable to get the instance of modelfacade, this is probably unnecessary
        {
            Logger.getLogger(AddProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't get the instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        clientComboBox.getItems().addAll(modelfacade.getClients());
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
     * This method detects when a user presses the ok button and then attempts to make a new projects based on given parameters.
     * @param event 
     */
    @FXML
    private void HandleAddProjectOkBtn(ActionEvent event)
    {

        //First the method checks if all the input fields are filled in and that the choosen client is paid.
        if (!ProjectNameTextField.getText().isEmpty() && !ProjectRateTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && getIsPaid() == 1)
        {
            try
            {
                //Here the input is taken from the fields and put into a new projects instance whitch is then sent to the database 
                int id = 1;
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int isPaid = getIsPaid();
                int projectRate = Integer.parseInt(ProjectRateTextField.getText());
                Project newproject = new Project(id, projectName, client, projectRate, isPaid);
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
        } else if (!ProjectNameTextField.getText().isEmpty() && ProjectRateTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && getIsPaid() == 1)
        {
            try
            {
                int id = 1;
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = clientComboBox.getValue().getClientRate();
                int isPaid = getIsPaid();
                Project newproject = new Project(id, projectName, client, projectRate, isPaid);
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
        } else if (!ProjectNameTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && getIsPaid() == 0)
        {
            try
            {
                int id = 1;
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = 0;
                int isPaid = getIsPaid();
                Project newproject = new Project(id, projectName, client, projectRate, isPaid);
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
            Logger.getLogger(AddProjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Something went wrong with the radiobuttons" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        
        
    }

    @FXML
    private void handleNotPaidRadiobutton(ActionEvent event)
    {
        if(notPaid.isSelected())
        {
            ProjectRateTextField.clear();
            ProjectRateTextField.setDisable(true);
        }
    }

    @FXML
    private void handlePaidRadioButton(ActionEvent event)
    {
        if(paid.isSelected())
        {
            ProjectRateTextField.setDisable(false);
        }
    }

    
}

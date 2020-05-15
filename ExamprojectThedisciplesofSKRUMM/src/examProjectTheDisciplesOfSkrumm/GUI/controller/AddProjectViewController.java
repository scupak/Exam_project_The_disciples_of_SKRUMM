/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
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

/**
 * FXML Controller class
 *
 * @author Christina
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
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientComboBox.getItems().addAll(modelfacade.getClients());
    }

    @FXML
    private void HandleAddProjectOkBtn(ActionEvent event)
    {

        if (!ProjectNameTextField.getText().isEmpty() && !ProjectRateTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && clientComboBox.getValue().getIsPaid() == 1)
        {
            try
            {
                int id = 1;
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = Integer.parseInt(ProjectRateTextField.getText());
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

    @FXML
    private void HandleAddProjectCancelBtn(ActionEvent event)
    {
        Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createUserView.close();
    }

    @FXML
    private void handleAddClient(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddClient.fxml"));
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

    

    public void refreshClientComboBox()
    {
        clientComboBox.getItems().clear();
        clientComboBox.getItems().addAll(modelfacade.getClients());
    }

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

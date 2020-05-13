/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lumby
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
    private ComboBox<Client> clientComboBox;
    
    private ModelFacadeInterface modelfacade;
    
    private AdminClientsAndProjectsController adminClientsAndProjectsController;
    
     private AdminViewController adminviewcontroller;
     
     private Project project;

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
        }
        clientComboBox.getItems().addAll(modelfacade.getClients());
    }    

    @FXML
    private void HandleEditProjectOkBtn(ActionEvent event) throws SQLException
    {
        Project sameProject = new Project(project.getId(), project.getProjectName(), project.getClient(), project.getProjectRate());
        
        if(project == sameProject)
        {
         Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addClientView.close();   
        }
     if (!ProjectNameTextField.getText().isEmpty() && !ProjectRateTextField.getText().isEmpty() && !(clientComboBox.getValue() == null) && clientComboBox.getValue().getIsPaid() == 1)
        {
            try
            {
                int id = project.getId();
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = Integer.parseInt(ProjectRateTextField.getText());
                Project newproject = new Project(id, projectName, client, projectRate);
                modelfacade.updateProject(newproject);
                adminClientsAndProjectsController.RefreshTableView();
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
                int id = project.getId();
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = clientComboBox.getValue().getClientRate();
                Project newproject = new Project(id, projectName, client, projectRate);
                modelfacade.updateProject(newproject);
                adminClientsAndProjectsController.RefreshTableView();
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
                int id = project.getId();
                String projectName = ProjectNameTextField.getText();
                Client client = clientComboBox.getValue();
                int projectRate = 0;
                Project newproject = new Project(id, projectName, client, projectRate);
                modelfacade.CreateProject(newproject);
                adminClientsAndProjectsController.RefreshTableView();
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
        Stage addClientView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addClientView.close();
    }

    @FXML
    private void handleAddClient(ActionEvent event) throws IOException
    {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddClient.fxml"));
        Parent root = loader.load();
        AddClientController controller = loader.getController();
        controller.setEditProjectViewController(this);
        controller.setAdminClientsAndProjectsController(adminClientsAndProjectsController);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(200);
        stage.setMinWidth(300);
        stage.setTitle("TimeTracker");
        stage.show();
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

    public AdminClientsAndProjectsController getAdminClientsAndProjectsController()
    {
        return adminClientsAndProjectsController;
    }

    public void setAdminClientsAndProjectsController(AdminClientsAndProjectsController adminClientsAndProjectsController)
    {
        this.adminClientsAndProjectsController = adminClientsAndProjectsController;
    }

    public void refreshClientComboBox()
    {
        clientComboBox.getItems().clear();
        clientComboBox.getItems().addAll(modelfacade.getClients());
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
        
        ProjectNameTextField.setText(project.getProjectName());
        ProjectRateTextField.setText(project.getProjectRate() + "");
        clientComboBox.setValue(project.getClient());
        
    }
    
    
}

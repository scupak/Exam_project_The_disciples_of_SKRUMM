/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class AdminViewController implements Initializable
{
    
    ModelFacadeInterface modelfacade;
    
    @FXML
    private TableView<User> UserInfoAdminPane;
    @FXML
    private JFXButton AdminAddUsrBtn;
    @FXML
    private JFXButton AdminEditUsrBtn;
    @FXML
    private JFXButton AdminDeleteUsrBtn;
    @FXML
    private JFXButton AdminMakeAdminBtn;
    @FXML
    private JFXButton AdminRemoveAdminBtn;
    @FXML
    private Label AdminLabel;
    @FXML
    private JFXButton ExportTaskTImeBtn;
    @FXML
    private TableView<Project> ClientList;
    @FXML
    private JFXButton AdminAddProjectBtn;
    @FXML
    private JFXButton AdminEditProjectBtn;
    @FXML
    private JFXButton AdminDeleteProjectBtn;
    @FXML
    private TableColumn<Project, String> projectNameColumn;
    @FXML
    private TableColumn<Project, String> clientNameColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> isAdminColumn;
    @FXML
    private TableColumn<Project, Integer> projectRateColumn;

    /**
     * Phineas and Ferb
     * Candace
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //set up the columns in the table
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("clientName"));
        projectRateColumn.setCellValueFactory(new PropertyValueFactory<Project, Integer>("ProjectRate"));
        
       /** ObservableList<Project> getData = FXCollections.observableArrayList();
        
        
        Project project1 = new Project("Take over the world", new Client("Doofensmirtz"));
        Project project2 = new Project("do something fun",new Client("Phineas and Ferb"));
        Project project3 = new Project("bust brothers", new Client("Candace"));
        
        getData.add(project1);
        getData.add(project2);
        getData.add(project3);
        */
        
       ClientList.setItems(modelfacade.getProjects());
       
       
       //other tableview
       firstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
       lastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
       emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
       isAdminColumn.setCellValueFactory(new PropertyValueFactory<User, String>("isAdmin"));
       
       
       
        try {
            UserInfoAdminPane.setItems(modelfacade.getAllUsers());
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
       
    }    

    @FXML
    private void addUser(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/CreateUserView.fxml"));
        Parent root = loader.load();
        CreateUserViewController controller = loader.getController();
        controller.setAdminViewController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }

    @FXML
    private void editUser(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/CreateUserView.fxml"));
        Parent root = loader.load();
        CreateUserViewController controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }


    @FXML
    private void exportTaskTime(ActionEvent event)
    {
    }


    @FXML
    private void addProject(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddProjectView.fxml"));
        Parent root = loader.load();
        AddProjectViewController controller = loader.getController();
        controller.setAdminViewController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }

    @FXML
    private void editProject(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddProjectView.fxml"));
        Parent root = loader.load();
        AddProjectViewController controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }

    @FXML
    private void deleteProject(ActionEvent event) throws SQLException
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if((ClientList.getSelectionModel().getSelectedItem() == null))
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a project to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            int input = JOptionPane.showConfirmDialog(null, "delete task?", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (input == JOptionPane.YES_OPTION)
                {
                    modelfacade.deleteProject(ClientList.getSelectionModel().getSelectedItem());
                    RefreshTableView();
                }
        }
        
    }

    @FXML
    private void deleteUser(ActionEvent event)
    {
    }
    
    public void RefreshTableView()
    {
        ClientList.setItems(modelfacade.getProjects());
        
        try {
            UserInfoAdminPane.setItems(modelfacade.getAllUsers());
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}

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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class AdminViewController implements Initializable
{

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

    /**
     * Phineas and Ferb
     * Candace
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //set up the columns in the table
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("clientName"));
        
        ObservableList<Project> getData = FXCollections.observableArrayList();
        
        
        Project project1 = new Project("Take over the world", new Client("Doofensmirtz"));
        Project project2 = new Project("do something fun",new Client("Phineas and Ferb"));
        Project project3 = new Project("bust brothers", new Client("Candace"));
        
        getData.add(project1);
        getData.add(project2);
        getData.add(project3);
        
        
       ClientList.setItems(getData);
       
       
       //other tableview
       firstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
       lastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
       emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
       isAdminColumn.setCellValueFactory(new PropertyValueFactory<User, String>("isAdmin"));
       
       ObservableList<User> getData2 = FXCollections.observableArrayList();
       
       User user1 = new User("Mads", "Jensen", "mads@jensen.dk", "yes");
       User user2 = new User("Lars", "Larsen", "lars@larsen.dk", "no");
       User user3 = new User("Lea", "Evergarden", "lea@evergarden.dk", "yes");
       User user4 = new User("Tim", "Mcilrath", "tim@mcilrath.dk", "no");
       
       getData2.add(user1);
       getData2.add(user2);
       getData2.add(user3);
       getData2.add(user4);
       
       UserInfoAdminPane.setItems(getData2);
       
       
       
    }    

    @FXML
    private void addUser(ActionEvent event) throws IOException
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
    private void deleteProject(ActionEvent event)
    {
    }

    @FXML
    private void deleteUser(ActionEvent event)
    {
    }

    
}

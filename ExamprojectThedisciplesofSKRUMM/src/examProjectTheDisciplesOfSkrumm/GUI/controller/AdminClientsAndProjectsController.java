/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author lumby
 */
public class AdminClientsAndProjectsController implements Initializable
{

    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableColumn<Client, String> clientNameColumn;
    @FXML
    private TableColumn<Client, Integer> clientProjectsColumn;
    @FXML
    private TableView<Project> projectTableView;
    @FXML
    private TableColumn<Project, String> projectNameColumn;
    @FXML
    private TableColumn<Project, String> projectClientColumn;
    @FXML
    private TableColumn<Project, Integer> projectHoursColumn;
    @FXML
    private TableColumn<Project, Integer> projectIsPaidColumn;
    @FXML
    private JFXButton createClientBtn;
    @FXML
    private JFXButton editClientBtn;
    @FXML
    private JFXButton deleteClientBtn;
    @FXML
    private JFXButton createProjectBtn;
    @FXML
    private JFXButton editProjectBtn;
    @FXML
    private JFXButton deleteProjectBtn;
    @FXML
    private JFXButton backBtn;

    ModelFacadeInterface modelfacade;

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
        //set up clientTable
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientProjectsColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Client)
                {
                    return new ReadOnlyStringWrapper(((Client) dataObj).getnumberOfProjects() + "");
                } else
                {
                    return null;
                }
            }

        });

        clientTableView.setItems(modelfacade.getClients());

        //set projectTable
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        projectClientColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Project)
                {
                    return new ReadOnlyStringWrapper(((Project) dataObj).getClientName());
                } else
                {
                    return null;
                }
            }

        });
        //projectHoursColumn.setCellValueFactory(value);
        projectIsPaidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));

        projectTableView.setItems(modelfacade.getProjects());
        
    }

    @FXML
    private void handleCreateClient(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddClient.fxml"));
        Parent root = loader.load();
        AddClientController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Create Client");
        stage.show();
    }

    @FXML
    private void handleEditClient(ActionEvent event) throws IOException
    {
         FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditClient.fxml"));
        Parent root = loader.load();
        EditClientController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Client");
        stage.show();

    }

    @FXML
    private void handleDeleteClient(ActionEvent event)
    {
    }

    @FXML
    private void handleCreateProject(ActionEvent event)
    {
    }

    @FXML
    private void handleEditProject(ActionEvent event)
    {
    }

    @FXML
    private void handleDeleteProject(ActionEvent event)
    {
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        Stage adminClientsAndProjectsView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminMainView.fxml"));
        Parent root = loader.load();
        AdminMainViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(523);
        stage.setMinWidth(721);
        stage.setTitle("Main Menu");
        stage.show();
        adminClientsAndProjectsView.close();
    }

}

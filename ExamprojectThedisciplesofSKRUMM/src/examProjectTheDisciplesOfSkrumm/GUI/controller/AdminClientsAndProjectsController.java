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
import java.sql.SQLException;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

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
    private TableColumn<Client, Integer> clientRateColumn;
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
    private TableColumn<Project, ?> projectCreationColumn;
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

    private ModelFacadeInterface modelfacade;
    
   

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
        clientRateColumn.setCellValueFactory(new PropertyValueFactory<>("ClientRate"));

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
        //projectCreationColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

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
        stage.showAndWait();
        RefreshTableView();
    }

    @FXML
    private void handleEditClient(ActionEvent event) throws IOException
    {
         FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditClient.fxml"));
        Parent root = loader.load();
        EditClientController controller = loader.getController();
        controller.setClient(clientTableView.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Client");
        stage.showAndWait();
        RefreshTableView();

    }

    @FXML
    private void handleDeleteClient(ActionEvent event) throws SQLException
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if(clientTableView.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a client to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        Client client = clientTableView.getSelectionModel().getSelectedItem();
        int input = JOptionPane.showConfirmDialog(null, "delete client: " + client.getClientName() + "?", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if(input == JOptionPane.YES_OPTION)
        {
            modelfacade.deleteClient(client);
            RefreshTableView();
        }
        
    }

    @FXML
    private void handleCreateProject(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddProjectView.fxml"));
        Parent root = loader.load();
        AddProjectViewController controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Create Client");
        stage.showAndWait();
        RefreshTableView();
        
    }

    @FXML
    private void handleEditProject(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditProjectView.fxml"));
        Parent root = loader.load();
        EditProjectViewController controller = loader.getController();
        controller.setProject(projectTableView.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Project");
        stage.showAndWait();
        RefreshTableView();
    }

    @FXML
    private void handleDeleteProject(ActionEvent event) throws SQLException
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if(projectTableView.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a project to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        Project project = projectTableView.getSelectionModel().getSelectedItem();
        int input = JOptionPane.showConfirmDialog(null, "delete project: " + project.getProjectName() + "?", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if(input == JOptionPane.YES_OPTION)
        {
            modelfacade.deleteProject(project);
            RefreshTableView();
        }
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

    public void RefreshTableView()
    {
        clientTableView.setItems(modelfacade.getClients());
       
        projectTableView.setItems(modelfacade.getProjects());

    }

    @FXML
    private void handleSelectedClient(MouseEvent event) throws SQLException
    {
        if(clientTableView.getSelectionModel().getSelectedItem() != null)
        {
            projectTableView.setItems(modelfacade.getProjectsForClient(
                    clientTableView.getSelectionModel().getSelectedItem()));
        }
    }
}

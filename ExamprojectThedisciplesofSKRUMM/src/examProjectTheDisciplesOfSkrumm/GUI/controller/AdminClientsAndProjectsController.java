
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SKRUMM
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
    private TableColumn<Project, Integer> projectRateColumn;
    @FXML
    private TableColumn<Project, String> projectCreationColumn;
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
        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't get the instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
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
        projectHoursColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Project)
                {
                    return new ReadOnlyStringWrapper(((Project) dataObj).getFormatedTime());
                } else
                {
                    return null;
                }
            }

        });
        
        projectIsPaidColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Project)
                {                 
                    return new ReadOnlyStringWrapper(((Project) dataObj).getIsPaidBoolean());
                } else
                {
                    return null;
                }
            }

        });
        
        projectCreationColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Project)
                {                 
                    return new ReadOnlyStringWrapper(((Project) dataObj).getFormatedCreationDate());
                } else
                {
                    return null;
                }
            }

        });
        projectRateColumn.setCellValueFactory(new PropertyValueFactory<>("ProjectRate"));

        RefreshTableView();
        
    }

    /**
     * The eventhandler that activates the CreateClient window. 
     * @param event 
     */
    @FXML
    private void handleCreateClient(ActionEvent event)
    {
        try 
        {
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADDCLIENT);
            Parent root = loader.load();
            AddClientController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create Client");
            stage.setMinWidth(300);
            stage.setMinHeight(248);
            stage.showAndWait();
            RefreshTableView();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the addClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the addClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }
    }

    /**
     *  The eventhandler that activates the EditClient window. 
     * @param event 
     */
    @FXML
    private void handleEditClient(ActionEvent event)
    {
        try 
        {
            if ((clientTableView.getSelectionModel().getSelectedItem() == null))
            {
                JOptionPane.showMessageDialog(null, "Nothing seems to be selected!\nSelect a client to edit before pressing edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                FXMLLoader loader = modelfacade.getLoader(ViewTypes.EDITCLIENT);
                Parent root = loader.load();
                EditClientController controller = loader.getController();
                controller.setClient(clientTableView.getSelectionModel().getSelectedItem());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Client");
                stage.setMinWidth(300);
                stage.setMinHeight(200);
                stage.showAndWait();
                RefreshTableView();
            }
            
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the editClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the addClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     *  The eventhandler that deletes the client
     * @param event 
     */
    @FXML
    private void handleDeleteClient(ActionEvent event)
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if(clientTableView.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a client to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            Client client = clientTableView.getSelectionModel().getSelectedItem();
            int input = JOptionPane.showConfirmDialog(null, "delete client: " + client.getClientName() + "?", "Select an Option...",
            JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if(input == JOptionPane.YES_OPTION)
            {
                try 
                {
                    modelfacade.deleteClient(client);
                    RefreshTableView();
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Couldn't delete the client from the databse" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        }
        
        
        
    }
    
    /**
     *  The eventhandler that activates the CreateProject window. 
     * @param event 
     */
    @FXML
    private void handleCreateProject(ActionEvent event)
    {
        try 
        {
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADDPROJECT);
            Parent root = loader.load();
            AddProjectViewController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create Project");
            stage.setMinWidth(320);
            stage.setMinHeight(250);
            stage.showAndWait();
            RefreshTableView();
        }
        catch (IOException ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the createClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the createClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *  The eventhandler that activates the EditProject window.
     * @param event 
     */
    @FXML
    private void handleEditProject(ActionEvent event)
    {
        try 
        {
            if ((projectTableView.getSelectionModel().getSelectedItem() == null))
            {
                JOptionPane.showMessageDialog(null, "Nothing seems to be selected!\nSelect a project to edit before pressing edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                FXMLLoader loader = modelfacade.getLoader(ViewTypes.EDITPROJECT);
                Parent root = loader.load();
                EditProjectViewController controller = loader.getController();
                controller.setProject(projectTableView.getSelectionModel().getSelectedItem());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Project");
                stage.setMinWidth(350);
                stage.setMinHeight(250);
                stage.showAndWait();
                RefreshTableView();
            }
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the editClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the editClient view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This handler deletes a selected project
     * @param event 
     */
    @FXML
    private void handleDeleteProject(ActionEvent event)
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if(projectTableView.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a project to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            Project project = projectTableView.getSelectionModel().getSelectedItem();
            int input = JOptionPane.showConfirmDialog(null, "delete project: " + project.getProjectName() + "?", "Select an Option...",
            JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if(input == JOptionPane.YES_OPTION)
            {
                try 
                {
                    modelfacade.deleteProject(project);
                    RefreshTableView();
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Couldn't delete project from the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }  
        }
        
        
    }

    /**
     * This method handles the back button and sends the user back to the main view
     * @param event 
     */
    @FXML
    private void handleBack(ActionEvent event)
    {
        try 
        {
            Stage adminClientsAndProjectsView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADMINMAIN);
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
        catch (IOException ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the adminMain view view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the adminMain view view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method refreshes the tableview. 
     */
    public void RefreshTableView()
    {
        
        clientTableView.setItems(modelfacade.getClients());
       
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> 
        {
            ObservableList<Project> projects = FXCollections.observableArrayList();
            projects.addAll(modelfacade.getProjects());
            
            ObservableList<Client> clients = FXCollections.observableArrayList();
            clients.addAll(modelfacade.getClients());
            
            Platform.runLater( () -> 
            {
                projectTableView.setItems(projects);
                clientTableView.setItems(clients);
            });
        });
        
        executor.shutdown();
    }

    /**
     * Displays the selected clients projects.
     * @param event 
     */
    @FXML
    private void handleSelectedClient(MouseEvent event)
    {
        if(clientTableView.getSelectionModel().getSelectedItem() != null)
        {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> 
            {
                try 
                {
                    ObservableList<Project> projects = FXCollections.observableArrayList();
                    projects.addAll(modelfacade.getProjectsForClient(clientTableView.getSelectionModel().getSelectedItem()));
                    
                    Platform.runLater( () ->
                    {
                        projectTableView.setItems(projects);
                    });
                } 
                catch (SQLException ex) 
                {
                    Platform.runLater( () ->
                    {
                        JOptionPane.showMessageDialog(null, "Couldn't get the projects for the client from the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    });
                }
            });
            
            executor.shutdown();
        }
    }

    /**
     * This method displays the chart for a project when a user double clicks on it. 
     * @param event 
     */
    @FXML
    private void handleProjectChartPerUser(MouseEvent event) 
    {
        
         final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if (event.getClickCount() == 2)
        {
            if (projectTableView.getSelectionModel().getSelectedItem() != null)
            {
                
                try
                {
                   Project clickedProject = projectTableView.getSelectionModel().getSelectedItem();
                    
                    
                    //Stage currentview = (Stage) ((Node) event.getSource()).getScene().getWindow();

                   FXMLLoader loader = modelfacade.getLoader(ViewTypes.CHART);
                   Parent root = loader.load();
                   ChartViewController controller = loader.getController();
                   
                   controller.setCurrentProject(clickedProject);
                   controller.getBackBtn().setVisible(false);
                   controller.getBackBtn().setDisable(false);
                   controller.getNameLabel().setText(clickedProject.getProjectName());
                   controller.getxAxisInBarChart().setLabel("Users");

                   Stage stage = new Stage();
                   stage.setScene(new Scene(root));
                   stage.setMinHeight(523);
                   stage.setMinWidth(721);
                   stage.setTitle("Statistics");
                   stage.show();
                   //currentview.close();
                   
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Couldn't load the chart view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                } 
                catch (Exception ex)
                {
                    Logger.getLogger(AdminClientsAndProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Couldn't load the chart view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                }
                

            }
        }
    }
    }



package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SKRUMM
 */
public class AdminTasksViewController implements Initializable
{

    @FXML
    private JFXButton backBtn;
    @FXML
    private TableView<Task> taskTableView;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<Task, String> taskProjectColumn;
    @FXML
    private TableColumn<Task, String> taskClientColumn;
    @FXML
    private TableColumn<Task, String> taskUserColumn;
    @FXML
    private TableColumn<Task, String> taskDurationColumn;
    @FXML
    private TableColumn<Task, String> taskIsPaidColumn;
    @FXML
    private JFXButton deleteBtn;
    
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
            Logger.getLogger(AdminTasksViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't get the instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        
        //set the table
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskProjectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        taskClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        
        // need to find out what is wrong with it
        taskUserColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getUserEmail());
                } else
                {
                    return null;
                }
            }

        });
        taskDurationColumn.setCellValueFactory(new PropertyValueFactory<>("formatedDuration"));
        taskIsPaidColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getIsPaidBoolean());
                } else
                {
                    return null;
                }
            }

        });
        
            refreshTableView();
        
        
    }    

    /**
     * Handles the button that takes the user back to the admin main view
     * @param event 
     */
    @FXML
    private void handleBack(ActionEvent event)
    {
        try
        {
            Stage adminTasksView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADMINMAIN);
            Parent root = loader.load();
            AdminMainViewController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(523);
            stage.setMinWidth(721);
            stage.setTitle("Main Menu");
            stage.show();
            adminTasksView.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(AdminTasksViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the admin main view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AdminTasksViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the admin main view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the button that deletes a task.
     * @param event 
     */
    @FXML
    private void handleDeleteTask(ActionEvent event)
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if(taskTableView.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a task to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        Task task = taskTableView.getSelectionModel().getSelectedItem();
        int input = JOptionPane.showConfirmDialog(null, "delete task: " + task.getTitle()+ "?", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if(input == JOptionPane.YES_OPTION)
        {
            try {
                modelfacade.deleteTask(task);
                refreshTableView();
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(AdminTasksViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Couln't delete the task from the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Refreshes the tableview
     */
    public void refreshTableView()
    {
       ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> 
            {
                try 
                {
                    ObservableList<Task> tasks = FXCollections.observableArrayList();
                    tasks.addAll(modelfacade.getAllTasks());
                    
                    Platform.runLater( () ->
                    {
                        taskTableView.setItems(tasks);
                    });
                } 
                catch (SQLException ex) 
                {
                    Platform.runLater( () ->
                    {
                        Logger.getLogger(AdminTasksViewController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Couln't get all the tasks from the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    });
                }
            });
            
            executor.shutdown();
    }
    
}

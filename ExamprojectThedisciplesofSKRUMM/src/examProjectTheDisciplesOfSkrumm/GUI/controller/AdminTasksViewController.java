/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lumby
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
        taskIsPaidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        
        try
        {
            taskTableView.setItems(modelfacade.getAllTasks());
        } catch (SQLException ex)
        {
            Logger.getLogger(AdminTasksViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        Stage adminTasksView = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
        adminTasksView.close();
    }

    @FXML
    private void handleDeleteTask(ActionEvent event) throws SQLException
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if(taskTableView.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a project to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        Task task = taskTableView.getSelectionModel().getSelectedItem();
        int input = JOptionPane.showConfirmDialog(null, "delete project: " + task.getTitle()+ "?", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if(input == JOptionPane.YES_OPTION)
        {
            modelfacade.deleteTask(task);
            refreshTableView();
        }
    }
    
    public void refreshTableView()
    {
      try
        {
            taskTableView.setItems(modelfacade.getAllTasks());
        } catch (SQLException ex)
        {
            Logger.getLogger(AdminTasksViewController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
}

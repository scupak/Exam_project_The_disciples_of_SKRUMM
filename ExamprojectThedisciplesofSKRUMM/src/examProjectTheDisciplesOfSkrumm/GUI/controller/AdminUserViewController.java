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
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lumby
 */
public class AdminUserViewController implements Initializable
{

    @FXML
    private JFXButton createUserBtn;
    @FXML
    private JFXButton editBtn;
    @FXML
    private JFXButton deleteUser;
    @FXML
    private JFXButton adminBtn;
    @FXML
    private JFXButton backBtn;
    @FXML
    private TableView<User> UserTableView;
    
    ModelFacadeInterface modelfacade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            //Getting the model
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void handleCreateUser(ActionEvent event)
    {
    }

    @FXML
    private void handleEditUser(ActionEvent event) throws IOException
    {
         final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        if ((UserTableView.getSelectionModel().getSelectedItem() == null))
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a user to edit before pressing edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else
        {

            User selectedUser =  UserTableView.getSelectionModel().getSelectedItem();

            

               

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditUserView.fxml"));
                Parent root = loader.load();
                EditUserController controller = loader.getController();
                controller.setEditUser(selectedUser);
                controller.setAdminUserViewController(this);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setMinHeight(288);
                stage.setMinWidth(346);
                stage.setTitle("Edit User");
                stage.show();

            

        }
    }

    @FXML
    private void handleDeleteUser(ActionEvent event)
    {
    }

    @FXML
    private void handleMakeAdmin(ActionEvent event)
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
    
    public void refreshTableview()
    {
        
    }
    
}

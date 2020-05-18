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
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> adminColumn;

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
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Could not get the instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        

        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName")
        );

        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName"));

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("email"));

        adminColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("isAdmin"));

        try
        {
            refreshTableview();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Could not refresh the tableview" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void handleCreateUser(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.CREATEUSER);
            Parent root;
            root = loader.load();
            CreateUserViewController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create user");
            stage.setMinHeight(288);
            stage.setMinWidth(346);
            stage.showAndWait();
            refreshTableview();
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load the create user view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);

        } 
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Something went wrong in the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Wrong view type used " + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
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

            User selectedUser = UserTableView.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditUserView.fxml"));
            Parent root = loader.load();
            EditUserController controller = loader.getController();
            controller.setEditUser(selectedUser);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(288);
            stage.setMinWidth(346);
            stage.setTitle("Edit User");
            stage.showAndWait();
            try
            {
                refreshTableview();
            } catch (SQLException ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Cant refresh" + ex);
                alert.setContentText("Please try again");
                alert.showAndWait();
            }

        }
    }

    @FXML
    private void handleDeleteUser(ActionEvent event)
    {
        User user = UserTableView.getSelectionModel().getSelectedItem();
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        try
        {
            if (user == null)
            {
                JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a user to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
                refreshTableview();
            } else if (modelfacade.userExist(modelfacade.getUser(user)) != true)
            {
                JOptionPane.showMessageDialog(dialog, "User does not seem to exist!\nSelect an already existing user to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
                refreshTableview();
            } else if (user.getEmail().equals(modelfacade.getCurrentuser().getEmail()))
            {
                JOptionPane.showMessageDialog(dialog, "You can not delete yourself!\nselect another user that is not you!", "ERROR", JOptionPane.ERROR_MESSAGE);
                refreshTableview();
            } else
            {
                modelfacade.deleteUser(user);
                refreshTableview();
            }
        } catch (SQLException karl)
        {
            karl.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "Database connection error\n Error code; Karl\n" + karl, "Karl", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void handleMakeAdmin(ActionEvent event) throws SQLException
    {
         final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        User clickedUser = UserTableView.getSelectionModel().getSelectedItem();
        User unchangeUser = clickedUser;
        
        if(clickedUser != null)
        {
            if(clickedUser.getEmail().equals(modelfacade.getCurrentuser().getEmail()))
            {
                JOptionPane.showMessageDialog(dialog, "You can not change your own admin status!"
                        + "\nselect another user that is not you!", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(clickedUser.getIsAdmin().equals(true))
            {
                clickedUser.setIsAdmin(false);
            }else
            {
                clickedUser.setIsAdmin(true);
            }
            modelfacade.updateUser(unchangeUser, clickedUser);
            refreshTableview();
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

    public void refreshTableview() throws SQLException
    {
        UserTableView.setItems(modelfacade.getAllUsers());
    }

    @FXML
    private void handleOpenUserView(MouseEvent event) throws IOException
    {
        Stage adminView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if (event.getClickCount() == 2)
        {
            if (UserTableView.getSelectionModel().getSelectedItem() != null)
            {
                if(UserTableView.getSelectionModel().getSelectedItem().getEmail()
                        .equals(modelfacade.getCurrentuser().getEmail()))
                {
                    JOptionPane.showMessageDialog(dialog, "You can not select yourself!\nselect another user that is not you!", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else{

                User clickedUser = UserTableView.getSelectionModel().getSelectedItem();
                modelfacade.setCurrentAdmin(modelfacade.getCurrentuser());
                modelfacade.setCurrentuser(clickedUser);

                FXMLLoader loader = new FXMLLoader(getClass().
                        getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/TaskView.fxml"));
                Parent root = loader.load();
                TaskViewController controller = loader.getController();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setMinHeight(523);
                stage.setMinWidth(721);
                stage.setTitle("Main Menu");
                stage.show();
                adminView.close();
                }

            }
        }
    }

}

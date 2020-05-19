
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * @author SKRUMM
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

        refreshTableview();
    }

    /**
     * Handles the button that creates a new user.
     * @param event 
     */
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
            Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Could not load the create user view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Something went wrong in the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong view type used " + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the button that that detect which user the user has selected and sends them to the edit user view.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleEditUser(ActionEvent event)
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        if ((UserTableView.getSelectionModel().getSelectedItem() == null))
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a user to edit before pressing edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
        else
        {

            try
            {
                User selectedUser = UserTableView.getSelectionModel().getSelectedItem();
                
                FXMLLoader loader = modelfacade.getLoader(ViewTypes.EDITUSER);
                Parent root = loader.load();
                EditUserController controller = loader.getController();
                controller.setEditUser(selectedUser);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setMinHeight(288);
                stage.setMinWidth(346);
                stage.setTitle("Edit User");
                stage.showAndWait();
                refreshTableview();
                
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(dialog, "Failed to contact the database" + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
            } 
            catch (Exception ex)
            {
                Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(dialog, "Given wrong viewtype" + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the button for deleteing users and calls the delete user method in modelfacade. 
     * @param event 
     */
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
        } catch (SQLException ex)
        {
            Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(dialog, "Database connection error\n Error code; Karl\n" + ex, "Karl", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    /**
     * Makes the selected user and admin or if it already is an admin revokes that privilige from it.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handleMakeAdmin(ActionEvent event)
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        User clickedUser = UserTableView.getSelectionModel().getSelectedItem();
        User unchangeUser = clickedUser;
        
        if(clickedUser != null)
        {
            try 
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
            catch (SQLException ex) 
            {
                Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(dialog, "Database connection error\n Error code; Karl\n" + ex, "Karl", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the button that sends the user back to the admin main view.
     * @param event
     * @throws IOException 
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
            Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load the view" + ex, "Karl", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong viewtype given" + ex, "Karl", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Refreshes the tableview
     * @throws SQLException 
     */
    public void refreshTableview()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> 
            {
                try 
                {
                    ObservableList<User> users = FXCollections.observableArrayList();
                    users.addAll(modelfacade.getAllUsers());
                    
                    Platform.runLater( () ->
                    {
                        UserTableView.setItems(users);
                    });
                } 
                catch (SQLException ex) 
                {
                    Platform.runLater( () ->
                    {
                        Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Couln't get all the users from the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    });
                }
            });
            
            executor.shutdown();
    }

    /**
     * Sends the admin into the selected users task view. 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleOpenUserView(MouseEvent event)
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

                    try
                    {
                        User clickedUser = UserTableView.getSelectionModel().getSelectedItem();
                        modelfacade.setCurrentAdmin(modelfacade.getCurrentuser());
                        modelfacade.setCurrentuser(clickedUser);
                        
                        FXMLLoader loader = modelfacade.getLoader(ViewTypes.TASK);
                        Parent root = loader.load();
                        TaskViewController controller = loader.getController();
                        
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setMinHeight(523);
                        stage.setMinWidth(721);
                        stage.setTitle("Task view");
                        stage.show();
                        adminView.close();
                    } 
                    catch (IOException ex)
                    {
                        Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(dialog, "Couln't load the view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception ex)
                    {
                        Logger.getLogger(AdminUserViewController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(dialog, "Wrong viewtype given" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        }
    }

}

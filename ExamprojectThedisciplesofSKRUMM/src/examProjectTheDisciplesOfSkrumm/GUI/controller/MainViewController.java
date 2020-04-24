/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christina
 */
public class MainViewController implements Initializable {

    @FXML
    private JFXButton AdminBtn;
    private GridPane taskGrid;
    @FXML
    private JFXButton taskBtn;

    private boolean adminCheck;
    @FXML
    private JFXButton clientsProjectBtn;
    @FXML
    private AnchorPane anchorPane00;

    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private boolean running = false;
    @FXML
    private Label timeLabe00;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        ColumnConstraints halfConstraint = new ColumnConstraints(50);
        taskGrid.getColumnConstraints().addAll(halfConstraint,halfConstraint); 
         */

        // anchorPane00.setUserData(new Task("title", "projectName", "clientName", 0) );
    }

    public MainViewController() {
        adminCheck = false;
    }

    public void setAdminCheck(boolean adminCheck) {
        this.adminCheck = adminCheck;
    }

    @FXML
    private void handleChartView(ActionEvent event) throws IOException {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ChartView.fxml"));
        Parent root = loader.load();
        ChartViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();

    }

    @FXML
    private void handleAdminView(ActionEvent event) throws IOException {
        if (adminCheck == true) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminView.fxml"));
            Parent root = loader.load();
            AdminViewController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(423);
            stage.setMinWidth(721);
            stage.setTitle("TimeTracker");
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("You do not have permision");
            alert.setContentText("It looks like you are not an admin user");
            alert.showAndWait();
        }
    }

    @FXML
    private void handletaskView(ActionEvent event) throws IOException {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/TaskView.fxml"));
        Parent root = loader.load();
        TaskViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();
    }

    @FXML
    private void handleClientProject(ActionEvent event) throws IOException {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ClientsAndProjects.fxml"));
        Parent root = loader.load();
        ClientsAndProjectsController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(355);
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();
    }

    private void handleLogOut(ActionEvent event) throws IOException {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/LoginView.fxml"));
        Parent root = loader.load();
        LoginViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(200);
        stage.setMinWidth(300);
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();
    }

    @FXML
    private void handleStartTimer(ActionEvent event) {

        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("javafx.runtime.version"));
        
        System.out.println("start");

        if (running) {

            running = false;

        } else if (!running) {
            running = true;

        }

        new Thread(()
                -> {
            while (true) {

                if (running) {

                    try {
                        Thread.sleep(1000);

                        sec++;

                        if (sec >= 60) {
                            min++;
                            sec = 0;
                        }

                        if (min >= 60) {
                            hour++;
                            min = 0;
                        }

                        String time = String.format("%02d", hour) + " : " + String.format("%02d", min) + " : " + String.format("%02d", sec);
                        System.out.println(time);
                        Platform.runLater(()
                                -> {
                            timeLabe00.setText(String.format("%02d", hour) + " : " + String.format("%02d", min) + " : " + String.format("%02d", sec));
                        }
                        );

                    } catch (Exception e) {
                    }

                } else {

                    break;

                }

            }

        }).start();

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author anton
 */
public class Exam_project_The_disciples_of_SKRUMM extends Application 
{
    ModelFacadeInterface modelfacade;
    
    @Override
    public void start(Stage stage) throws Exception {
        modelfacade = ModelFacade.getInstance();
        Client client = new Client(1, "THEM", 666, 1);
        modelfacade.createClient(client);
        Project project1 = new Project(1,"Project X", client, 2);
        Project project2 = new Project(1,"Operation operation", client, 4);
        Project project3 = new Project(1,"THE BIG ONE", client, 8);
        modelfacade.CreateProject(project1);
        modelfacade.CreateProject(project2);
        modelfacade.CreateProject(project3);
        Task interval1 = new Task(1, "Developing apps", project3, 432240, LocalDateTime.now(), LocalDate.now(), LocalTime.of(1, 20, 50), LocalTime.of(3, 24, 50), new ArrayList());
        ArrayList<Task> intervals = new ArrayList<Task>();
        intervals.add(interval1);
        Task task1 = new Task(1, "Making candy", project1, 0, LocalDateTime.now(), LocalDate.now(), LocalTime.MIN, LocalTime.MIN, new ArrayList());
        Task task2 = new Task(1, "Developing apps", project3, 532240, LocalDateTime.now(), LocalDate.now(), LocalTime.MIN, LocalTime.MIN, intervals);
        
        modelfacade.createTask(task1);
        modelfacade.createTask(task2);
                
        Parent root = FXMLLoader.load(getClass().getResource("GUI/view/LoginView.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("GUI/view/TimeView.fxml"));
        Scene scene = new Scene(root);
//        stage.setMinHeight(525);
//        stage.setMinWidth(726);
        stage.setScene(scene); 
        stage.show();
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

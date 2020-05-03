/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.TaskModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.controller.TaskViewController;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.util.List;

/**
 *
 * @author kacpe
 */
public class TaskModel implements TaskModelInterface
{
    private final BLLFacadeInterface bllfacade;
    private ObservableList<Task> tasks;

    TaskModel() throws IOException
    {
        bllfacade = new BLLFacade();
       
        
    }

    

    @Override
    public TreeItem<Task> getModel() 
    {
        return bllfacade.getModel(tasks);
    }

    @Override
    public void createTask(Task task) 
    {
        //tasks.add(task);
        
       
        
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }
    
    @Override
    public void newInterval(Interval interval) throws SQLServerException, SQLException
    {
        System.out.println(interval.getStartTime() + "!!!!!");
        bllfacade.newInterval(interval);
    }
    
    @Override
    public ObservableList<Task> getSixTasks(User user) throws SQLException
    {
        //ArrayList<Task> sixTasks = (ArrayList<Task>) bllfacade.getSixTasks(user);
        ObservableList<Task> sixTasks = FXCollections.observableArrayList();
        sixTasks.addAll(bllfacade.getSixTasks(user));
        return sixTasks;
    }
    
    
}



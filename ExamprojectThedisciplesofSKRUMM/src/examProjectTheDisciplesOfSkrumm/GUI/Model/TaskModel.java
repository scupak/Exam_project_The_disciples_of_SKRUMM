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
import examProjectTheDisciplesOfSkrumm.BLL.Util.TimerUtil;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javafx.scene.control.Alert;

/**
 *
 * @author kacpe
 */
public class TaskModel implements TaskModelInterface
{
    private final BLLFacadeInterface bllfacade;
    private ObservableList<Task> tasks;
    private boolean isTimerRunning;
    private TimerUtil timerutil = null;
    private ExecutorService executorService;
    ObservableList<Task> sixTasks;

    TaskModel() throws IOException
    {
       bllfacade = new BLLFacade();
       tasks = FXCollections.observableArrayList();
       isTimerRunning = false;
      
        
    }

    
    
    @Override
    public TreeItem<Task> getModel(User user, LocalDate fromdate, LocalDate todate) 
    {
        try {
            tasks.clear();
            tasks.addAll(getTasksForUserbetween2Dates(user, fromdate, todate));
        } catch (SQLException ex) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("Could connect to database\n" + ex);
           alert.setContentText("Please try again");
           alert.showAndWait(); 
        }
        return bllfacade.getModel(tasks);
    }

    @Override
    public void createTask(Task task) 
    {
        try {
            bllfacade.createTask(task);
        } catch (SQLException ex) {
            Logger.getLogger(TaskModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         
    }

    @Override
    public ObservableList<Task> getTasks() {
        return tasks;
    }

    @Override
    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }
    
    @Override
    public void newInterval(Interval interval) throws SQLServerException, SQLException
    {
        bllfacade.newInterval(interval);
    }
    
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return bllfacade.updateInterval(oldInterval, newInterval);
    }
    
    @Override
    public ObservableList<Task> getSixTasks(User user) throws SQLException
    {
        //ArrayList<Task> sixTasks = (ArrayList<Task>) bllfacade.getSixTasks(user);
        sixTasks = FXCollections.observableArrayList();
        sixTasks.addAll(bllfacade.getSixTasks(user));
        return sixTasks;
    }
    
    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return bllfacade.getTasksForUser(user, date);
    }

    @Override
    public ObservableList<Task> getAllTasks() throws SQLException
    {
        ObservableList<Task> allTasks = FXCollections.observableArrayList();
        allTasks.addAll(bllfacade.getAllTasks());
        return allTasks;
    }

    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return bllfacade.taskExist(task);
    }

    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        for (Task tasks : sixTasks)
        {
            if(tasks.getId() == task.getId())
            {
                tasks.setDuration(task.getDuration());
                tasks.setProject(task.getProject());
                tasks.setTitle(task.getTitle());
            }
                     
        }
        return bllfacade.updateTask(task);
    }

    @Override
    public Task getTask(Task task) throws SQLException
    {
        return bllfacade.getTask(task);
    }

    @Override
    public String convertSecToTimeString(int totalSec) {
       return bllfacade.convertSecToTimeString(totalSec);
    }

    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
        return bllfacade.deleteTask(task);
    }

    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return bllfacade.clearTask(task);
    }
    
    public boolean getisTimerRunning() {
        
        System.err.println(isTimerRunning + "is timer running");
        return isTimerRunning;
    }

    @Override
    public void setIsTimerRunning(boolean isTimerRunning) {
        this.isTimerRunning = isTimerRunning;
    }

    @Override
    public TimerUtil getTimerutil() {
        return timerutil;
    }

    @Override
    public void setTimerutil(TimerUtil timerutil) {
        this.timerutil = timerutil;
    }

    @Override
    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public boolean deleteInterval(Interval interval) throws SQLException {
        return bllfacade.deleteInterval(interval);
    }

    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException {
        return bllfacade.getTasksForUserbetween2Dates(user, fromdate, todate);
    }
    
    
    
    
    
    
    
}



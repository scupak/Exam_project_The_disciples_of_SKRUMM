/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.TaskModelInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TimerUtil;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javafx.scene.control.Alert;

/**
 *
 * @author SKRUMM
 */
public class TaskModel implements TaskModelInterface
{
    private final BLLFacadeInterface bllfacade;
    private ObservableList<Task> tasks;
    private boolean isTimerRunning;
    private TimerUtil timerutil = null;
    private ExecutorService executorService;
    ObservableList<Task> sixTasks;

    TaskModel() throws IOException, Exception
    {
       bllfacade = new BLLFacade();
       tasks = FXCollections.observableArrayList();
       isTimerRunning = false;
      
        
    }

    
    /**
     * gets a list of tasks from the DB and makes them root treeitems
     * @param user
     * @param fromdate
     * @param todate
     * @return a list of tasks from the DB and makes them root treeitems
     */
    @Override
    public synchronized TreeItem<Task> getModel(User user, LocalDate fromdate, LocalDate todate) 
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
    
    /**
     * creates a task
     * @param task 
     */
    @Override
    public void createTask(Task task) 
    {
        try {
            bllfacade.createTask(task);
        } catch (SQLException ex) {
            Logger.getLogger(TaskModel.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * gets a list of tasks
     * @return a list of tasks
     */
    @Override
    public ObservableList<Task> getTasks() {
        return tasks;
    }
    
    /**
     * sets the tasks to the given list
     * @param tasks 
     */
    @Override
    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }
    
    /**
     * makes a new interval
     * @param interval
     * @throws SQLException
     * @throws SQLServerException 
     */
    @Override
    public void newInterval(Interval interval) throws SQLServerException, SQLException
    {
        bllfacade.newInterval(interval);
    }
    
    /**
     * updates the interval
     * @param oldInterval
     * @param newInterval
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return bllfacade.updateInterval(oldInterval, newInterval);
    }
    
    /**
     * gets the six most recent tasks for the user
     * @param user
     * @return the six most recent tasks for the user
     * @throws SQLException 
     */
    @Override
    public ObservableList<Task> getSixTasks(User user) throws SQLException
    {
        //ArrayList<Task> sixTasks = (ArrayList<Task>) bllfacade.getSixTasks(user);
        sixTasks = FXCollections.observableArrayList();
        sixTasks.addAll(bllfacade.getSixTasks(user));
        return sixTasks;
    }
    
    /**
     * gets all tasks for the user
     * @param user
     * @param date
     * @return all tasks for the user
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return bllfacade.getTasksForUser(user, date);
    }

    /**
     * gets a list of all tasks
     * @return a list of all tasks
     * @throws SQLException 
     */
    @Override
    public ObservableList<Task> getAllTasks() throws SQLException
    {
        ObservableList<Task> allTasks = FXCollections.observableArrayList();
        allTasks.addAll(bllfacade.getAllTasks());
        return allTasks;
    }

    /**
     * checks if a task already exists
     * @param task
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return bllfacade.taskExist(task);
    }

    /**
     * updates a task
     * @param task
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
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

    /**
     * gets a task
     * @param task
     * @return a task
     * @throws SQLException 
     */
    @Override
    public Task getTask(Task task) throws SQLException
    {
        return bllfacade.getTask(task);
    }

    /**
     * converts the seconds it is given to hours, minutes and seconds
     * @param totalSec
     * @return the seconds it is given to hours, minutes and seconds
     */
    @Override
    public String convertSecToTimeString(int totalSec) {
       return bllfacade.convertSecToTimeString(totalSec);
    }

    /**
     * deletes a task
     * @param task
     * @return boolean
     * @throws SQLException      */
    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
        return bllfacade.deleteTask(task);
    }

    /**
     * clears a task
     * @param task
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return bllfacade.clearTask(task);
    }
    
    /**
     * gets if the timer is running
     * @return boolean
     */
    public boolean getisTimerRunning() {
        
        System.err.println(isTimerRunning + "is timer running");
        return isTimerRunning;
    }

    /**
     * sets if the timer is running
     * @param isTimerRunning 
     */
    @Override
    public void setIsTimerRunning(boolean isTimerRunning) {
        this.isTimerRunning = isTimerRunning;
    }

    /**
     * gets the timer util
     * @return the timer util
     */
    @Override
    public TimerUtil getTimerutil() {
        return timerutil;
    }

    /**
     * sets tie timer util
     * @param timerutil 
     */
    @Override
    public void setTimerutil(TimerUtil timerutil) {
        this.timerutil = timerutil;
    }

    /**
     * gets the executor service and the runnable it has been assigned
     * @return the executor service
     */
    @Override
    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * sets the executor service to run a runnable class
     * @param executorService 
     */
    @Override
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * deletes a interval
     * @param interval
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteInterval(Interval interval) throws SQLException {
        return bllfacade.deleteInterval(interval);
    }

    /**
     * gets all tasks for a project
     * @param project
     * @return all tasks for a project
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
        return bllfacade.getAllTasks4Project(project);
    }
    
    /**
     * gets tasks for a user between two dates
     * @param user
     * @param fromdate
     * @param todate
     * @return tasks for a user between two dates
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException {
        return bllfacade.getTasksForUserbetween2Dates(user, fromdate, todate);
    }

    /**
     * gets the duration from intervals between two dates
     * @param userID
     * @param projectID
     * @param fromdate
     * @param todate
     * @return the duration from intervals between two dates
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException {
        return bllfacade.getDurationFromIntervalsbetween2Dates(userID, projectID, fromdate, todate);
    }
    
    /**
     * gets the viewtype
     * @param viewtype
     * @return the viewtype
     * @throws Exception
     * @throws IOException 
     */
    @Override
    public FXMLLoader getLoader(ViewTypes viewtype) throws Exception, IOException {
       return bllfacade.getLoader(viewtype);
    }

    

    

   
    
    
    
    
    
    
    
}



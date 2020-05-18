/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TimerUtil;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javafx.fxml.FXMLLoader;
/**
 *
 * @author SKRUMM
 */
public interface TaskModelInterface 
{
    
    /**
     * gets a list of tasks from the DB and makes them root treeitems
     * @param user
     * @param fromdate
     * @param todate
     * @return a list of tasks from the DB and makes them root treeitems
     */
    public TreeItem<Task> getModel(User user, LocalDate fromdate, LocalDate todate);
    
    /**
     * creates a task
     * @param task 
     */
    public void createTask(Task task);
    
    /**
     * gets a list of tasks
     * @return a list of tasks
     */
    public ObservableList<Task> getTasks();
    
    /**
     * sets the tasks to the given list
     * @param tasks 
     */
    public void setTasks(ObservableList<Task> tasks);

    /**
     * makes a new interval
     * @param interval
     * @throws SQLException
     * @throws SQLServerException 
     */
    public void newInterval(Interval interval) throws SQLException, SQLServerException;
    
    /**
     * gets the six most recent tasks for the user
     * @param user
     * @return the six most recent tasks for the user
     * @throws SQLException 
     */
    public ObservableList<Task> getSixTasks(User user) throws SQLException;
    
    /**
     * gets all tasks for the user
     * @param user
     * @param date
     * @return all tasks for the user
     * @throws SQLException 
     */
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException;
    
    /**
     * gets a list of all tasks
     * @return a list of all tasks
     * @throws SQLException 
     */
    public ObservableList<Task> getAllTasks() throws SQLException;

    /**
     * checks if a task already exists
     * @param task
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    public boolean taskExist(Task task) throws SQLException;

    /**
     * updates a task
     * @param task
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    public Boolean updateTask(Task task) throws SQLException;

    /**
     * gets a task
     * @param task
     * @return a task
     * @throws SQLException 
     */
    public Task getTask(Task task) throws SQLException;
    
    /**
     * converts the seconds it is given to hours, minutes and seconds
     * @param totalSec
     * @return the seconds it is given to hours, minutes and seconds
     */
    public String convertSecToTimeString(int totalSec);
    
    /**
     * gets if the timer is running already
     * @return true if successful, false otherwise
     */
    public boolean getisTimerRunning();
     
    /**
     * sets if the timer is running
     * @param isTimerRunning 
     */
    public void setIsTimerRunning(boolean isTimerRunning);
    
    /**
     * gets the timer util
     * @return the timer util
     */
    public TimerUtil getTimerutil();

    /**
     * sets the timer util
     * @param timerutil 
     */
    public void setTimerutil(TimerUtil timerutil);
  
    /**
     * deletes a task
     * @param task
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    public boolean deleteTask(Task task) throws SQLException;
    
    /**
     * clears a task
     * @param task
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    public boolean clearTask(Task task) throws SQLException;
    
    /**
     * gets the executor service and the runnable it has been assigned
     * @return the executor service
     */
    public ExecutorService getExecutorService();

    /**
     * sets the executor service to run a runnable class
     * @param executorService 
     */
    public void setExecutorService(ExecutorService executorService);
    
    /**
     * updates a interval
     * @param oldInterval
     * @param newInterval
     * @return true if successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException; 
    
    /**
     * deletes a interval
     * @param interval
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    public boolean deleteInterval(Interval interval) throws SQLException;
    
    /**
     * gets all tasks for a project
     * @param project
     * @return all tasks for a project
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException;

    /**
     * gets tasks for a user between two dates
     * @param user
     * @param fromdate
     * @param todate
     * @return tasks for a user between two dates
     * @throws SQLException 
     */
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException;
    
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
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException;
    
    /**
     * gets the viewtype
     * @param viewtype
     * @return the viewtype
     * @throws Exception
     * @throws IOException 
     */
    public FXMLLoader getLoader(ViewTypes viewtype) throws Exception, IOException; 
    
    
    
   
    
    
   
    
}

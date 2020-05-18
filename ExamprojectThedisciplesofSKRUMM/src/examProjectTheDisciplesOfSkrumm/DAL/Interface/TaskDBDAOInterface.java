/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public interface TaskDBDAOInterface
{

    /**
     * gets a list of all the tasks
     * @return a list of all the tasks
     * @throws SQLException 
     */
    public List<Task> getAllTasks() throws SQLException;

    /**
     * checks if the given task exists
     * @param task
     * @return true if it already exists, false otherwise
     * @throws SQLException 
     */
    public boolean taskExist(Task task) throws SQLException;

    /**
     * creates a task
     * @param task
     * @return the task
     * @throws SQLException 
     */
    public Task createTask(Task task) throws SQLException;

    /**
     * updates the given task
     * @param task
     * @return true if it already exists, false otherwise
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
     * makes a new interval
     * @param interval
     * @throws SQLServerException
     * @throws SQLException 
     */
    public void newInterval(Interval interval) throws SQLServerException, SQLException;

    /**
     * gets a list of the six most recent tasks for the user
     * @param user
     * @return a list of the six most recent tasks for the user
     * @throws SQLException 
     */
    public List<Task> getSixTasks(User user) throws SQLException;
    
    /**
     * gets a list of the tasks for the user
     * @param user
     * @param date
     * @return a list of the tasks for the user
     * @throws SQLException 
     */
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException;
    
    /**
     * deletes a task from the DB
     * @param task
     * @return true if it is deleted, false otherwise
     * @throws SQLException 
     */
    public boolean deleteTask(Task task) throws SQLException;
    
    /**
     * clears a task
     * @param task
     * @return true if it is cleared, false otherwise
     * @throws SQLException 
     */
    public boolean clearTask(Task task) throws SQLException;
    
    /**
     * updates the interval from old interval to new interval
     * @param oldInterval
     * @param newInterval
     * @return true if it updated, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException;
    
    /**
     * deletes the given interval
     * @param interval
     * @return true if it is deleted, false otherwise
     * @throws SQLException 
     */
    public boolean deleteInterval(Interval interval) throws SQLException;
    
    /**
     * gets all the tasks for the projects, as a list
     * @param project
     * @return all the tasks for the projects, as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException;
    
    /**
     * gets a list of all the tasks between two dates for the user
     * @param user
     * @param fromdate
     * @param todate
     * @return a list of all the tasks between two dates for the user
     * @throws SQLException 
     */
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException;
    
    /**
     * gets the duration from the given tasks
     * @param project
     * @return the duration of the given tasks
     * @throws SQLServerException
     * @throws SQLException 
     */
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException;
    
    /**
     * gets the total duration of intervals between two dates
     * @param userID
     * @param projectID
     * @param fromdate
     * @param todate
     * @return the total duration of intervals between two dates
     * @throws SQLServerException
     * @throws SQLException 
     */ 
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException;
}

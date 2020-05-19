/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.chart.XYChart;

/**
 *
 * @author SKRUMM
 */
public interface TaskManagerInterface 
{
    /**
     * Makes a new interval
     * @param interval
     * @throws SQLException 
     */
    public void newInterval(Interval interval) throws SQLException;
    
    /**
     * gets the six latest tasks for the given user
     * @param user
     * @return the six latest tasks for the given user
     * @throws SQLException 
     */
    public List<Task> getSixTasks(User user) throws SQLException;
    
    /**
     * gets a list of tasks for the user
     * @param user
     * @param date
     * @return a list of tasks for the user
     * @throws SQLException 
     */
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException;
    
    /**
     * makes a converted string of time, calculated from the int totalSec
     * @param totalSec
     * @return  a converted string of time, calculated from the int totalSec
     */
    public String convertSecToTimeString(int totalSec);
    
    /**
     * gets a list of all tasks
     * @return a list of all tasks
     * @throws SQLException 
     */
    public List<Task> getAllTasks() throws SQLException;

    /**
     * checks wether or not a task exists
     * @param task
     * @return if a task exists or not
     * @throws SQLException 
     */
    public boolean taskExist(Task task) throws SQLException;

    /**
     * creates a new task
     * @param task
     * @return the new task
     * @throws SQLException 
     */
    public Task createTask(Task task) throws SQLException;

    /**
     * updates a task with the newly given information
     * @param task
     * @return the updated task
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
     * deletes a task
     * @param task
     * @return if the task was deleted
     * @throws SQLException 
     */
    public boolean deleteTask(Task task) throws SQLException;
    
    /**
     * clears task(s)
     * @param task
     * @return clears the task(s)
     * @throws SQLException 
     */
    public boolean clearTask(Task task) throws SQLException;
    
    /**
     * updates the interval from oldInterval to newInterval
     * @param oldInterval
     * @param newInterval
     * @return updated interval
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException;
    
    /**
     * deletes the chosen interval
     * @param interval
     * @return if the chosen interval was deleted
     * @throws SQLException 
     */
    public boolean deleteInterval(Interval interval) throws SQLException;
    
    /**
     * gets a list of all the tasks for the given project
     * @param project
     * @return a list of all the tasks for the given project
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException;

    /**
     * gets tasks for user between two dates
     * @param user
     * @param fromdate
     * @param todate
     * @return tasks for user between two dates
     * @throws SQLException 
     */
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException;
    
    /**
     * gets duration from all tasks in a project
     * @param project
     * @return duration from all tasks in a project
     * @throws SQLServerException
     * @throws SQLException 
     */
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException;
    
    /**
     * gets duration from interval between two dates
     * @param userID
     * @param projectID
     * @param fromdate
     * @param todate
     * @return duration from interval between two dates
     * @throws SQLServerException
     * @throws SQLException 
     */
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException;
    
    /**
     * handles the bar chart data for the project(s)
     * @param userID
     * @param fromdate
     * @param todate
     * @return the bar chart data for the project(s)
     * @throws SQLException 
     */
    public XYChart.Series<String, Number> handleProjectBarChartData(String userID,LocalDate fromdate, LocalDate todate) throws SQLException;
    
    /**
     * handles the bar chart data for the administrator
     * @param projectID
     * @param fromdate
     * @param todate
     * @return the bar chart data for the administrator
     * @throws SQLException 
     */
    public XYChart.Series handleProjectBarChartDataForAdmin(int projectID,LocalDate fromdate, LocalDate todate) throws SQLException;
    
}

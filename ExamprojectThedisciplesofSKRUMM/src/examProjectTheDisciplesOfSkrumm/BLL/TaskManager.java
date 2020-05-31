/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TaskManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Util.DALFacadeFactory;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import examProjectTheDisciplesOfSkrumm.enums.DALFacadeTypes;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.XYChart;

/**
 *
 * @author SKRUMM
 */
public class TaskManager implements TaskManagerInterface
{

    private DALFacadeInterface dal;

    public TaskManager(DALFacadeInterface dal) throws IOException, Exception
    {
        this.dal = dal;
    }

    /**
     * creates a new interval
     *
     * @param interval
     * @throws SQLException
     */
    @Override
    public void newInterval(Interval interval) throws SQLException
    {
        dal.newInterval(interval);
    }

    /**
     * updates an existing interval
     *
     * @param oldInterval
     * @param newInterval
     * @return Interval
     * @throws SQLServerException
     * @throws SQLException
     */
    @Override
    public boolean updateInterval(Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return dal.updateInterval(oldInterval, newInterval);
    }

    /**
     * Gets the six most recent tasks for a user
     *
     * @param user
     * @return List tasks
     * @throws SQLException
     */
    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
        return dal.getSixTasks(user);
    }

    /**
     * gets all tasks for a user
     *
     * @param user
     * @param date
     * @return List tasks
     * @throws SQLException
     */
    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return dal.getTasksForUser(user, date);
    }

    /**
     * converts a tasks total time from seconds to hours and minutes
     *
     * @param totalSec
     * @return string
     */
    @Override
    public String convertSecToTimeString(int totalSec)
    {
        int hour = 0;
        int min = 0;
        int sec = 0;

        while (totalSec >= 3600)
        {
            totalSec = totalSec - 3600;
            hour++;
            //System.out.println("added one to hours...");
        }

        while (totalSec >= 60)
        {
            totalSec = totalSec - 60;
            min++;
            //System.out.println("added one to min...");
        }

        sec = totalSec;
       // System.out.println("added rest of seconds to sec...");

        return (String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
    }

    /**
     * Gets all tasks
     *
     * @return List tasks
     * @throws SQLException
     */
    @Override
    public List<Task> getAllTasks() throws SQLException
    {
        return dal.getAllTasks();
    }

    /**
     * Checks if a task exists
     *
     * @param task
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return dal.taskExist(task);
    }

    /**
     * Creates a new task
     *
     * @param task
     * @return task
     * @throws SQLException
     */
    @Override
    public Task createTask(Task task) throws SQLException
    {
        return dal.createTask(task);
    }

    /**
     * Updates an already existing task
     *
     * @param task
     * @return task
     * @throws SQLException
     */
    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        return dal.updateTask(task);
    }

    /**
     * Gets a specific task
     *
     * @param task
     * @return task
     * @throws SQLException
     */
    @Override
    public Task getTask(Task task) throws SQLException
    {
        return dal.getTask(task);
    }

    /**
     * Deletes a task
     *
     * @param task
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
        return dal.deleteTask(task);
    }

    /**
     * Clears a task for intervals
     *
     * @param task
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return dal.clearTask(task);
    }

    /**
     * Deletes a interval
     *
     * @param interval
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean deleteInterval(Interval interval) throws SQLException
    {
        return dal.deleteInterval(interval);
    }

    /**
     * Gets all tasks for a project
     *
     * @param project
     * @return List tasks
     * @throws SQLServerException
     * @throws SQLException
     */
    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException
    {
        return dal.getAllTasks4Project(project);
    }

    /**
     * Gets all tasks for a user based on the tasks creation date
     *
     * @param user
     * @param fromdate
     * @param todate
     * @return List tasks
     * @throws SQLException
     */
    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException
    {
        return dal.getTasksForUserbetween2Dates(user, fromdate, todate);
    }

    /**
     * Gets the duration for a task
     *
     * @param project
     * @return String formatedDuration
     * @throws SQLServerException
     * @throws SQLException
     */
    @Override
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException
    {
        return dal.getDurationFromTasks(project);
    }

    /**
     * Gets the duration from intervals between two dates
     *
     * @param userID
     * @param projectID
     * @param fromdate
     * @param todate
     * @return int
     * @throws SQLServerException
     * @throws SQLException
     */
    @Override
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException
    {
        return dal.getDurationFromIntervalsbetween2Dates(userID, projectID, fromdate, todate);
    }

    /**
     * handles bar chart for user
     *
     * @param userID
     * @param fromdate
     * @param todate
     * @return data
     * @throws SQLException
     */
    @Override
    public synchronized XYChart.Series<String, Number> handleProjectBarChartData(String userID, LocalDate fromdate, LocalDate todate) throws SQLException
    {
        ArrayList<Project> allProject = new ArrayList<>();

        /**
         * get all the projects*
         */
        allProject.addAll(dal.getAllProjects());

        XYChart.Series<String, Number> data = new XYChart.Series();
        data.setName("Hours spent on projects");
        
        int i = 0;

        for (Project project : allProject)
        { 
            //get the duration for this project
            double duration = (getDurationFromIntervalsbetween2Dates(userID, project.getId(), fromdate, todate));
            
            //dont display the project that have a duration of zero or lower
            if(!(duration <= 0)){
                
                //convert duration to hours
                duration = duration /3600.0;

            //System.out.println(project.getProjectName() + getDurationFromIntervalsbetween2Dates(userID, project.getId(), fromdate, todate));
            /* the group divides the output of getDurationFromIntervalsbetween2Dates by 3600 to go from seconds to hours */
           
            
            data.getData().add(new XYChart.Data(project.getProjectName() + project.getId() +" "+ convertSecToTimeString(((int) Math.round(duration * 3600.0))),  duration));

            // convertSecToTimeString(((int) Math.round(duration * 3600.0)));
 
            }
        }
         
        
  

        return data;
    
    }
    /**
     * handles the bar chart for admin
     *
     * @param projectID
     * @param fromdate
     * @param todate
     * @return data
     * @throws SQLException
     */
    @Override
    public synchronized XYChart.Series handleProjectBarChartDataForAdmin(int projectID, LocalDate fromdate, LocalDate todate) throws SQLException
    {
        ArrayList<User> allUsers = new ArrayList<>();

        /**
         * get all the projects*
         */
        allUsers.addAll(dal.getAllUsers());

        XYChart.Series data = new XYChart.Series();
        data.setName("Hours this user spent on the project");

        for (User user : allUsers)
        {
             double duration = (getDurationFromIntervalsbetween2Dates(user.getEmail(), projectID, fromdate, todate));
             
             if(!(duration <= 0)){
                 
                 /* the group divides the output of getDurationFromIntervalsbetween2Dates by 3600 to go from seconds to hours */
                duration = duration /3600.0;

            //System.out.println(user.getEmail() + getDurationFromIntervalsbetween2Dates(user.getEmail(), projectID, fromdate, todate));
            
            data.getData().add(new XYChart.Data(user.getFirstName() +" "+ user.getLastName() +" "+ convertSecToTimeString(((int) Math.round(duration * 3600.0))),duration ));  
            
             }
        }

        
        
        
        return data;
    }

    public static void main(String[] args) throws Exception
    {
       TaskManager tm = new TaskManager(DALFacadeFactory.CreateDALFacade(DALFacadeTypes.PRODUCTION));
       
       
        System.out.println(tm.handleProjectBarChartDataForAdmin(1, LocalDate.of(2020, Month.MAY, 1), LocalDate.of(2020, Month.MAY, 15)).getData());

    }

}

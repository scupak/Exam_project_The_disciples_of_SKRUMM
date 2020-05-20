/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.LogDBDAOInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.TaskDBDAOInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author SKRUMM
 */
public class TaskDBDAO implements TaskDBDAOInterface
{

    private final ConnectionPool conPool;
    private UserDBDAO userDBDAO;
    private ProjectDBDAO projectDBDAO;
    private LogDBDAOInterface  logDBDAO;

    public TaskDBDAO() throws IOException, Exception
    {
        this.conPool = ConnectionPool.getInstance();
        userDBDAO = new UserDBDAO();
        projectDBDAO = new ProjectDBDAO();
        logDBDAO = new LogDBDAO();
    }

    /**
     * gets a list of all the tasks
     * @return a list of all the tasks
     * @throws SQLException 
     */
    @Override
    public List<Task> getAllTasks() throws SQLException
    {
        ArrayList<Task> returntasks = new ArrayList<>();
        Connection con = conPool.checkOut();

        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task]");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id, id), id));
                int duration = rs.getInt("duration");
                String clientName = project.getClientName();
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalDateTime startTime = rs.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime stopTime = rs.getTimestamp("stopTime").toLocalDateTime();

                ArrayList<Interval> intervals = new ArrayList<Interval>();

                String userEmail = rs.getString("userEmail");

                User user = userDBDAO.getUser(new User(userEmail, clientName, clientName, title, false));

                intervals.addAll(getIntervals(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals)));

                returntasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals));
            }
            

            return returntasks;

        }
        finally
        {
            conPool.checkIn(con);
        }
    }
    
    /**
     * checks if the given task exists
     * @param task
     * @return true if it already exists, false otherwise
     * @throws SQLException 
     */
    public boolean taskExist(Task task) throws SQLException
    {
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task] WHERE id = ?");
            ps.setInt(1, task.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                return true;
            }

            return false;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * creates a task
     * @param task
     * @return the task
     * @throws SQLException 
     */
    @Override
    public Task createTask(Task task) throws SQLException
    {
        

        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO [task]"
                    + "(title, projectID, lastUsed, CreationDate, startTime, stopTime, duration, userEmail)"
                    + "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, task.getTitle());
            ps.setInt(2, task.getProject().getId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(task.getLastUsed()));
            ps.setDate(4, java.sql.Date.valueOf(task.getCreationDate()));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(task.getStartTime()));
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(task.getStopTime()));
            ps.setInt(7, task.getDuration());
            ps.setString(8, task.getUserEmail());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next())
            {
                task.setId((int) rs.getLong(1));
                //logDBDAO.createLog(task.getUserEmail(), "create task", task.getProjectName(), task.getTitle());
                logDBDAO.createLog(task.getUserEmail() +"-"+ "create task" +"-"+ task.getProjectName() +"-"+ task.getTitle()+ "-" +  "SUCCESS");
                
            } else
            {
               // logDBDAO.createLog(task.getUserEmail(), "create task not successful", task.getProjectName(), task.getTitle());
                 logDBDAO.createLog(task.getUserEmail() +"-"+ "create task not successful" +"-"+ task.getProjectName() +"-"+ task.getTitle()+ "-" +  "ERROR");
                return null;
            }

            return task;
        }
        
        finally
        {
            conPool.checkIn(con);
        }

    }
    
    /**
     * deletes a task
     * @param task
     * @return true if the task was deleted, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
        Connection con = conPool.checkOut();
        try
        {
            if(clearTask(task))
            {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [task] WHERE id = ?");
            ps.setInt(1, task.getId());
            
            int updatedRows = ps.executeUpdate();
            
            if(updatedRows > 0){
               logDBDAO.createLog(task.getUserEmail() +"-"+ "delete task successful" +"-"+ task.getProjectName() +"-"+ task.getTitle()+ "-" +  "SUCCESS");
            }
            
            return updatedRows > 0;
            }
            
        }
        finally
        {
            conPool.checkIn(con);
        }
        
        logDBDAO.createLog(task.getUserEmail() +"-"+ "delete task not successful" +"-"+ task.getProjectName() +"-"+ task.getTitle()+ "-" +  "ERROR");
        return false;
    }
    
    /**
     * clears a task
     * @param task
     * @return true if it was cleared, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [interval] WHERE taskId = ?");
            ps.setInt(1, task.getId());
            ps.executeUpdate();
            
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM [interval] WHERE taskId = ?");
            ps2.setInt(1, task.getId());
            ResultSet rs = ps2.executeQuery();
            
            while(rs.next())
            {
                return false;
            }
            
            return true;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * updates the given task
     * @param task
     * @return true if it already exists, false otherwise
     * @throws SQLException 
     */
    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        if (!taskExist(task))
        {
            logDBDAO.createLog(task.getUserEmail() +"-"+ "update task failed cause the task did not exist" +"-"+ task.getProjectName() +"-"+ task.getTitle() + "-" +  "ERROR");
            return null;
        }

        System.err.println(task);
        Connection con = conPool.checkOut();

        try
        {
            PreparedStatement ps = con.prepareStatement("UPDATE [task] "
                    + "SET title = ?, projectID = ?, lastUsed = ?, creationDate = ?,"
                    + " startTime = ?, stopTime = ?, duration = ?, userEmail = ?"
                    + " WHERE id = ?");
            ps.setString(1, task.getTitle());
            ps.setInt(2, task.getProject().getId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(task.getLastUsed()));
            ps.setDate(4, java.sql.Date.valueOf(task.getCreationDate()));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(task.getStartTime()));
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(task.getStopTime()));
            ps.setInt(7, task.getDuration());
            ps.setString(8, task.getUserEmail());
            ps.setInt(9, task.getId());

            int updatedRows = ps.executeUpdate();
            
            if( updatedRows > 0){
                
                logDBDAO.createLog(task.getUserEmail() +"-"+ "update task successful" +"-"+ task.getProjectName() +"-"+ task.getTitle() + "-" + "SUCCESS");
            
            
            }

            return updatedRows > 0;

        }
        finally
        {
            conPool.checkIn(con);
        }

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
        if (!taskExist(task))
        {
            return null;
        }

        Task returnTask = null;
        ArrayList<Interval> intervals = new ArrayList<>();
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task] WHERE id = ?");

            ps.setInt(1, task.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("projectID"), title, new Client(0, title, 0, 0), 0));
                int duration = rs.getInt("duration");
                String projectName = project.getProjectName();
                String clientName = project.getClientName();
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalDateTime startTime = rs.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime stopTime = rs.getTimestamp("stopTime").toLocalDateTime();
                String userEmail = rs.getString("userEmail");
                intervals = getIntervals(task);
                User user = userDBDAO.getUser(new User(userEmail, clientName, clientName, title, true));

                returnTask = new Task(task.getId(), title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals);

            }

        }
        finally
        {
            conPool.checkIn(con);
        }

        return returnTask;
    }

    /**
     * gets a list of the six most recent tasks for the user
     * @param user
     * @return a list of the six most recent tasks for the user
     * @throws SQLException 
     */
    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
        ArrayList<Task> tasks = new ArrayList<>();

        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT TOP 6 * FROM [task] WHERE userEmail = ? ORDER BY lastUsed DESC");

            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id, id), id));
                int duration = rs.getInt("duration");
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalDateTime startTime = rs.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime stopTime = rs.getTimestamp("stopTime").toLocalDateTime();
                String userEmail = rs.getString("userEmail");
                User user1 = userDBDAO.getUser(new User(userEmail, "22", "22", title, false));
                
                ArrayList<Interval> intervals = new ArrayList<>();
                
                intervals.addAll(getIntervals(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals)));
                
                tasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user1, intervals));

            }
            if (tasks.isEmpty())
            {
                System.out.println("no tasks for this user");
            }
            
            

            return tasks;

        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * gets a list of the tasks for the user
     * @param user
     * @param date
     * @return a list of the tasks for the user
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        ArrayList<Task> tasks = new ArrayList<>();
        String sqlString;
        PreparedStatement ps;

        Connection con = conPool.checkOut();
        try
        {

            if (date == null)
            {
                sqlString = "SELECT * FROM [task] WHERE userEmail = ?";
                ps = con.prepareStatement(sqlString);
                ps.setString(1, user.getEmail());

            } else
            {
                sqlString = "SELECT * FROM [task] WHERE userEmail = ? AND "
                        + "cast(CONVERT(varchar(8), lastUsed, 112) AS date) = ?";
                ps = con.prepareStatement(sqlString);
                ps.setString(1, user.getEmail());
                ps.setDate(2, java.sql.Date.valueOf(date));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id,
                        id), id));
                int duration = rs.getInt("duration");
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalDateTime startTime = rs.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime stopTime = rs.getTimestamp("stopTime").toLocalDateTime();
                ArrayList<Interval> intervals = getIntervals(new Task(id, title, project, duration, lastUsed, 
                        creationDate, startTime, stopTime, user));
                String userEmail = rs.getString("userEmail");
                User user1 = userDBDAO.getUser(new User(userEmail, "22", "22", title, false));
                tasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, 
                        stopTime, user1, intervals));

            }

            return tasks;
        }
        finally
        {
            conPool.checkIn(con);
        }

    }
    /**
     * makes a new interval
     * @param interval
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public void newInterval(Interval interval) throws SQLServerException, SQLException
    {
        //set last used in the task
        //TODO implement transactions.
        Connection con = conPool.checkOut();
        try
        {
            //Tell SQL Server not to auto-commit all SQL statements - we have to do this manually
            con.setAutoCommit(false); 
            
            PreparedStatement ps = con.prepareStatement("INSERT INTO [interval] VALUES (?,?,?,?,?,?)");

            ps.setDate(1, java.sql.Date.valueOf(interval.getCreationDate()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(interval.getStartTime()));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(interval.getStopTime()));
            ps.setInt(4, interval.getIntervalTime());
            ps.setInt(5, interval.getTask().getId());
            ps.setInt(6, interval.getIsPaid());
            
            ps.executeUpdate();
            
            
            String updateLastUsed = "UPDATE [task] SET lastUsed = ?, duration = ? WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateLastUsed);

            ps2.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps2.setInt(2, interval.getTask().getDuration());
            ps2.setInt(3, interval.getTask().getId());

            ps2.executeUpdate();
            
            con.commit();
            logDBDAO.createLog(interval.getTask().getUserEmail() +"-"+ "create new interval was successful" +"-"+ interval.getTask().getProjectName() +"-"+ interval.getTask().getTitle() + "-" + "SUCCESS");
            
        }
        catch(SQLServerException e)
        {
            if (con != null) {
                con.rollback(); //an exception happened in executing the statements
                System.out.println("Rolling back changes...");                
            }
             logDBDAO.createLog(interval.getTask().getUserEmail() +"-"+ "create new interval was not successful" +"-"+ interval.getTask().getProjectName() +"-"+ interval.getTask().getTitle() + "-" +  "ERROR");
            throw new SQLException();
            
        }
        catch(SQLException e)
        {
            if (con != null) {
                con.rollback(); //an exception happened in executing the statements
                System.out.println("Rolling back changes...");                
            }
             logDBDAO.createLog(interval.getTask().getUserEmail() +"-"+ "create new interval was not successful" +"-"+ interval.getTask().getProjectName() +"-"+ interval.getTask().getTitle() + "-" +  "ERROR");
            throw  new SQLException();
            
        }
        finally
        {
            if (con != null) {
                con.setAutoCommit(true); //set default again 
               
            }
            conPool.checkIn(con);
        }
    }
    /**
     * updates the interval from old interval to new interval
     * @param oldInterval
     * @param newInterval
     * @return true if it updated, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        if(!intervalExist(oldInterval))
        {
            logDBDAO.createLog(oldInterval.getTask().getUserEmail() +"-"+ "Interval does not exist!" +"-"+ oldInterval.getTask().getProjectName() +"-"+ oldInterval.getTask().getTitle() + "-" + "ERROR");
            return false;
        }
        
        
        Connection con = conPool.checkOut();
        try
        {
            String sql = "UPDATE [interval] SET creationDate = ?, startTime = ?, stopTime = ?, intervalTime = ?, isPaid = ? WHERE intervalId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setDate(1, java.sql.Date.valueOf(newInterval.getCreationDate()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(newInterval.getStartTime()));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(newInterval.getStopTime()));
            ps.setInt(4, newInterval.getIntervalTime());
            ps.setInt(5, newInterval.getIsPaid());
            ps.setInt(6, newInterval.getId());
            
            ps.executeUpdate();
            
            
            System.out.println(oldInterval.getIntervalTime());
            System.out.println(newInterval.getIntervalTime());
            System.out.println(newInterval.getTask().getDuration());
            
            String updateLastUsed = "UPDATE [task] SET lastUsed = ?, duration = ? WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateLastUsed);

            ps2.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps2.setInt(2, newInterval.getTask().getDuration() - oldInterval.getIntervalTime() + newInterval.getIntervalTime());
            ps2.setInt(3, newInterval.getTask().getId());

            int updatedRows = ps2.executeUpdate();

            if(updatedRows > 0){
                logDBDAO.createLog(oldInterval.getTask().getUserEmail() +"-"+ "Task has been updated successfully!" +"-"+ oldInterval.getTask().getProjectName() +"-"+ oldInterval.getTask().getTitle() + "-" + "SUCCESS");
               return true; 
            }
            
        }
        finally
        {
            conPool.checkIn(con);
        }
        logDBDAO.createLog(oldInterval.getTask().getUserEmail() +"-"+ "Cannot update task!" +"-"+ oldInterval.getTask().getProjectName() +"-"+ oldInterval.getTask().getTitle() + "-" + "ERROR");
        return false;
    }

    /**
     * gets a list of all the intervals for the task
     * @param task
     * @return a list of all the intervals for the task
     * @throws SQLException 
     */
    private ArrayList<Interval> getIntervals(Task task) throws SQLException
    {
        Connection con = conPool.checkOut();
        try
        {
            ArrayList<Interval> intervals = new ArrayList<>();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM [interval] WHERE interval.taskId = ?");

            ps.setInt(1, task.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Date date = rs.getDate("creationDate");
                java.sql.Timestamp intervalStartTime = rs.getTimestamp("startTime");
                java.sql.Timestamp intervalStopTime = rs.getTimestamp("stopTime");
                int intervalTime = rs.getInt("intervalTime");
                int isPaid = rs.getInt("isPaid");
                int id = rs.getInt("intervalId");

                Interval interval = new Interval(id, intervalStartTime.toLocalDateTime(), intervalStopTime.toLocalDateTime(), date.toLocalDate(), intervalTime,
                        task, isPaid);

                intervals.add(interval);
            }
            return intervals;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }
    
    /**
     * deletes the given interval
     * @param interval
     * @return true if it is deleted, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteInterval(Interval interval) throws SQLException 
    {
        if(!intervalExist(interval))
        {
            return false;
        }
        
        Connection con = conPool.checkOut();
        try
        { 
            PreparedStatement ps = con.prepareStatement("DELETE FROM [interval] WHERE intervalId = ?");
            ps.setInt(1, interval.getId());
            
            int updatedRows = ps.executeUpdate();
            
            String updateLastUsed = "UPDATE [task] SET lastUsed = ?, duration = ? WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateLastUsed);

            ps2.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps2.setInt(2, interval.getTask().getDuration() - interval.getIntervalTime());
            ps2.setInt(3, interval.getTask().getId());

            ps2.executeUpdate();
            
            if(updatedRows > 0){
                logDBDAO.createLog(interval.getTask().getUserEmail() +"-"+ "Interval Deleted successfully!" +"-"+ interval.getTask().getProjectName() +"-"+ interval.getTask().getTitle() + "-" + "SUCCESS");
                return true;
            }
            
        }
        finally
        {
            conPool.checkIn(con);
        }
        
        logDBDAO.createLog(interval.getTask().getUserEmail() +"-"+ "Cannot delete interval!" +"-"+ interval.getTask().getProjectName() +"-"+ interval.getTask().getTitle() + "-" + "ERROR");
        return false;
    }
        
    /**
     * checks if the given interval exists
     * @param interval
     * @return true if the interval exists, false otherwise
     * @throws SQLException 
     */
    public boolean intervalExist(Interval interval) throws SQLException 
    {
        Connection con = conPool.checkOut();
        try
        { 
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [interval] WHERE intervalId = ?");
            ps.setInt(1, interval.getId());
            
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                return true;
            }

            return false;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }
    
    /**
     * gets a list of all the tasks for a user between two dates
     * @param user
     * @param fromdate
     * @param todate
     * @return a list of all the tasks for a user between two dates
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException
    {
        ArrayList<Task> tasks = new ArrayList<>();
        String sqlString;
        PreparedStatement ps;

        Connection con = conPool.checkOut();
        try
        {

            
                sqlString = "SELECT DISTINCT  interval.taskId, task.id , task.creationDate,task.duration, task.lastUsed , task.projectID , task.startTime, task.stopTime, task.title, task.userEmail "
                        + "FROM task "
                        + "LEFT OUTER JOIN interval on interval.taskId = task.id "
                        + "WHERE  task.userEmail = ? AND interval.creationDate >= ? AND interval.creationDate <= ? OR task.userEmail = ? AND task.creationDate >= ? AND task.creationDate <= ? "
                        + "ORDER BY task.lastUsed DESC ";
                
                ps = con.prepareStatement(sqlString);
                
                ps.setString(1, user.getEmail());
                ps.setDate(2, java.sql.Date.valueOf(fromdate));
                ps.setDate(3, java.sql.Date.valueOf(todate));
                ps.setString(4, user.getEmail());
                ps.setDate(5, java.sql.Date.valueOf(fromdate));
                ps.setDate(6, java.sql.Date.valueOf(todate));
            

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {                                                                  
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id,
                        id), id));
                int duration = rs.getInt("duration");
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalDateTime startTime = rs.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime stopTime = rs.getTimestamp("stopTime").toLocalDateTime();
                //make the from date to date dal method work for intervals
                ArrayList<Interval> intervals = getIntervalsbetween2Dates(new Task(id, title, project, duration, lastUsed, 
                        creationDate, startTime, stopTime, user),fromdate,todate);
                
                String userEmail = rs.getString("userEmail");
                
                User user1 = userDBDAO.getUser(new User(userEmail, "22", "22", title, false));
                
                tasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, 
                        stopTime, user1, intervals));

            }

            return tasks;
        }
        finally
        {
            conPool.checkIn(con);
        }

    }
        
        
        //make the from date to date dal method work for intervals
    /**
     * gets all intervals between two dates
     * @param task
     * @param fromdate
     * @param todate
     * @return a list of all the intervals between two dates
     * @throws SQLException 
     */
    private ArrayList<Interval> getIntervalsbetween2Dates(Task task, LocalDate fromdate, LocalDate todate) throws SQLException
    {
        Connection con = conPool.checkOut();
        try
        {
            ArrayList<Interval> intervals = new ArrayList<>();

            PreparedStatement ps = con.prepareStatement("SELECT interval.creationDate,interval.intervalId,interval.intervalTime,interval.isPaid, interval.startTime,interval.stopTime,interval.taskId,  task.id , task.userEmail ,task.title "
                                                      + "FROM interval "
                                                      + "INNER JOIN task on interval.taskId = task.id "
                                                      + "WHERE  task.id = ? AND interval.creationDate >= ? AND interval.creationDate <= ? ");

            ps.setInt(1, task.getId());
            ps.setDate(2,  java.sql.Date.valueOf(fromdate));
            ps.setDate(3, java.sql.Date.valueOf(todate));

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Date date = rs.getDate("creationDate");
                java.sql.Timestamp intervalStartTime = rs.getTimestamp("startTime");
                java.sql.Timestamp intervalStopTime = rs.getTimestamp("stopTime");
                int intervalTime = rs.getInt("intervalTime");
                int isPaid = rs.getInt("isPaid");
                int id = rs.getInt("intervalId");

                Interval interval = new Interval(id, intervalStartTime.toLocalDateTime(), intervalStopTime.toLocalDateTime(), date.toLocalDate(), intervalTime,
                        task, isPaid);

                intervals.add(interval);
            }

            return intervals;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }
        
    public static void main(String[] args) throws IOException, SQLException, Exception
    {
        TaskDBDAO taskDBDAO = new TaskDBDAO();

          User user = new User("standard@user.now", "kok", "kok", "kok", true);
          ArrayList<Interval> intervals = new ArrayList<>();
          Client client = new Client(1, "why", 0, 0);
          Project project = new Project(2, "reeeeeeee", client, 0);
          Task task = new Task(2, "rjo", project, 50, LocalDateTime.now(), LocalDate.now(), LocalDateTime.now(), LocalDateTime.now(), user, intervals);
    }

    /**
     * gets a list of all the tasks for a project
     * @param project
     * @return a list of all the tasks for a project
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
                ArrayList<Task> returntasks = new ArrayList<>();

        Connection con = conPool.checkOut();        
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task] WHERE ProjectID = ?");
            ps.setInt(1, project.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project projectoid = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id, id), id));
                int duration = rs.getInt("duration");
                String clientName = project.getClientName();
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalDateTime startTime = rs.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime stopTime = rs.getTimestamp("stopTime").toLocalDateTime();

                ArrayList<Interval> intervals = new ArrayList<Interval>();

                String userEmail = rs.getString("userEmail");

                User user = userDBDAO.getUser(new User(userEmail, clientName, clientName, title, false));

                intervals.addAll(getIntervals(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals)));

                returntasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals));
            }

            return returntasks;

        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * gets the total duration from tasks
     * @param project
     * @return the total duration from tasks
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException {
        int time = 0;
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT SUM(task.duration) AS 'sumDuration' FROM [task] WHERE ProjectID = ?");
            ps.setInt(1, project.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                time = rs.getInt("sumDuration") + time;
            }

            return time;

        }
        finally
        {
            conPool.checkIn(con);
        }
    }
    /**
     * gets duration from intervals between two dates
     * @param userID
     * @param projectID
     * @param fromdate
     * @param todate
     * @return a time integer in seconds for the amount of time on a project 
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
     public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException {
        int time = 0;
        Connection con = conPool.checkOut();
         try 
        {
            PreparedStatement ps = con.prepareStatement( "SELECT  SUM(interval.intervalTime) AS 'sumDuration' "
                                                       + "FROM interval "
                                                       + "INNER JOIN task on interval.taskId = task.id "
                                                       + "INNER JOIN project on project.id = task.projectID "
                                                       + "WHERE task.userEmail = ? AND projectID = ? AND interval.creationDate >= ? AND interval.creationDate <= ? ");
            ps.setString(1, userID);
            ps.setInt(2, projectID);
            ps.setDate(3,java.sql.Date.valueOf(fromdate));
            ps.setDate(4,java.sql.Date.valueOf(todate));
            
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                time = rs.getInt("sumDuration") + time;
            }

            return time;

        }
         finally
        {
            conPool.checkIn(con);
        }
    }
}

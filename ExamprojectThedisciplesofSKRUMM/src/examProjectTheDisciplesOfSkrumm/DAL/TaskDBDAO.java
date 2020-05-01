/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
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
import java.time.LocalTime;

/**
 *
 * @author lumby
 */
public class TaskDBDAO
{

    private final DatabaseConnector dbCon;

    public TaskDBDAO() throws IOException
    {
        dbCon = new DatabaseConnector();
    }

    public List<Task> getAllTasks() throws SQLException
    {
        ArrayList<Task> tasks = new ArrayList<>();

        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task]");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = new Project(1, "need project table", new Client(1, "need client table", 5, 1), 755);
                int duration = rs.getInt("duration");
                String projectName = project.getProjectName();
                String clientName = project.getClientName();
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                LocalTime stopTime = rs.getTime("stopTime").toLocalTime();
                
                


                tasks.add(new Task(id, title, project, duration, lastUsed, LocalDate.MIN, LocalTime.MIN, LocalTime.MIN, tasks));

            }

            return tasks;

        }
    }

    public boolean taskExist(Task task) throws SQLException
    {
        try (Connection con = dbCon.getConnection())
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
    }

    public Task createTask(Task task) throws SQLException
    {
        if (taskExist(task))
        {
            return null;
        }

        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO [task]"
                    + "(title, projectID, lastUsed, CreationDate, startTime, stopTime, duration)"
                    + "VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, task.getTitle());
            ps.setInt(2, task.getProject().getId());
            ps.setDate(3, java.sql.Date.valueOf(task.getLastUsed().toLocalDate()));
            ps.setDate(4, java.sql.Date.valueOf(task.getCreationDate()));
            ps.setTime(5, java.sql.Time.valueOf(task.getStartTime()));
            ps.setTime(6, java.sql.Time.valueOf(task.getStopTime()));
            ps.setInt(7, task.getDuration());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next())
            {
                task.setId((int) rs.getLong(1));
            } else
            {
                return null;
            }

            return task;
        }

    }

    public static void main(String[] args) throws IOException, SQLException
    {
        TaskDBDAO taskDBDAO = new TaskDBDAO();
        Client client = new Client(0, "why", 0, 0);
        Project project = new Project(0, "reeeeeeee", client, 0);
        ArrayList<Task> intervals = new ArrayList<>();
        Task task = new Task(1, "pølse", project, 50, LocalDateTime.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), intervals);
        taskDBDAO.createTask(task);
        System.out.println(taskDBDAO.createTask(task));
    }
}

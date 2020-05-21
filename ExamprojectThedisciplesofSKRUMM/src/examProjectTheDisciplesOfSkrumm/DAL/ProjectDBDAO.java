/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.LogDBDAOInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.ProjectDBDAOInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author SKRUMM
 */
public class ProjectDBDAO implements ProjectDBDAOInterface
{
    private LogDBDAOInterface  logDBDAO;
    private final ConnectionPool conPool;
    private ClientDBDAO clientdb;

    public ProjectDBDAO() throws IOException, Exception {
        this.conPool = ConnectionPool.getInstance();
        clientdb = new ClientDBDAO();
        logDBDAO = new LogDBDAO();
    }
    
    /**
     * Gets a list of all projects
     * @return a list of all projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException
    {
        ArrayList<Project> projects = new ArrayList<>();
        
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT e.id, e.projectName, e.clientID, e.projectrate, e.creationDate, e.isPaid, d.id AS Cid, d.name, d.rate, d.isPaid AS CisPaid \n" +
                    "FROM project e \n" +
                     "JOIN client d ON e.clientID = d.id");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                int isPaid = rs.getInt("isPaid");
                String projectName = rs.getString("projectName").trim();
                Client client = new Client(rs.getInt("Cid"), rs.getString("name"), rs.getInt("rate"), rs.getInt("CisPaid"));
                int projectrate = rs.getInt("projectrate");
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                Project p = new Project(id, projectName, client, projectrate, isPaid);
                p.setCreationDate(creationDate);
                projects.add(p);
                
                
            }
        }
        finally
        {
            conPool.checkIn(con);
        }
        return projects;
    }

    /**
     * checks if a project already exists
     * @param project
     * @return true if the project already exists, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean projectExist(Project project) throws SQLException 
    {
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM project WHERE id = ?");
            ps.setInt(1, project.getId());
            
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
     * creates a new project
     * @param project
     * @return the new project that has been made
     * @throws SQLException 
     */    
    @Override
    public Project createProject(Project project) throws SQLException 
    {
        if(project.getCreationDate() != LocalDate.now())
        {
        project.setCreationDate(LocalDate.now());
        }
        
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO [project]"
                    + "(projectName, clientID, projectrate, creationDate, isPaid)"
                    + "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, project.getProjectName());
            ps.setInt(2, project.getClient().getId());
            ps.setInt(3, project.getProjectRate());
            ps.setDate(4, java.sql.Date.valueOf(project.getCreationDate()));
            ps.setInt(5, project.getIsPaid());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next())
            {
                project.setId((int) rs.getLong(1));
            }else
            {
                logDBDAO.createLog("create project unsuccessful" + "-" + project.getProjectName() + "-" +  "ERROR");
                return null;
            }
            System.out.println(project.getClientName() + " Assigned to " + project.getProjectName());
            JOptionPane.showMessageDialog(null, project.getProjectName() + " has been created!");
            logDBDAO.createLog("create project successful" + "-" + project.getProjectName() + "-" +  "SUCCESS");
            return project; 
        }
        finally
        {
            conPool.checkIn(con);
        }
        
    }

    /**
     * gets the given project
     * @param project
     * @return the given project as a project object
     * @throws SQLException 
     */
    @Override
    public Project getProject(Project project) throws SQLException 
    {
            if (!projectExist(project))
            {
                return null;
            }
            
        
        Client steve = new Client(1, "Steve", 0, 0);
        Project returnproject = null;
        Connection con = conPool.checkOut();
        
        try    
        {
            PreparedStatement ps = con.prepareStatement("SELECT e.id, e.projectName, e.clientID, e.projectrate, e.creationDate, e.isPaid, d.id AS Cid, d.name, d.rate, d.isPaid AS CisPaid\n" +
        "FROM project e \n" +
        "JOIN client d ON e.clientID = d.id\n" +
        "WHERE e.id = ?"); 
            ps.setInt(1, project.getId());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                int isPaid = rs.getInt("isPaid");
                String projectName = rs.getString("projectName").trim();
                Client client = new Client(rs.getInt("Cid"), rs.getString("name"), rs.getInt("rate"), rs.getInt("CisPaid"));
                int projectrate = rs.getInt("projectrate");
                returnproject = new Project(id, projectName, client, projectrate, isPaid);
                returnproject.setCreationDate(rs.getDate("creationDate").toLocalDate());
                
            }
            return returnproject;
        }
        finally
        {
            conPool.checkIn(con);
        }
        
    }
    
   /**
     * gets a list of all the projects for a given client
     * @param project
     * @return a list of all the projects for a given client
     * @throws SQLException 
     */
    @Override
    public List<Project> getProjectsForClient(Client client) throws SQLException
    {
        ArrayList<Project> projects = new ArrayList<>();
        
        if(!clientdb.clientExist(client)) return null;
        
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [project] where clientId = ?");
            ps.setInt(1, client.getId());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int id = rs.getInt("id");
                int isPaid = rs.getInt("isPaid");
                String projectName = rs.getString("projectName").trim();
                int projectrate = rs.getInt("projectrate");
                Project p = new Project(id, projectName, client, projectrate, isPaid);
                p.setCreationDate(rs.getDate("creationDate").toLocalDate());
                projects.add(p);
            }
        }
        finally
        {
            conPool.checkIn(con);
        }
        
        
        
        return projects;
    }
    
    public static void main(String[] args) throws IOException, SQLException, Exception
    {   
       
        ClientDBDAO clientdb = new ClientDBDAO();
        ProjectDBDAO projectDb = new ProjectDBDAO();
        ArrayList<Project> projects = new ArrayList<>();
        Client client = new Client(6, "Dick", 0, 0);
        projects.addAll(projectDb.getProjectsForClient(client));
        
        for (Project project : projects)
        {
            System.out.println(project);   
        }
    }

    /**
     * deletes the given project
     * @param project
     * @return true if the project was deleted, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteProject(Project project) throws SQLException 
    {
        Connection con = conPool.checkOut();

        try
        {
            if(clearProject(project))
            {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [project] WHERE id = ?");
            ps.setInt(1, project.getId());
            
            int updatedRows = ps.executeUpdate();
            
            if(updatedRows > 0){
               logDBDAO.createLog("delete project successful" + "-" + project.getProjectName() + "-" +  "SUCCESS");
               return true;  
            }

            }
            
        }
        finally
        {
            conPool.checkIn(con);
        }
        logDBDAO.createLog("delete project unsuccessful" + "-" + project.getProjectName() + "-" +  "ERROR");
        return false;
        
    }

    /**
     * clears the project 
     * this removes all the information in the project, keeping the project still listed
     * @param project
     * @return true if the project was cleared, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean clearProject(Project project) throws SQLException 
    {
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [task] WHERE projectID = ?");
            ps.setInt(1, project.getId());
            ps.executeUpdate();
            
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM [task] WHERE projectID = ?");
            ps2.setInt(1, project.getId());
            ResultSet rs = ps2.executeQuery();
            
            while(rs.next())
            {
                logDBDAO.createLog("clear project unsuccessful" + "-" + project.getProjectName() + "-" +  "ERROR");
                return false;
            }
            logDBDAO.createLog("clear project successful" + "-" + project.getProjectName() + "-" +  "SUCCESS");
            return true;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * updates the information on the given project with the given information
     * @param project
     * @return true if the project was updated, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException {
        if (!projectExist(project))
        {
            return false;
        }

        System.err.println(project);

        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("UPDATE [project] "
                    + "SET projectName = ?,"
                    + " projectrate = ?, clientID = ?, isPaid = ?"
                    + " WHERE id = ?");
            ps.setString(1, project.getProjectName());
            ps.setInt(2, project.getProjectRate());
            ps.setInt(3, project.getClient().getId());
            ps.setInt(4, project.getIsPaid());
            ps.setInt(5, project.getId());
            

            int updatedRows = ps.executeUpdate();

            if(updatedRows > 0){
                logDBDAO.createLog("update project successful" + "-" + project.getProjectName() + "-" +  "SUCCESS");
                return true;
            }
            

        }
        finally
        {
            conPool.checkIn(con);
        }
        logDBDAO.createLog("update project unsuccessful" + "-" + project.getProjectName() + "-" +  "ERROR");
        return false;
    }

    
    
}

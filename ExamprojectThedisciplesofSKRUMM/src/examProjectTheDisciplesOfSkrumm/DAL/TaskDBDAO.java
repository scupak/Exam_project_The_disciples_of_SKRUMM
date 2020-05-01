/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
    
    
    public List<Task> getAllTasks() throws SQLServerException, SQLException
    {
        ArrayList<Task> taks = new ArrayList<>();
        
        try(Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task]");
            
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.LogDBDAOInterface;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public class LogDBDAO implements LogDBDAOInterface{
    private final ConnectionPool conPool;

    public LogDBDAO() throws IOException, Exception
    {
        this.conPool = ConnectionPool.getInstance();
    }
    /**
     * 
     * @return list of the top 100 logs.
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
     public List<String> getAllLogs() throws SQLServerException, SQLException
    {
        ArrayList<String> logs = new ArrayList<>();
        Connection con = conPool.checkOut();

        try
        {
            
            PreparedStatement ps = con.prepareStatement("SELECT TOP (100) * FROM [Log] ORDER BY [Log].timesStamp DESC ");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                
                    //logs.add(rs.getTimestamp("timesStamp").toLocalDateTime().toString().trim() +"-"+ rs.getString("userName").trim() +"-"+ rs.getString("action").trim() +"-"+ rs.getString("projectName").trim() +"-"+ rs.getString("taskName").trim()+"-"+ rs.getInt("id"));
                      logs.add(rs.getTimestamp("timesStamp").toLocalDateTime().toString().trim() +"-"+ rs.getString("description").trim());
            }
            
            return logs;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }
     
   
     /**
      * creates a log entry
      * @param description
      * @return the new log entry
      * @throws SQLException 
      */
    @Override
    public String createLog(String description) throws SQLException
    {
        String returnlog;
       
        
        Connection con = conPool.checkOut();

        try
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dbo.Log"
                    + "(timesStamp, description) "
                    + "VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, description);
         

            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                returnlog = rs.getLong(1) +"";
            } else {
                return null;
            }
             returnlog = returnlog +"-"+ description;
             
            return returnlog;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }
     
     
     
     public static void main(String[] args) throws Exception {
        
         LogDBDAO ld = new LogDBDAO();
         
         ld.createLog("create task");
    }
    
}

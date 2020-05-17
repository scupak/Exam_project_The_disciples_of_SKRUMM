/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.LogDBDAOInterface;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anton
 */
public class LogDBDAO implements LogDBDAOInterface{
    private final ConnectionPool conPool;

    public LogDBDAO() throws IOException, Exception
    {
        this.conPool = ConnectionPool.getInstance();
    }
    
    @Override
     public List<String> getAllLogs() throws SQLServerException, SQLException
    {
        ArrayList<String> logs = new ArrayList<>();
        Connection con = conPool.checkOut();

        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [Log]");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                logs.add(rs.getTimestamp("timesStamp").toLocalDateTime().toString().trim() +"-"+ rs.getString("userName").trim() +"-"+ rs.getString("action").trim() +"-"+ rs.getString("projectName").trim() +"-"+ rs.getString("taskName").trim()+"-"+ rs.getInt("id"));
            }
            
            return logs;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }
     
     
     
     
     public static void main(String[] args) throws Exception {
        
         LogDBDAO ld = new LogDBDAO();
         
         for (String allLog : ld.getAllLogs()) {
             
             System.out.println(allLog);
             
         }
    }
    
}

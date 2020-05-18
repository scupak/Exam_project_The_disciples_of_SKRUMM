/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public interface LogDBDAOInterface {
    /**
     * gets the 100 most recent log entries
     * @return the 100 most recent log entries as strings in the log window
     * @throws SQLServerException
     * @throws SQLException 
     */ 
    public List<String> getAllLogs() throws SQLServerException, SQLException;
     
     /**
      * creates a log in the DB
      * @param description
      * @return the new log entry
      * @throws SQLException 
      */
     public String createLog(String description) throws SQLException;
    
}

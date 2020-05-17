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
 * @author anton
 */
public interface LogDBDAOInterface {
    
    
     public List<String> getAllLogs() throws SQLServerException, SQLException;
     
     public String createLog(String description) throws SQLException;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 *
 * @author lumby
 */
public class DatabaseConnector
{
     private SQLServerDataSource dataSource;

    /**
     * DatabaseConnector constructor
     * the class takes the info from the DBSettings.txt file and establishes a connection to the database. 
     */
    public DatabaseConnector() throws FileNotFoundException, IOException{
        Properties props = new Properties();
       
        props.load(new FileReader("DBSettings.txt"));
        
        dataSource = new SQLServerDataSource();
        dataSource.setDatabaseName(props.getProperty("database"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));
        dataSource.setServerName(props.getProperty("server"));
    }

    /**
     * Get the connection to the database
     *
     * @return Database connection
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
    
}


package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *The connectionPool stores instances of connection for later reuse. It also takes care of all the fuctionality needed for this purpose. 
 * @author kacpe
 */
public class ConnectionPool extends ObjectPool<Connection>
{

    private static ConnectionPool connectionpool = null;
    private DatabaseConnector dbconnector;

    private ConnectionPool() throws  IOException
    {
        super();
        try
        {
            dbconnector = new DatabaseConnector();
        } catch (IOException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cant connect to the database" + ex);
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }
    
   /**
    * The connectionPoll is a singleton. 
    * @return
    * @throws IOException
    * @throws Exception 
    */
    public static synchronized ConnectionPool getInstance() throws IOException, Exception
    {
        if (connectionpool == null)
        {
            connectionpool = new ConnectionPool();
        }
        return connectionpool;
    }

    /**
     * This method creates a new instance of connection.
     * @return
     * 
     */
    @Override
    protected Connection create() 
    {
        try 
        {
            return dbconnector.getConnection();
        } 
        catch (SQLServerException ex) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cant connect to the database" + ex);
            alert.setContentText("Please try again");
            alert.showAndWait();
            return null;
        }
        
        
    }

    /**
     * This method checks the validity of an instance. 
     * @param o
     * @return
     * 
     */
    @Override
    public boolean validate(Connection o) 
    {
        try
        {
            return !o.isClosed();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cant connect to the database" + ex);
            alert.setContentText("Please try again");
            alert.showAndWait();
            return false;
        }
    }

    /**
     * This method checks if the instance in question has expired. 
     * @param o
     * 
     */
    @Override
    public void expire(Connection o)
    {
        try
        {
            o.close();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cant connect to the database" + ex);
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }

}

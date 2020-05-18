/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public interface ClientManagerInterface
{
    /**
     * gets a client
     * @param client
     * @return client
     * @throws SQLException 
     */
     public Client getClient(Client client) throws SQLException;
    
     /**
      * gets all clients
      * @return all clients
      * @throws SQLServerException
      * @throws SQLException 
      */
     public List<Client> getAllClients() throws SQLServerException, SQLException;
    
     /**
      * gets if a client exists already
      * @param client
      * @return true or false, depending on wether or not the client exists
      * @throws SQLException 
      */
     public boolean clientExist(Client client) throws SQLException;
    
     /**
      * makes a new client
      * @param client
      * @return a new client
      * @throws SQLException 
      */
     public Client createClient(Client client) throws SQLException;
     
     /**
      * deletes an already existing client
      * @param client
      * @return if a client is deleted or not (true or false)
      * @throws SQLException 
      */
     public boolean deleteClient(Client client) throws SQLException;
     
     /**
      * updates an already existing client
      * @param client
      * @return if a client was updated (true or false)
      * @throws SQLException 
      */
     public boolean updateClient(Client client) throws SQLException;
  
}

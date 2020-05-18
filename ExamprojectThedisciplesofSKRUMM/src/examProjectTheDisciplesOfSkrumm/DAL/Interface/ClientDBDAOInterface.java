/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public interface ClientDBDAOInterface
{
    /**
     * gets a client
     * @param client
     * @return a client
     * @throws SQLException 
     */
    public Client getClient(Client client) throws SQLException;
    
    /**
     * gets all clients as a list
     * @return all clients as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Client> getAllClients() throws SQLServerException, SQLException;
    
    /**
     * checks if the given client exists
     * @param client
     * @return true if the client exists, false otherwise
     * @throws SQLException 
     */
    public boolean clientExist(Client client) throws SQLException;
    
    /**
     * creates a client
     * @param client
     * @return the new client
     * @throws SQLException 
     */
    public Client createClient(Client client) throws SQLException;
     
    /**
     * deletes a client
     * @param client
     * @return true if the client was deleted, false otherwise
     * @throws SQLException 
     */
    public boolean deleteClient(Client client) throws SQLException;
    
    /**
     * updates a client
     * @param client
     * @return true if the client was updated, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateClient(Client client) throws SQLServerException, SQLException;
}

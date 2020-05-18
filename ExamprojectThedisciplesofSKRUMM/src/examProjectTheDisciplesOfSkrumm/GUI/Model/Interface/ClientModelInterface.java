/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author SKRUMM
 */
public interface ClientModelInterface 
{
    /**
     * gets a list of clients
     * @return a list of clients
     */
    public ObservableList<Client> getClients();
    
    /**
     * sets clients to the given list
     * @param clients 
     */
    public void setClients(ObservableList<Client> clients);
    
    /**
     * creates a client
     * @param client 
     */
    public void createClient(Client client);
    
    /**
     * deletes a client
     * @param client
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    public boolean deleteClient(Client client) throws SQLException;
    
    /**
     * updates a client
     * @param client
     * @return true if successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateClient(Client client) throws SQLServerException, SQLException;
           
    
}

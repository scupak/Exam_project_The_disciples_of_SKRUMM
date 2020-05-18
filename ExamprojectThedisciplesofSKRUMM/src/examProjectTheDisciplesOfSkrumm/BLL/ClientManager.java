/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.ClientManagerInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public class ClientManager implements ClientManagerInterface
{
    DALFacadeInterface dalfacade;

    public ClientManager(DALFacadeInterface dalfacade) throws IOException, Exception 
    {
        this.dalfacade = dalfacade;
    }
    

    /**
     * gets a specific client
     * @param client
     * @return Client newClient
     * @throws SQLException 
     */
    @Override
    public Client getClient(Client client) throws SQLException 
    {
        Client newClient = dalfacade.getClient(client);
       newClient.getProjects().addAll(dalfacade.getProjectsForClient(newClient));
       return newClient;
    }

    /**
     * Gets a list of clients 
     * @return ArrayList clients
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Client> getAllClients() throws SQLServerException, SQLException 
    {
        ArrayList<Client> clients = new ArrayList<>();
        clients.addAll(dalfacade.getAllClients());
        for (Client client : clients)
        {
            
            if(dalfacade.getProjectsForClient(client) != null && !dalfacade.getProjectsForClient(client).isEmpty())
            {
               client.getProjects().addAll(dalfacade.getProjectsForClient(client));
            }
            
        }
        
       return clients;
    }

    /**
     * checks if a client exists
     * @param client
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean clientExist(Client client) throws SQLException 
    {
        return dalfacade.clientExist(client);
    }

    /**
     * Creates a new client
     * @param client
     * @return client
     * @throws SQLException 
     */
    @Override
    public Client createClient(Client client) throws SQLException 
    {
        return dalfacade.createClient(client);
    }

    /**
     * Deletes a client
     * @param client
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteClient(Client client) throws SQLException {
       return dalfacade.deleteClient(client);
    }

    /**
     * updates an existing client
     * @param client
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean updateClient(Client client) throws SQLException {
        return dalfacade.updateClient(client);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.ClientManagerInterface;
import examProjectTheDisciplesOfSkrumm.DAL.DALFacade;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kacpe
 */
public class ClientManager implements ClientManagerInterface
{
    DALFacadeInterface dalfacade;

    public ClientManager() throws IOException 
    {
        dalfacade = new DALFacade();
    }
    

    @Override
    public Client getClient(Client client) throws SQLException 
    {
        Client newClient = dalfacade.getClient(client);
       newClient.getProjects().addAll(dalfacade.getProjectsForClient(newClient));
       return newClient;
    }

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

    @Override
    public boolean clientExist(Client client) throws SQLException 
    {
        return dalfacade.clientExist(client);
    }

    @Override
    public Client createClient(Client client) throws SQLException 
    {
        return dalfacade.createClient(client);
    }

    @Override
    public boolean deleteClient(Client client) throws SQLException {
       return dalfacade.deleteClient(client);
    }
    
}

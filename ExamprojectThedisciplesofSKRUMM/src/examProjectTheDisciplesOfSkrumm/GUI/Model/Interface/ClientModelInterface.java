/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author kacpe
 */
public interface ClientModelInterface 
{
    public ObservableList<Client> getClients();
    
    public void setClients(ObservableList<Client> clients);
    
    public void createClient(Client client);
    
    public boolean deleteClient(Client client) throws SQLException;
           
    
}

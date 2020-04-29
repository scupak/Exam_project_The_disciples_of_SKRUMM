/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author kacpe
 */
public class Client 
{
    private SimpleStringProperty ClientName;
    private SimpleIntegerProperty ClientRate;

    public Client(String ClientName, int ClientRate) 
    {
        this.ClientName = new SimpleStringProperty(ClientName);
        this.ClientRate = new SimpleIntegerProperty(ClientRate);
    }

    public String getClientName() {
        return ClientName.get();
    }

    public void setClientName(String ClientName) {
        this.ClientName.set(ClientName);
    }

    public int getClientRate() {
        return ClientRate.get();
    }

    public void setClientRate(int ClientRate) {
        this.ClientRate.set(ClientRate);
    }
    
    

    @Override
    public String toString() {
        return  "" + ClientName.get();
    }
    
    
    
    
    
}

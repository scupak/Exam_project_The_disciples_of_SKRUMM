/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author kacpe
 */
public class Client 
{
    private SimpleStringProperty ClientName;

    public Client(String ClientName) {
        this.ClientName = new SimpleStringProperty(ClientName);
    }

    public String getClientName() {
        return ClientName.get();
    }

    public void setClientName(String ClientName) {
        this.ClientName.set(ClientName);
    }
    
    
    
}

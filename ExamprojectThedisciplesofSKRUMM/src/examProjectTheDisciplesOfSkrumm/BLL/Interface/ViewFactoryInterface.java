/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author SKRUMM
 */
public interface ViewFactoryInterface 
{
    /**
     * This method returns a view based on a chosen viewtype enum. 
     * @param viewtype
     * @return FXMLLoader
     * @throws Exception 
     * @throws java.io.IOException 
     */
    public FXMLLoader getLoader(ViewTypes viewtype) throws Exception, IOException;
}

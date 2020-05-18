/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Util;

import examProjectTheDisciplesOfSkrumm.DAL.DALFacade;
import examProjectTheDisciplesOfSkrumm.DAL.DALMockFacade;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import examProjectTheDisciplesOfSkrumm.enums.DALFacadeTypes;
import java.io.IOException;

/**
 *
 * @author SKRUMM
 */
public class DALFacadeFactory
{
    /**
     * Creates DALFacades based on given type.
     *
     * @param type
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static DALFacadeInterface CreateDALFacade(DALFacadeTypes type) throws IOException, Exception
    {
        switch (type)
        {
            case PRODUCTION:
                return new DALFacade();
            case MOCK:
                return new DALMockFacade();
            default:
                throw new Exception("Unknown DALFfacade type given to DALFacade factory");
        }

    }
}

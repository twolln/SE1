package org.hbrs.se1.ws22.uebung4;

import org.hbrs.se1.ws22.uebung4.persistence.PersistenceStrategy;
import org.hbrs.se1.ws22.uebung4.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws22.uebung4.view.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {
    ///////////////////////////////////////////////MAIN-METHODE//////////////////////////////////////////////

    public static void main(String[] args) throws Exception {
        /*Container c1 = Container.getInstance();
        c1.startEingabe();*/

        Client client = new Client();
        client.startEingabe();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
}

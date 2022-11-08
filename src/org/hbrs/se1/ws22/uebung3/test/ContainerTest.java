package org.hbrs.se1.ws22.uebung3.test;

import org.hbrs.se1.ws22.uebung3.ConcreteMember;
import org.hbrs.se1.ws22.uebung3.Container;
import org.hbrs.se1.ws22.uebung3.Member;
import org.hbrs.se1.ws22.uebung3.exception.ContainerException;
import org.hbrs.se1.ws22.uebung3.exception.PersistenceException;
import org.hbrs.se1.ws22.uebung3.persistence.PersistenceStrategy;
import org.hbrs.se1.ws22.uebung3.persistence.PersistenceStrategyMongoDB;
import org.hbrs.se1.ws22.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws22.uebung3.view.Client;
import org.hbrs.se1.ws22.uebung3.view.MemberView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

    MemberView memberview;
    Container c;
    Client client;
    private final Member[] memberArray = {  new ConcreteMember(100),    //Index 0
                                            new ConcreteMember(200),    //Index 1
                                            new ConcreteMember(300) };  //Index 2

    @BeforeEach
    void setUp() {
        c = Container.getInstance();
        memberview = new MemberView();
        client = new Client();

        for (Member iMember: memberArray) {
            c.deleteMember(iMember.getID());
        }
    }


    //========================================================================================================
    //Positiv Test: Methode dump der Klasse MemberView testen
    //========================================================================================================
    @Test
    void memberView() throws ContainerException {
        assertEquals(0, c.size());
        c.addMember(memberArray[0]); //add id 100
        assertEquals(1, c.size());
        c.addMember(memberArray[1]); //add id 200
        assertEquals(2, c.size());
        c.addMember(memberArray[2]); //add id 300
        assertEquals(3, c.size());
        c.deleteMember(100);
        assertEquals(2, c.size());

        /*  Expected:
            Member (ID = 200)
            Member (ID = 300)
        */
        memberview.dump(c.getCurrentList());
    }


    //========================================================================================================
    //Positiv Test: Client Klasse testen
    //========================================================================================================
    @Test
    void client() throws ContainerException {
        assertEquals(0, c.size());
        client.addMember(memberArray[0]); //add id 100
        assertEquals(1, c.size());
        client.addMember(memberArray[1]); //add id 200
        assertEquals(2, c.size());
        client.addMember(memberArray[2]); //add id 300
        assertEquals(3, c.size());
        client.deleteMember(100);
        assertEquals(2, c.size());

        /*  Expected:
            Member (ID = 200)
            Member (ID = 300)
        */
        client.getCurrentList();
    }

    //========================================================================================================
    //Postiv Test: Strategie vorhanden, Methoden store und load testen
    //========================================================================================================
    @Test
    void persistenceStrategy() throws ContainerException, PersistenceException {
        PersistenceStrategy<Member> ps = new PersistenceStrategyStream<>();
        c.setPersistenceStrategy(new PersistenceStrategyStream<>());

        client.addMember(memberArray[0]);

        c.store(); //In der Liste befindet sich zu diesem Zeitpunkt nur Member (ID = 100)

        client.addMember(memberArray[1]);
        client.addMember(memberArray[2]);

        c.load(); //Wiederherstellung von Liste in der sich nur der Member mit der ID = 100 befindet

        assertEquals(1,c.getCurrentList().size());
        client.getCurrentList();
    }

    //========================================================================================================
    //Negativ Test: Keine Strategie vorhanden
    //========================================================================================================
    @Test
    void persistenceStrategyNull() throws ContainerException, PersistenceException {

        PersistenceStrategy<Member> persistenceStrategyNull = null;

        c.setPersistenceStrategy(persistenceStrategyNull);
        //c.store();

        assertThrows(PersistenceException.class, () -> c.store());
    }


    //========================================================================================================
    //Negativ Test: PersistenceStrategyMongoDB als Strategie
    //========================================================================================================
    @Test
    void persistenceStrategyMongoDB() throws ContainerException, PersistenceException {

        PersistenceStrategy<Member> mongoDB = new PersistenceStrategyMongoDB<>();

        c.setPersistenceStrategy(mongoDB);

        assertThrows(UnsupportedOperationException.class, () -> c.store());

    }

}
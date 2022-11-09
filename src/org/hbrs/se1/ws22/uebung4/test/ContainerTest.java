package org.hbrs.se1.ws22.uebung4.test;

import org.hbrs.se1.ws22.uebung4.Container;
import org.hbrs.se1.ws22.uebung4.Employee;
import org.hbrs.se1.ws22.uebung4.EmployeeConcrete;

import org.hbrs.se1.ws22.uebung4.exception.*;
import org.hbrs.se1.ws22.uebung4.persistence.*;
import org.hbrs.se1.ws22.uebung4.view.*;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContainerTest {

    Container c;
    Client client;
    private final Employee[] employeeArray = {  new EmployeeConcrete(100),    //Index 0
                                            new EmployeeConcrete(200),    //Index 1
                                            new EmployeeConcrete(300) };  //Index 2

    @BeforeEach
    void setUp() {
        c = Container.getInstance();
        client = new Client();

        for (Employee iEmployee : employeeArray) {
            c.deleteEmployee(iEmployee.getPid());
        }
    }


    //========================================================================================================
    //Positiv Test: Client Klasse testen
    //========================================================================================================
    @Test
    void client() throws ContainerException {
        assertEquals(0, c.size());
        client.addEmployee(employeeArray[0]); //add id 100
        assertEquals(1, c.size());
        client.addEmployee(employeeArray[1]); //add id 200
        assertEquals(2, c.size());
        client.addEmployee(employeeArray[2]); //add id 300
        assertEquals(3, c.size());
        client.deleteEmployee(100);
        assertEquals(2, c.size());

        /*  Expected:
            Employee (ID = 200)
            Employee (ID = 300)
        */
        client.getCurrentList();
    }

    //========================================================================================================
    //Postiv Test: Strategie vorhanden, Methoden store und load testen
    //========================================================================================================
    @Test
    void persistenceStrategy() throws ContainerException, PersistenceException {
        PersistenceStrategy<Employee> ps = new PersistenceStrategyStream<>();
        c.setPersistenceStrategy(new PersistenceStrategyStream<>());

        client.addEmployee(employeeArray[0]);
        client.addEmployee(new EmployeeConcrete(23));

        c.store(); //In der Liste befindet sich zu diesem Zeitpunkt nur Employee (ID = 100)

        client.addEmployee(employeeArray[1]);
        client.addEmployee(employeeArray[2]);

        c.load(); //Wiederherstellung von Liste in der sich nur der Employee mit der ID = 100 befindet

        assertEquals(2,c.getCurrentList().size());
        client.getCurrentList();
    }

    //========================================================================================================
    //Negativ Test: Keine Strategie vorhanden
    //========================================================================================================
    @Test
    void persistenceStrategyNull() throws ContainerException, PersistenceException {

        PersistenceStrategy<Employee> persistenceStrategyNull = null;

        c.setPersistenceStrategy(persistenceStrategyNull);
        //c.store();

        assertThrows(PersistenceException.class, () -> c.store());
    }


    //========================================================================================================
    //Negativ Test: PersistenceStrategyMongoDB als Strategie
    //========================================================================================================
    @Test
    void persistenceStrategyMongoDB() throws ContainerException, PersistenceException {

        PersistenceStrategy<Employee> mongoDB = new PersistenceStrategyMongoDB<>();

        c.setPersistenceStrategy(mongoDB);

        assertThrows(PersistenceException.class, () -> c.store());

    }

}
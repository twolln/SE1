package org.hbrs.se1.ws22.uebung4.view;

import org.hbrs.se1.ws22.uebung4.Container;
import org.hbrs.se1.ws22.uebung4.Employee;
import org.hbrs.se1.ws22.uebung4.exception.ContainerException;


import java.util.List;
import java.util.Scanner;

public class Client {

    //========================================================================================================
    private final Container container = Container.getInstance();
    //========================================================================================================


    public void getCurrentList() {
        List<Employee> arrayList = container.getCurrentList();

        for (Employee iEmployee : arrayList) {
            System.out.println(iEmployee.toString());
        }
    }

    public void addEmployee(Employee employee) throws org.hbrs.se1.ws22.uebung4.exception.ContainerException {
        container.addEmployee(employee);
    }

    public void deleteEmployee(Integer id) throws ContainerException {
        container.deleteEmployee(id);
    }

    public void startEingabe() throws Exception {
        container.startEingabe();
    }

    public void startAusgabe() {
        container.startAusgabe();
    }




}

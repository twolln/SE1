package org.hbrs.se1.ws22.uebung3.view;

import org.hbrs.se1.ws22.uebung3.Container;
import org.hbrs.se1.ws22.uebung3.Member;
import org.hbrs.se1.ws22.uebung3.exception.ContainerException;

public class Client {

    //========================================================================================================
    private  final MemberView memberview = new MemberView();
    private final Container container = Container.getInstance();
    //========================================================================================================


    public void getCurrentList() {
        memberview.dump(container.getCurrentList());
    }

    public void addMember(Member member) throws ContainerException {
        container.addMember(member);
    }

    public void deleteMember(Integer id) throws ContainerException {
        container.deleteMember(id);
    }

}

package org.hbrs.se1.ws22.uebung3.view;

import org.hbrs.se1.ws22.uebung3.Member;

import java.util.List;

public class MemberView {

    //Methode um alle abgespeicherten Member Objekte zu printen
    public void dump(List<Member> arrayList) {
        for (Member iMember : arrayList) {
            System.out.println(iMember.toString());
        }
    }

}

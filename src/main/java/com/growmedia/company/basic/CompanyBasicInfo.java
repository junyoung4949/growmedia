package com.growmedia.company.basic;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CompanyBasicInfo {

    String name;
    String ceo;
    String privacyOfficer;
    String bizNumber;
    String address;
    String email;

    public CompanyBasicInfo(String name, String ceo, String privacyOfficer, String bizNumber, String address, String email) {
        this.name = name == null? "비어있음" : name;
        this.ceo = ceo == null? "비어있음" : ceo;
        this.privacyOfficer = privacyOfficer == null? "비어있음" : privacyOfficer;
        this.bizNumber = bizNumber == null? "비어있음" : bizNumber;
        this.address = address == null? "비어있음" : address;
        this.email = email == null? "비어있음" : email;
    }
}

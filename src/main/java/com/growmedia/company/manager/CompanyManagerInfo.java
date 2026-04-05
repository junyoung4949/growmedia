package com.growmedia.company.manager;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CompanyManagerInfo {

    String title;
    String name;
    String phone;
    String email;

    public CompanyManagerInfo(String title, String name, String phone, String email) {
        this.title = title == null? "비어있음" : title;
        this.name = name == null? "비어있음" : name;
        this.phone = phone == null? "비어있음" : phone;
        this.email = email == null? "비어있음" : email;
    }
}

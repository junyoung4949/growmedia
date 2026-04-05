package com.growmedia.company;

import com.growmedia.company.basic.CompanyBasicInfo;
import com.growmedia.company.manager.CompanyManagerInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class CompanyInfo {

    private final CompanyBasicInfo basicInfo;
    private final CompanyManagerInfo managerInfo;

    public CompanyInfo(CompanyBasicInfo basicInfo, CompanyManagerInfo managerInfo) {
        if (basicInfo == null) throw new IllegalArgumentException("회사정보가 비어있음");
        if (managerInfo ==null) throw new IllegalArgumentException("담당자 정보가 비어있음");
        this.basicInfo = basicInfo;
        this.managerInfo = managerInfo;
    }
}

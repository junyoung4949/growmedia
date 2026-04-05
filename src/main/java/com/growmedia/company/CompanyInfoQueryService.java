package com.growmedia.company;

import com.growmedia.company.basic.CompanyBasicInfo;
import com.growmedia.company.basic.CompanyBasicInfoRepository;
import com.growmedia.company.manager.CompanyManagerInfo;
import com.growmedia.company.manager.CompanyManagerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyInfoQueryService {

    private final CompanyBasicInfoRepository basicInfoRepository;
    private final CompanyManagerInfoRepository managerInfoRepository;

    public CompanyInfo getCompanyInfo() {
        CompanyBasicInfo basicInfo = basicInfoRepository.findAll();
        CompanyManagerInfo managerInfo = managerInfoRepository.findAll();
        return new CompanyInfo(basicInfo, managerInfo);
    }
}

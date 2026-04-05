package com.growmedia.controller;

import com.growmedia.company.CompanyInfo;
import com.growmedia.company.CompanyInfoQueryService;
import com.growmedia.portfolio.PortfolioQueryService;
import com.growmedia.service.ServiceQueryService;
import com.growmedia.stats.StatsQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PortfolioQueryService portfolioQueryService;
    private final StatsQueryService statsQueryService;
    private final ServiceQueryService serviceQueryService;
    private final CompanyInfoQueryService companyInfoQueryService;

    @GetMapping("/")
    public String index(Model model) {
        CompanyInfo companyInfo = companyInfoQueryService.getCompanyInfo();

        model.addAttribute("portfolio", portfolioQueryService.getVisiblePortfolio());
        model.addAttribute("stats",     statsQueryService.getVisibleStats());
        model.addAttribute("services",  serviceQueryService.getVisibleService());
        model.addAttribute("company",   companyInfo.getBasicInfo());
        model.addAttribute("manager",   companyInfo.getManagerInfo());

        return "index";
    }
}
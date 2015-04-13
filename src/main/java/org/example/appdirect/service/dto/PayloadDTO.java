package org.example.appdirect.service.dto;

import java.util.Map;

public class PayloadDTO {

    private AccountDTO account;
    private CompanyDTO company;
    private Map<String, String> configuration;
    private OrderDTO order;

    public AccountDTO getAccount() {

        return account;
    }

    public void setAccount(final AccountDTO account) {

        this.account = account;
    }

    public CompanyDTO getCompany() {

        return company;
    }

    public void setCompany(final CompanyDTO company) {

        this.company = company;
    }

    public Map<String, String> getConfiguration() {

        return configuration;
    }

    public void setConfiguration(final Map<String, String> configuration) {

        this.configuration = configuration;
    }

    public OrderDTO getOrder() {

        return order;
    }

    public void setOrder(final OrderDTO order) {

        this.order = order;
    }
}

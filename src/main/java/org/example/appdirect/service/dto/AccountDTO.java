package org.example.appdirect.service.dto;

public class AccountDTO {

    private String accountIdentifier;
    private String status;

    public String getAccountIdentifier() {

        return accountIdentifier;
    }

    public void setAccountIdentifier(final String accountIdentifier) {

        this.accountIdentifier = accountIdentifier;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(final String status) {

        this.status = status;
    }
}

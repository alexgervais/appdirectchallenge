package org.example.appdirect.web.rest.dto;

public class CreationResponse extends Response {

    private String accountIdentifier;

    public CreationResponse() {

        super(true);
    }

    public String getAccountIdentifier() {

        return accountIdentifier;
    }

    public void setAccountIdentifier(final String accountIdentifier) {

        this.accountIdentifier = accountIdentifier;
    }

    @Override
    public String toString() {

        return "Response{" +
            "success=" + isSuccess() +
            ", accountIdentifier='" + accountIdentifier + "'" +
            '}';
    }
}

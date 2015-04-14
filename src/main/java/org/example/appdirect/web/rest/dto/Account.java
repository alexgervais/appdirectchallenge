package org.example.appdirect.web.rest.dto;

import java.util.List;

public class Account {

    private String login;
    private List<String> roles;

    public String getLogin() {

        return login;
    }

    public void setLogin(final String login) {

        this.login = login;
    }

    public List<String> getRoles() {

        return roles;
    }

    public void setRoles(final List<String> roles) {

        this.roles = roles;
    }

    @Override
    public String toString() {

        return "Account{" +
            "login='" + login + '\'' +
            ", roles=" + roles +
            '}';
    }
}

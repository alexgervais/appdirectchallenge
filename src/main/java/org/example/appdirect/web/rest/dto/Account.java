package org.example.appdirect.web.rest.dto;

import java.util.List;

public class Account {

    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String langKey;
    private List<String> roles;

    public String getLogin() {

        return login;
    }

    public void setLogin(final String login) {

        this.login = login;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(final String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(final String lastName) {

        this.lastName = lastName;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(final String email) {

        this.email = email;
    }

    public String getLangKey() {

        return langKey;
    }

    public void setLangKey(final String langKey) {

        this.langKey = langKey;
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
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", langKey='" + langKey + '\'' +
            ", roles=" + roles +
            '}';
    }
}

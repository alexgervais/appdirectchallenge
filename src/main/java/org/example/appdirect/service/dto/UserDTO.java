package org.example.appdirect.service.dto;

import java.util.Map;

public class UserDTO {

    private Map<String, String> attributes;
    private String email;
    private String firstName;
    private String lastName;
    private String language;
    private String openId;
    private String uuid;

    public Map<String, String> getAttributes() {

        return attributes;
    }

    public void setAttributes(final Map<String, String> attributes) {

        this.attributes = attributes;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(final String email) {

        this.email = email;
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

    public String getLanguage() {

        return language;
    }

    public void setLanguage(final String language) {

        this.language = language;
    }

    public String getOpenId() {

        return openId;
    }

    public void setOpenId(final String openId) {

        this.openId = openId;
    }

    public String getUuid() {

        return uuid;
    }

    public void setUuid(final String uuid) {

        this.uuid = uuid;
    }
}

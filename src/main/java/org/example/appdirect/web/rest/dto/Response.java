package org.example.appdirect.web.rest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

@JacksonXmlRootElement(localName = "result")
public abstract class Response implements Serializable {

    private boolean success;

    public Response(final boolean success) {

        this.success = success;
    }

    public boolean isSuccess() {

        return success;
    }
}

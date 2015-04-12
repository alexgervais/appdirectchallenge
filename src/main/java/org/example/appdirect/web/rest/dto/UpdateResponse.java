package org.example.appdirect.web.rest.dto;

public class UpdateResponse extends Response {

    public UpdateResponse() {

        super(true);
    }

    @Override
    public String toString() {

        return "UpdateResponse{" +
            "success=" + isSuccess() +
            '}';
    }
}

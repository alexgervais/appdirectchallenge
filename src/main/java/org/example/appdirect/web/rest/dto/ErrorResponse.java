package org.example.appdirect.web.rest.dto;

public class ErrorResponse extends Response {

    private String errorCode = "UNKNOWN_ERROR";
    private String message;

    public ErrorResponse() {

        super(false);
    }

    public String getErrorCode() {

        return errorCode;
    }

    public void setErrorCode(final String errorCode) {

        this.errorCode = errorCode;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(final String message) {

        this.message = message;
    }

    @Override
    public String toString() {

        return "UpdateResponse{" +
            "success=" + isSuccess() +
            ", errorCode='" + errorCode + "'" +
            ", message='" + message + "'" +
            '}';
    }
}

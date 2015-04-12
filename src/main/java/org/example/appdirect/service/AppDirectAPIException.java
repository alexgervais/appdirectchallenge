package org.example.appdirect.service;

public class AppDirectAPIException extends Exception {

    public AppDirectAPIException(final String eventURL, final Throwable cause) {

        super(String.format("An error occurred while requesting event url [%s]", eventURL), cause);
    }
}

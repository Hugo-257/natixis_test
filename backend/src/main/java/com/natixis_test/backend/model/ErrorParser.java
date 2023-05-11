package com.natixis_test.backend.model;

public class ErrorParser {

    public static final String DEFAULT_ERROR_MESSAGE="Something went wrong!";
    String description;

    public  ErrorParser(String description){
        this.description=description;
    }

}

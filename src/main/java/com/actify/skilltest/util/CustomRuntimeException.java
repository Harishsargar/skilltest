package com.actify.skilltest.util;

public class CustomRuntimeException extends RuntimeException {

    public CustomRuntimeException(String message){
        super(message);
    }

    public CustomRuntimeException(){
        super("Resource Not Found!!");
    }

}

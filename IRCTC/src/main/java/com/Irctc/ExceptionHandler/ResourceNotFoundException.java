package com.Irctc.ExceptionHandler;

import lombok.Getter;
import lombok.Setter;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}

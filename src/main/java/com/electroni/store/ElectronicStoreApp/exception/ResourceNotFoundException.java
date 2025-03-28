package com.electroni.store.ElectronicStoreApp.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException
{
    //default constructor
    public ResourceNotFoundException()
    {
        super("Resource Not Found..!!");
    }

    //parameterized constructor
    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}

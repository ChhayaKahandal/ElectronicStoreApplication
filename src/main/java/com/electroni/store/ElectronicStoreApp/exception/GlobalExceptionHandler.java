package com.electroni.store.ElectronicStoreApp.exception;

import com.electroni.store.ElectronicStoreApp.dtoclasses.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//this class holds the logic to handle the ResourceNotFound exception.
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //logic to handle resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)//ex is the obejet of generated exception for resorcenotfoundclass.
    {
        logger.info("Exception Handler invoked !!");
         ApiResponseMessage response=ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
         return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }




    //logic to handle the Method Argument Not valid Exception.(mhnje apn dto mdhe je validator lavle ahet tysathi exception handle krtoy)
    //MethodArgumentNotValidException it is the inbuild class.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        //here we getting all errors from ex object.and store into list.
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response=new HashMap<>();
        //now here we are traversing on that list to get one field error.
        allErrors.stream().forEach(objError->{
            String message = objError.getDefaultMessage();//value.we get error message for that fieldname
            String field = ((FieldError) objError).getField();//key.we get field name
            response.put(field,message);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);


    }



    //logic to handle image file extension exception(handle BadApiRequest)
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex)//ex is the obejet of generated exception for BadApiRequestclass.
    {
        logger.info("Bad Api request!!");
        ApiResponseMessage response=ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}

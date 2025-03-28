package com.electroni.store.ElectronicStoreApp.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
//this interface is for annoattion that is ImageNameValid
@Target({ElementType.FIELD,ElementType.PARAMETER})//targeting on field and parameter.
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy =ImageNameValidator.class)//where we want to write logic of this annotaion.
//@interface annotation is used to make that interface as annotation.means we use ImageNameValid as a annotation.
public @interface  ImageNameValid
{
    //error mesasage
    String message() default "Invalid image name..!!";
     //represent group of constraints
    Class<?>[] groups() default { };
      //represent additional information about annoatation.
    Class<? extends Payload>[] payload() default { };

}

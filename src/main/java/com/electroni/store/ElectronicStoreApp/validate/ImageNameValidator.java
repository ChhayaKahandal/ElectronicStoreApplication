package com.electroni.store.ElectronicStoreApp.validate;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//this class is for provide the lodic for ImageNameValid(annoattion)
public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String>//<ImageNameValid=annoation type,String=ani ti property kontya type chi ahe (apli imageNamwe property string type chi ahe.>
{
    //logger to check that our logic is correctly works or not
    private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        logger.info("Message from isValid method : {}",value);

        //logic to check the imageName is blank or not
        if(value.isBlank())
        {
            return false;//value is incorrect.
        }
        else
        {
            return true;//valid
        }


    }
}

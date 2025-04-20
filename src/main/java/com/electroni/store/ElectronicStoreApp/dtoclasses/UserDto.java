package com.electroni.store.ElectronicStoreApp.dtoclasses;


import com.electroni.store.ElectronicStoreApp.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto
{
    private String userId;

    @Size(min=3,max=25,message="Invalid name..!!")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid User email!!")
    //@Email(message="Invalid user email")
    @NotBlank(message="Email is required")
    private String email;

     @NotBlank(message = "password is required..!!")
    private String password;

    @Size(min=4,max=6,message = "invalid gender!!")
    private String gender;

    @NotBlank(message = "Write something about yourself!!")
    private String about;


    //custom validation
    @ImageNameValid
    private String imageName;

}


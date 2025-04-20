package com.electroni.store.ElectronicStoreApp.controllers;


import com.electroni.store.ElectronicStoreApp.dtoclasses.ApiResponseMessage;
import com.electroni.store.ElectronicStoreApp.dtoclasses.ImageResponse;
import com.electroni.store.ElectronicStoreApp.dtoclasses.PageableResponse;
import com.electroni.store.ElectronicStoreApp.dtoclasses.UserDto;
import com.electroni.store.ElectronicStoreApp.services.FileService;
import com.electroni.store.ElectronicStoreApp.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;//UserService interface

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")//this path is declare in app.properties
    private String imageUploadPath;

    private Logger logger= LoggerFactory.getLogger(UserController.class);
    //create
    @PostMapping
    //In Requestbody we are taking userdto details and return that userdto to service with responseentity.
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto userDto1=userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);//sending userDto and https code.
    }

    //update
    @PutMapping("/{userId}")//path uri variable.
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,
                                              @Valid @RequestBody UserDto userDto)//jo user update krychha ahe tyacha id ghetoye ani ky ky update krych ahet te details ghteoy
    {
        UserDto updatedUserDto1=userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updatedUserDto1, HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId)
    {
        userService.deleteUser(userId);
        //ApiResponseMessage message=ApiResponseMessage.builder().message("User is deleted Successfully").success(true).status(HttpStatus.OK).build();
        ApiResponseMessage message=ApiResponseMessage.builder().message("User is deleted Successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);//we sending that successfull meassage in json format .

    }

    //delete the image from images folder which are related to that user.
    @DeleteMapping("includingrelatedimages/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser1(@PathVariable String userId)
    {

            userService.deleteUserdata(userId);

        //ApiResponseMessage message=ApiResponseMessage.builder().message("User is deleted Successfully").success(true).status(HttpStatus.OK).build();
        ApiResponseMessage message=ApiResponseMessage.builder().message("User is deleted Successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);//we sending that successfull meassage in json format .

    }
    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir

    )//value=optional parameter astil,mhnjech mandatory nastil.
    {
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    //get single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId)
    {
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }
    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }
    //search user by keyword
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> getUserByKeyword(@PathVariable String keywords)
    {
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserimage( @RequestParam("userImage") MultipartFile image,@PathVariable String userId) throws IOException {
       String imageName=fileService.uploadFile(image,imageUploadPath);

       //we getting that user where we want to add this image name in database.
        UserDto user= userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
       return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    //serve user image mhnjech postman chya console vr aplyla particular user chi  image disel.
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId , HttpServletResponse response) throws IOException {
         UserDto user=userService.getUserById(userId);
         logger.info("User image Name: {}",user.getImageName());//user chi image kay ahe ji apan kadhtoy
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }


    //jevha apn user la delete kru tevha tyachya related jya image aplya "images" folder mdhe ahet tya pn delet jhalya phijet.





}

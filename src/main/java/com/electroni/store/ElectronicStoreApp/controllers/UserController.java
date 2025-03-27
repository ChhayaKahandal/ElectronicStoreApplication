package com.electroni.store.ElectronicStoreApp.controllers;


import com.electroni.store.ElectronicStoreApp.dtoclasses.ApiResponseMessage;
import com.electroni.store.ElectronicStoreApp.dtoclasses.UserDto;
import com.electroni.store.ElectronicStoreApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;//UserService interface
    //create
    @PostMapping
    //In Requestbody we are taking userdto details and return that userdto to service with responseentity.
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
    {
        UserDto userDto1=userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);//sending userDto and https code.
    }

    //update
    @PutMapping("/{userId}")//path uri variable.
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,
                                              @RequestBody UserDto userDto)//jo user update krychha ahe tyacha id ghetoye ani ky ky update krych ahet te details ghteoy
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
    //get all
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
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


}

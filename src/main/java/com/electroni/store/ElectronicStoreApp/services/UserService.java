package com.electroni.store.ElectronicStoreApp.services;

import com.electroni.store.ElectronicStoreApp.dtos.UserDto;
import com.electroni.store.ElectronicStoreApp.entities.User;

import java.util.List;

public interface UserService
{
    //create user
    UserDto createUser(UserDto userDto);
    //update user
    UserDto updateUser(UserDto userDto,String userId);
    //delete user
    void deleteUser(String userId);
    //get all users
    List<UserDto> getAllUser();
    //get single user by id
    UserDto getUserById(String userId);
    //get single user by email
    UserDto getUserByEmail(String email);
    //search user
    List<UserDto> searchUser(String keyword);
    //other user specific feature
}

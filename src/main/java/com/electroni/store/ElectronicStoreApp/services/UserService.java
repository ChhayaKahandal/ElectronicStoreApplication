package com.electroni.store.ElectronicStoreApp.services;

import com.electroni.store.ElectronicStoreApp.dtoclasses.PageableResponse;
import com.electroni.store.ElectronicStoreApp.dtoclasses.UserDto;
import com.electroni.store.ElectronicStoreApp.entities.User;

import java.io.IOException;
import java.util.List;

public interface UserService
{
    //create user
    UserDto createUser(UserDto userDto);
    //update user
    UserDto updateUser(UserDto userDto,String userId);
    //delete user
    void deleteUser(String userId);
    //delete user related all data like image in "images" folder
    void deleteUserdata(String userId) ;
    //get all users
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
    //get single user by id
    UserDto getUserById(String userId);
    //get single user by email
    UserDto getUserByEmail(String email);
    //search user
    List<UserDto> searchUser(String keyword);
    //other user specific feature
}

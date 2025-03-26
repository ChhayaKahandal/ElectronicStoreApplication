package com.electroni.store.ElectronicStoreApp.services.impl;

import com.electroni.store.ElectronicStoreApp.dtos.UserDto;
import com.electroni.store.ElectronicStoreApp.entities.User;
import com.electroni.store.ElectronicStoreApp.repositories.UserRepository;
import com.electroni.store.ElectronicStoreApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto)
    {
        //generate unique Id in string format(we dont want id from user)
        String userId= UUID.randomUUID().toString();

        //converting Dto into entity.
        User user=dtoToEntity(userDto);
        User savedUser=userRepository.save(user);

        //converting entity to dto.
        UserDto newDto=entityToDto(savedUser);
        return newDto;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId)
    {
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found with this Id"));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setGender(user.getGender());
        user.setImageName(user.getImageName());
        User updatedUser=userRepository.save(user);

        return entityToDto(updatedUser);
    }

    @Override
    public void deleteUser(String userId)
    {
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user with this id not found"));
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser()
    {
        List<User> users=userRepository.findAll();
        List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId)
    {
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found with this id"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with given email id."));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword)
    {
        List<User> users=userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }


    //converting entity to dto manually.
    private UserDto entityToDto(User savedUser)
    {
        UserDto userDto=UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .gender(savedUser.getGender())
                .imageName(savedUser.getImageName())
                .build();
        return userDto;
    }
    //converting Dto into entity manually.
    private User dtoToEntity(UserDto userDto)
    {
        User user=User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getName())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .imageName(userDto.getImageName())
                .build();

        return user;
    }
}

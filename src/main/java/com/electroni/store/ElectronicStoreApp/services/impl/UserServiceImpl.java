package com.electroni.store.ElectronicStoreApp.services.impl;

import com.electroni.store.ElectronicStoreApp.dtoclasses.PageableResponse;
import com.electroni.store.ElectronicStoreApp.dtoclasses.UserDto;
import com.electroni.store.ElectronicStoreApp.entities.User;
import com.electroni.store.ElectronicStoreApp.exception.ResourceNotFoundException;
import com.electroni.store.ElectronicStoreApp.helper.Helper;
import com.electroni.store.ElectronicStoreApp.repositories.UserRepository;
import com.electroni.store.ElectronicStoreApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserDto createUser(UserDto userDto)
    {
        //generate unique Id in string format(we dont want id from user)
        String userId= UUID.randomUUID().toString();
        userDto.setUserId(userId);

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
        //hanadling exception using custom exception class ResourceNotFoundException
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with this Id"));
        /*ResourceNotFoundException("User not found with this Id")); ha msg ya class chya para constructor mdhe jail .tithun to GlobalException
        class mdhe ex.getMessage() ne get hoil ani aplyala to console vr disel.*/
        //User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found with this Id"));
        //here we are setting the new information from userDto to existing user.
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        //save data
        User updatedUser=userRepository.save(user);
        UserDto updatedDto=entityToDto(updatedUser);
        return updatedDto ;
    }

    @Override
    public void deleteUser(String userId)
    {
        //User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user with this id not found"));
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with this id not found"));
        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir)
    {

        //sort is class
       // Sort sort = Sort.by(sortBy);
        //we apply turnary oprator..jar sortDir variable mdhe desc asel tr descending chi method call hoil.ani dusr khi asel tr ascending chi method call hoil.
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        //page numbesrs are start by default from 0 .
        //pageable is interface,we cant create direct objet of this.
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);//pageNumber-1  ithe aplyala page number 0 pasun bhettoy pn aplyala 1 pasun hava ahe mhnun he logic dil.
        Page<User> page=userRepository.findAll(pageable);
        // List<User> users=userRepository.findAll();//normal operation

        /*List<User> users = page.getContent();//using this we get list of users according to pagesize and pagenumber.
        List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
        PageableResponse<UserDto> response=new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;*/
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
          return response;
        //return dtoList;
    }

    @Override
    public UserDto getUserById(String userId)
    {
        //User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user with this id not found"));
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with this id"));//ha message hya class chya parameteri
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email)
    {
        //User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user with this id not found"));
        User user=userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email id."));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword)
    {
        List<User> users=userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }



//jevha apn user la delete kru tevha tyachya related jya image aplya "images" folder mdhe ahet tya pn delet jhalya phijet.
  @Override
    public void deleteUserdata(String userId)  {
    User userDelete = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("user not found with given id "));


    //delete user profile image
    //  images/user/ + abc.png =   images/user/abc.png
    String fullPath= imagePath + userDelete.getImageName();
    Path path= Paths.get(fullPath);
    try {
        Files.delete(path);
    } catch (NoSuchFileException ex) {
        logger.info("user image not foud in folder");
        ex.printStackTrace();

    }catch (IOException e) {
        e.printStackTrace();
    }

    //delete user
    userRepository.delete(userDelete);

    }

    //converting entity to dto manually.
    /*private UserDto entityToDto(User savedUser)
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
    }*/
    //converting Dto into entity manually.
    /*private User dtoToEntity(UserDto userDto)
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
    }*/

    //converting entity to dto using model apper.
    private UserDto entityToDto(User savedUser)
    {
        return mapper.map(savedUser,UserDto.class);//first parameter is user that we have to convert into dto which second param.

    }

    //converting dto to entity  using model apper.
    private User dtoToEntity(UserDto userDto)
    {
        return mapper.map(userDto,User.class);//first parameter is userDto that we have to convert into user which second param.

    }

}

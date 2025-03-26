package com.electroni.store.ElectronicStoreApp.repositories;

import com.electroni.store.ElectronicStoreApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,String>
{
    //custom method for get user by passed email.
    //runtime la JPA "where" chi query right krel.
    //User findByEmail(String email);
    //if we dont get that user object then to handle that situation so thats why we use optional.
    Optional<User> findByEmail(String email);
    //custom method for get user by passed email&password.
    //runtime la JPA "where" chi query "and" sobt write krel.
    //User findByEmailAndPassword(String email,String password);
    Optional<User>findByEmailAndPassword(String email,String password);
    //custom method to find the user list which contains that given keywords.
    //runtime la JPA "Like" chi query write krel.
    //List<User> findByNameContaining(String keywords);
    List<User> findByNameContaining(String keywords);

}

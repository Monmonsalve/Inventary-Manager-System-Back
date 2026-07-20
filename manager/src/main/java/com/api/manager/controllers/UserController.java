package com.api.manager.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.manager.models.UserModel;
import com.api.manager.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Get Users
    @GetMapping
    public ArrayList<UserModel> getUser(){
        return this.userService.getUser();
    }

    //Create user
    @PostMapping
    public UserModel saveUser(@RequestBody UserModel user){
        if(user != null){
            return this.userService.saveUser(user);
        }else{
            return null;
        }
    }

    //Search User by Id
    @GetMapping(path = "/{id}")
    public Optional<UserModel> getUserById(@PathVariable("id")Long id){
        return this.userService.getById(id);
    }

    //Update user
    @PutMapping(path = "/{id}")
    public UserModel updateUserById(@RequestBody UserModel request, @PathVariable("id")Long id){
        return this.userService.updateById(request, id);
    }

    //Delete user By Id
    @DeleteMapping(path = "{id}")
    public String deleteById(@PathVariable("id")Long id){
        boolean ok = this.userService.deleteUser(id);
        if(ok){ 
            return "User deleted with id: " + id;
        }else{
            return "Could not delete user with id: " + id;
        }
    }

}

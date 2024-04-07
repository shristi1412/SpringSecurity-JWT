package com.springsecurity.SpringSecurity.controllers;

import com.springsecurity.SpringSecurity.Service.UserService;
import com.springsecurity.SpringSecurity.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class HomeController {

    private UserService users;
    public HomeController(UserService users){
        this.users=users;
    }
   // http://localhost:8083/admin/users

    @GetMapping("/users")
    public List<User> getUser(){
        System.out.println("Getting Users");
        return this.users.getUsers();
    }

    @GetMapping("/current-user")
    public String getCurrentUser(Principal p){
        return  p.getName();
    }

}

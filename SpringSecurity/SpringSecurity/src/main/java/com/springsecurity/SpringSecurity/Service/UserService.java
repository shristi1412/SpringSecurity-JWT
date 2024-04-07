package com.springsecurity.SpringSecurity.Service;

import com.springsecurity.SpringSecurity.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {
    private List<User> store = new ArrayList<>();

    public UserService(){
        store.add(new User(UUID.randomUUID().toString(), "test", "test@12gmail.com", "test21"));
        store.add(new User(UUID.randomUUID().toString(), "test12", "test@18gmail.com", "test22"));
        store.add(new User(UUID.randomUUID().toString(), "test14", "test@22gmail.com", "test33"));
    }

    public List<User> getUsers(){
        return store;
    }
}

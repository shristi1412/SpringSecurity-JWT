package com.springsecurity.SpringSecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER_TBL")
public class User {

    @Id
    private String id;
    private String userName;
    private String email;
    private String password;

    public User(String id, String password, Optional<String> email, String userName){
        this.id = Objects.requireNonNull(id);
        this.password= Objects.requireNonNull(password);
        this.userName=Objects.requireNonNull(userName);
        this.email= email.orElseThrow(()-> new IllegalArgumentException("Cannot be null email"));
    }
}

package com.example.todospringbootbackend.service;

import org.springframework.stereotype.Component;

@Component
public class LoginService {

    //only excepts one hard-coded user for now
    //could also be implemented as interface
    //then one dummy implementation could use hardcoded values like this
    //and one implementation could talk to DB
    //when switch between the 2 implementations,
    // -> nothing needs to be changed in loginController
    public boolean validateUser(String username, String password){
        return username.equalsIgnoreCase("peter")
                && password.equalsIgnoreCase("pan");
    }
}

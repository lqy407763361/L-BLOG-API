package com.lblog.api;

import com.lblog.domain.User;
import com.lblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Object login(User user){
        userService.login(user);
        return 1;
    }
}

package com.beratozturk.SystemOfExam.Controller;

import com.beratozturk.SystemOfExam.Model.User;
import com.beratozturk.SystemOfExam.Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
}

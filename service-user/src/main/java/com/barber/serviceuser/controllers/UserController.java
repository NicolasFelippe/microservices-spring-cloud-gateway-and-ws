package com.barber.serviceuser.controllers;

import com.barber.serviceuser.entities.User;
import com.barber.serviceuser.entities.VO.ResponseTemplateVO;
import com.barber.serviceuser.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping()
    public ResponseTemplateVO getUser(
            @RequestHeader(value = "id") Long userId,
            @RequestHeader(value = "role") String role) {
        return userService.getUserWithDepartment(userId);
    }

    @GetMapping(value = "/secure")
    public String getSecure() {
        return "Secure endpoint available";
    }
}
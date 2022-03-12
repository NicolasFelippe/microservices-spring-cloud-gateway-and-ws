package com.barber.serviceuser.controllers;

import com.barber.serviceuser.entities.User;
import com.barber.serviceuser.entities.VO.AuthVO;
import com.barber.serviceuser.entities.VO.ResponseTemplateVO;
import com.barber.serviceuser.entities.VO.UserVO;
import com.barber.serviceuser.exceptions.EmailAlreadyExistsException;
import com.barber.serviceuser.exceptions.UserNonExistsException;
import com.barber.serviceuser.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public UserVO save(@Valid @RequestBody UserVO userVO) throws EmailAlreadyExistsException {
        return userService.save(userVO);
    }

    @PostMapping("/authentication")
    public UserVO getUser(@RequestBody AuthVO authVO) throws UserNonExistsException, AuthException {
        return userService.authentication(authVO);
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
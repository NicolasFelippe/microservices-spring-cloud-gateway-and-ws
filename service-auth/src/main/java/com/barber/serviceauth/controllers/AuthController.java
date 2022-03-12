package com.barber.serviceauth.controllers;


import com.barber.serviceauth.entities.AuthRequest;
import com.barber.serviceauth.entities.AuthResponse;
import com.barber.serviceauth.entities.vo.AuthVO;
import com.barber.serviceauth.entities.vo.UserVO;
import com.barber.serviceauth.exceptions.NonAuthorizeException;
import com.barber.serviceauth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserVO> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthVO authVO) throws NonAuthorizeException {
        return ResponseEntity.ok(authService.authetication(authVO));
    }

}
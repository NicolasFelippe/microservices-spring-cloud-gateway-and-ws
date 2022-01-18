package com.barber.servicegateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class GatewayController {

    @GetMapping("/first")
    public String defaultMessage(){
        return "Houve algum erro ao conectar. Por favor, tente novamente mais tarde.";
    }
}

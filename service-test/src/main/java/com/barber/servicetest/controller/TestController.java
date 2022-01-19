package com.barber.servicetest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service-test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    private final Environment env;

    public TestController(Environment env) {
        this.env = env;
    }
    @GetMapping("/hello")
    public String test() {
        String appPort = env.getProperty("local.server.port");
        logger.info(appPort);
        return appPort + ", hello world!";
    }
}

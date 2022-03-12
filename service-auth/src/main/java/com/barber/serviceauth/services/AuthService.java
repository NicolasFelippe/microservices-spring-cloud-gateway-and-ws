package com.barber.serviceauth.services;


import com.barber.serviceauth.entities.AuthRequest;
import com.barber.serviceauth.entities.AuthResponse;
import com.barber.serviceauth.entities.enums.TypeTokenEnum;
import com.barber.serviceauth.entities.vo.AuthVO;
import com.barber.serviceauth.entities.vo.UserVO;
import com.barber.serviceauth.exceptions.NonAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService implements IAuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwt;
    private final String SERVICE_USER = "http://SERVICE-USER/users";

    @Autowired
    public AuthService(RestTemplate restTemplate,
                       final JwtUtil jwt) {
        this.restTemplate = restTemplate;
        this.jwt = jwt;
    }

    public UserVO register(AuthRequest authRequest) {
        UserVO userVO = restTemplate.postForObject(SERVICE_USER, authRequest, UserVO.class);
        Assert.notNull(userVO, "Failed to register user. Please try again later");

        return userVO;
    }

    @Override
    public AuthResponse authetication(AuthVO authVO) throws NonAuthorizeException {
        UserVO userVO = restTemplate.postForObject(SERVICE_USER + "/authentication", authVO, UserVO.class);
        if(userVO != null){
            return new AuthResponse(jwt.generate(userVO, TypeTokenEnum.ACCESS), jwt.generate(userVO, TypeTokenEnum.REFRESH_TOKEN));
        }
        throw new NonAuthorizeException();
    }
}

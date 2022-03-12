package com.barber.serviceauth.services;

import com.barber.serviceauth.entities.AuthRequest;
import com.barber.serviceauth.entities.AuthResponse;
import com.barber.serviceauth.entities.vo.AuthVO;
import com.barber.serviceauth.entities.vo.UserVO;
import com.barber.serviceauth.exceptions.NonAuthorizeException;

public interface IAuthService {

    UserVO register(AuthRequest authRequest);

    AuthResponse authetication(AuthVO authVO) throws NonAuthorizeException;

}

package com.barber.serviceuser.services;

import com.barber.serviceuser.entities.User;
import com.barber.serviceuser.entities.VO.AuthVO;
import com.barber.serviceuser.entities.VO.ResponseTemplateVO;
import com.barber.serviceuser.entities.VO.UserVO;
import com.barber.serviceuser.exceptions.EmailAlreadyExistsException;
import com.barber.serviceuser.exceptions.UserNonExistsException;

import javax.security.auth.message.AuthException;

public interface IUserService {
    ResponseTemplateVO getUserWithDepartment(Long id);

    User getById(Long id);

    UserVO save(UserVO userVO) throws EmailAlreadyExistsException;

    UserVO authentication(AuthVO authVO) throws UserNonExistsException, AuthException;
}

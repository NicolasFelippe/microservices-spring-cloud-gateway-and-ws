package com.barber.serviceuser.services;

import com.barber.serviceuser.entities.User;
import com.barber.serviceuser.entities.VO.ResponseTemplateVO;

public interface IUserService {
    ResponseTemplateVO getUserWithDepartment(Long id);
    User getById(Long id);

    User save(User user);
}

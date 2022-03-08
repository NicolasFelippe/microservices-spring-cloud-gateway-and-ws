package com.barber.serviceuser.services;


import com.barber.serviceuser.entities.User;
import com.barber.serviceuser.entities.VO.ResponseTemplateVO;
import com.barber.serviceuser.repositories.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService implements IUserService {

    private final IUserRepository repository;
    private final RestTemplate restTemplate;

    public UserService(IUserRepository repository,
                       RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }


    public User save(User user) {
        return this.repository.save(user);
    }

    public User getById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public ResponseTemplateVO getUserWithDepartment(Long id) {
        User user = this.getById(id);

//        Department department = restTemplate.getForObject("http://department-service/departments/" + user.getDepartmentId(), Department.class);

        return new ResponseTemplateVO(user);
    }
}
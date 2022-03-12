package com.barber.serviceuser.services;


import com.barber.serviceuser.config.BCryptPasswordEncoder;
import com.barber.serviceuser.entities.User;
import com.barber.serviceuser.entities.VO.AuthVO;
import com.barber.serviceuser.entities.VO.ResponseTemplateVO;
import com.barber.serviceuser.entities.VO.UserVO;
import com.barber.serviceuser.exceptions.EmailAlreadyExistsException;
import com.barber.serviceuser.exceptions.UserNonExistsException;
import com.barber.serviceuser.repositories.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.message.AuthException;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService {

    private final IUserRepository repository;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(IUserRepository repository,
                       RestTemplate restTemplate,
                       ModelMapper modelMapper,
                       BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public UserVO save(UserVO userVO) throws EmailAlreadyExistsException {
        Optional<User> user = repository.findByEmail(userVO.getEmail());
        if (user.isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
        User userSaved = repository.save(modelMapper.map(userVO, User.class));
        return modelMapper.map(userSaved, UserVO.class);
    }

    @Override
    public UserVO authentication(AuthVO authVO) throws UserNonExistsException, AuthException {
        User user = repository.findByEmail(authVO.getEmail()).orElseThrow(UserNonExistsException::new);
        boolean matchPassword = passwordEncoder.matches(authVO.getPassword(), user.getPassword());
        if (matchPassword) {
            return modelMapper.map(user, UserVO.class);
        }
        throw new AuthException("Senha não confere");
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
package com.paic.unittest.demo.service;

import com.paic.unittest.demo.entity.User;
import com.paic.unittest.demo.repository.UserRepository;
import com.paic.unittest.demo.service.dto.XserviceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final XServiceClient serviceClient;

    public UserService(UserRepository userRepository, XServiceClient serviceClient) {
        this.userRepository = userRepository;
        this.serviceClient = serviceClient;
    }

    @Transactional
    public User saveUser(User user) {
        ResponseEntity<XserviceDto> en = serviceClient.getDto();
        log.info("xservice return : {}", en);
        if (en.getStatusCode().is2xxSuccessful() && Objects.nonNull(en.getBody())
                && Objects.nonNull(en.getBody().getField())) {
            user.setPasswordHash(en.getBody().getField());
        }
        log.info("save user {}", user);
        return userRepository.save(user);
    }


    @Transactional
    public User saveUserWithParam(User user) {
        ResponseEntity<XserviceDto> en = serviceClient.getDtoWithParam(user.getLogin());
        log.info("xservice return : {}", en);
        if (en.getStatusCode().is2xxSuccessful() && Objects.nonNull(en.getBody())
                && Objects.nonNull(en.getBody().getField())) {
            user.setPasswordHash(en.getBody().getField());
        }
        log.info("save user {}", user);
        return userRepository.save(user);
    }


    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }



}

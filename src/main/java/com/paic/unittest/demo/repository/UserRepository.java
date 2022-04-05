package com.paic.unittest.demo.repository;

import com.paic.unittest.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByLogin(String login);

    User findUserByLogin(String login);
}

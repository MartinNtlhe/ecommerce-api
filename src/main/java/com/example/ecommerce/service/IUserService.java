package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface IUserService {
    User createUser(User user);
    boolean updateType(User user);
    List<User> findAllUsers();
    Optional<User> findOneUser(Long id);
}

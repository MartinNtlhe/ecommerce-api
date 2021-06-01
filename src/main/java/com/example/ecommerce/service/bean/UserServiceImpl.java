package com.example.ecommerce.service.bean;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserType;
import com.example.ecommerce.resource.IUserRepository;
import com.example.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Override
    public User createUser(User user) {
        if(user != null){
            return repository.save(user);
        }
        return null;
    }

    @Override
    public boolean updateType(User user) {
        if(user != null){
            repository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findOneUser(Long id) {
        return repository.findById(id);
    }
}

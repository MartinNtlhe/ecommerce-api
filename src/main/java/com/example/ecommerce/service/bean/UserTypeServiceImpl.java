package com.example.ecommerce.service.bean;

import com.example.ecommerce.entity.UserType;
import com.example.ecommerce.resource.IUserTypeRepository;
import com.example.ecommerce.service.IUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserTypeServiceImpl implements IUserTypeService {

    @Autowired
    private IUserTypeRepository repository;

    @Override
    public UserType createType(UserType type) {
        if(type != null){
            return repository.save(type);
        }
        return null;
    }

    @Override
    public boolean updateType(UserType type) {
        if(type != null){
            repository.save(type);
            return true;
        }
        return false;
    }

    @Override
    public List<UserType> findAllTypes() {
        return repository.findAll();
    }

    @Override
    public Optional<UserType> findOneType(Long id) {
        return repository.findById(id);
    }
}

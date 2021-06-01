package com.example.ecommerce.service;

import com.example.ecommerce.entity.UserType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface IUserTypeService {
    UserType createType(UserType type);
    boolean updateType(UserType type);
    List<UserType> findAllTypes();
    Optional<UserType> findOneType(Long id);
}

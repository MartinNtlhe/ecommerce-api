package com.example.ecommerce.service.bean;

import com.example.ecommerce.entity.Type;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserAddress;
import com.example.ecommerce.entity.UserType;
import com.example.ecommerce.resource.IUserAddressRepository;
import com.example.ecommerce.resource.IUserRepository;
import com.example.ecommerce.service.IUserService;
import com.example.ecommerce.service.IUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.ecommerce.util.DateUtil.stringToDate;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository repository;
    @Autowired
    private IUserAddressRepository iUserAddressRepository;
    @Autowired
    private IUserTypeService userTypeService;

    @Autowired
    public void createDefaultUser() {
        final User inputPerson = buildUser("12343", buildUserType(Type.CUSTOMER) , stringToDate("22-09-2009"));
        User savedUser = repository.save(inputPerson);
        final UserAddress userAddress = new UserAddress();
        userAddress.setCompany("Ecommerce");
        userAddress.setAddress("127.0.0.1");
        userAddress.setPhone("12345");
        userAddress.setEmail("master@localhost");
        userAddress.setUser(savedUser);
        final UserAddress savedAddress = iUserAddressRepository.save(userAddress);
    }

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

    private User buildUser(final String idNumber, final UserType userType, final Date joinedDate) {
        final User user = new User();
        user.setIdNumber(idNumber);
        user.setUserType(userType);
        user.setJoinDate(joinedDate);
        return user;
    }

    private UserType buildUserType (final Type name) {
        return userTypeService.findOneTypeByType(name);
    }
}

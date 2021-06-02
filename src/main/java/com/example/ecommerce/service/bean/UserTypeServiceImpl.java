package com.example.ecommerce.service.bean;

import com.example.ecommerce.entity.BillItem;
import com.example.ecommerce.entity.Type;
import com.example.ecommerce.entity.UserType;
import com.example.ecommerce.resource.IUserTypeRepository;
import com.example.ecommerce.service.IUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    public List<UserType> createDefault() {
        final List<UserType> userTypes = new ArrayList<UserType>() {{
            add(buildUserType(Type.EMPLOYEE));
            add(buildUserType(Type.AFFILIATE));
            add(buildUserType(Type.CUSTOMER));
        }};

        return repository.saveAll(userTypes);
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

    @Override
    public UserType findOneTypeByType(Type type) {
        List<UserType> all = repository.findAll();
        for (UserType detail: all) {
            if(detail.getType().equals(type)){
                return detail;
            }
        }
        return null;
    }

    private UserType buildUserType (final Type name) {
        final UserType type = new UserType();
        type.setType(name);
        return type;
    }
}

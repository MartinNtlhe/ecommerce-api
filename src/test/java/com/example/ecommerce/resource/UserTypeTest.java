package com.example.ecommerce.resource;

import com.example.ecommerce.entity.Type;
import com.example.ecommerce.entity.UserType;
import com.example.ecommerce.service.bean.UserTypeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserTypeTest {

    @Mock
    private IUserTypeRepository iUserTypeRepository;

    @InjectMocks
    private UserTypeServiceImpl userTypeServiceImpl;

    @Test
    public void shouldGetUserTypes() {
        final List<UserType> expectedUserTypes = new ArrayList<UserType>() {{
            add(buildUserType(Type.EMPLOYEE));
            add(buildUserType(Type.AFFILIATE));
            add(buildUserType(Type.CUSTOMER));
        }};

        when(iUserTypeRepository.findAll()).thenReturn(expectedUserTypes);
        final List<UserType> actualUserTypes = userTypeServiceImpl.findAllTypes();

        verify(iUserTypeRepository, times(1)).findAll();
        assertEquals(expectedUserTypes, actualUserTypes);
    }

    private UserType buildUserType (final Type name) {
        final UserType type = new UserType();
        type.setType(name);
        return type;
    }

}

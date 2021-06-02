package com.example.ecommerce.resource;

import com.example.ecommerce.EcommerceApplication;
import com.example.ecommerce.entity.Type;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserAddress;
import com.example.ecommerce.entity.UserType;
import com.example.ecommerce.service.bean.UserServiceImpl;
import com.example.ecommerce.service.bean.UserTypeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.ecommerce.util.DateUtil.stringToDate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EcommerceApplication.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @Mock
    private IUserRepository iUserRepository;
    @Mock
    private IUserAddressRepository iUserAddressRepository;
    @Mock
    private IUserTypeRepository iUserTypeRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @InjectMocks
    private UserTypeServiceImpl userTypeServiceImpl;

    @Test
    public void shouldRegisterNewEmployee() {
        final User inputPerson = buildUser("12343", buildUserType(Type.EMPLOYEE) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost");
        final User expectedPerson = Mockito.mock(User.class);

        when(iUserRepository.save(inputPerson)).thenReturn(expectedPerson);
        final User actualPerson = userServiceImpl.createUser(inputPerson);

        verify(iUserRepository, times(1)).save(inputPerson);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void shouldRegisterNewAffliate() {
        final User inputPerson = buildUser("12343", buildUserType(Type.AFFILIATE) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost");
        final User expectedPerson = Mockito.mock(User.class);

        when(iUserRepository.save(inputPerson)).thenReturn(expectedPerson);
        final User actualPerson = userServiceImpl.createUser(inputPerson);

        verify(iUserRepository, times(1)).save(inputPerson);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void shouldRegisterOldCustomer() {
        final User inputPerson = buildUser("12343", buildUserType(Type.CUSTOMER) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost");
        final User expectedPerson = Mockito.mock(User.class);

        when(iUserRepository.save(inputPerson)).thenReturn(expectedPerson);
        final User actualPerson = userServiceImpl.createUser(inputPerson);

        verify(iUserRepository, times(1)).save(inputPerson);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void shouldRegisterNewCustomer() {
        final User inputPerson = buildUser("11121", buildUserType(Type.CUSTOMER) , stringToDate("22-09-2020"), "ECommerce", "127.0.0.5", "1235", "customer1@localhost");
        final User expectedPerson = Mockito.mock(User.class);

        when(iUserRepository.save(inputPerson)).thenReturn(expectedPerson);
        final User actualPerson = userServiceImpl.createUser(inputPerson);

        verify(iUserRepository, times(1)).save(inputPerson);
        assertEquals(expectedPerson, actualPerson);
    }

    private User buildUser(final String idNumber, final UserType userType, final Date joinedDate, final String company, final String address, final String phone, final String email) {
        final User user = new User();
        final UserAddress inputContactDetails = buildAddressDetail(company, address, phone, email);

        user.setIdNumber(idNumber);
        user.setUserType(userType);
        user.setAddress(inputContactDetails);
        user.setJoinDate(joinedDate);
        return user;
    }

    private UserAddress buildAddressDetail(final String company, final String address, final String phone, final String email) {
        final UserAddress userAddress = new UserAddress();
        userAddress.setCompany(company);
        userAddress.setAddress(address);
        userAddress.setPhone(phone);
        userAddress.setEmail(email);
        return userAddress;
    }

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

    @Test
    public void shouldGetUserById() {
        final User inputPerson = buildUser("12343", buildUserType(Type.EMPLOYEE) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost");

        when (iUserRepository.findById(inputPerson.getId())).thenReturn(java.util.Optional.of(inputPerson));
        final User actualPerson = userServiceImpl.findOneUser(inputPerson.getId()).get();

        verify(iUserRepository, times(1)).findById(inputPerson.getId());
        assertEquals(inputPerson, actualPerson);
    }

    @Test
    public void shouldRetrieveAllUsers(){

        final List<User> expectedPersons = new ArrayList<User>() {{
            add(buildUser("12343", buildUserType(Type.EMPLOYEE) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost"));
            add(buildUser("12343", buildUserType(Type.AFFILIATE) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost"));
            add(buildUser("12343", buildUserType(Type.CUSTOMER) , stringToDate("22-09-2009"), "Ecommerce", "127.0.0.1", "12345", "master@localhost"));
            add(buildUser("12343", buildUserType(Type.CUSTOMER) , stringToDate("22-09-2020"), "Ecommerce", "127.0.0.1", "12345", "master@localhost"));
        }};

        when(iUserRepository.findAll()).thenReturn(expectedPersons);
        final List<User> actualPersons = userServiceImpl.findAllUsers();

        verify(iUserRepository, times(1)).findAll();
        assertEquals(expectedPersons,actualPersons);
    }
}

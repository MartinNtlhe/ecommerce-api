package com.example.ecommerce.resource;

import com.example.ecommerce.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAddressRepository extends JpaRepository<UserAddress, Long> {
}

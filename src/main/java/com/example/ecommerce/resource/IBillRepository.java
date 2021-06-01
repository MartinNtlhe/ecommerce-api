package com.example.ecommerce.resource;

import com.example.ecommerce.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUserReference(String id);
}

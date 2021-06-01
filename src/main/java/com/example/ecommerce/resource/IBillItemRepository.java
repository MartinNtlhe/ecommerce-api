package com.example.ecommerce.resource;

import com.example.ecommerce.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillItemRepository extends JpaRepository<BillItem, Long> {

}

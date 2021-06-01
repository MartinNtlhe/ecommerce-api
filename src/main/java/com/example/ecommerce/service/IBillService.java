package com.example.ecommerce.service;

import com.example.ecommerce.entity.Bill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface IBillService {
     Bill placeBill(Bill bill);
     List<Bill> findByUserReference(String reference);
     List<Bill> findAll();
     Optional<Bill> findByOne(Long id);
}


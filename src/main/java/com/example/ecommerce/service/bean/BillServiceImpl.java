package com.example.ecommerce.service.bean;

import com.example.ecommerce.entity.Bill;
import com.example.ecommerce.resource.IBillRepository;
import com.example.ecommerce.service.IBillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillServiceImpl implements IBillService {

    private final IBillRepository repository;

    public BillServiceImpl(IBillRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bill placeBill(Bill order) {
        if(order != null) {
          return repository.save(order);
        }
        return null;
    }

    @Override
    public List<Bill> findByUserReference(String reference) {
        if(reference != null) {
            return repository.findByUserReference(reference);
        }
        return null;
    }

    @Override
    public Optional<Bill> findByOne(Long id) {
        if(id != null) {
            return repository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<Bill> findAll() {
        return repository.findAll();
    }
}

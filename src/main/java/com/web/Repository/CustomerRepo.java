package com.web.Repository;

import com.web.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CustomerRepo extends CrudRepository<Customer,Long> {
    public Customer findByCustId(Long custId);
}

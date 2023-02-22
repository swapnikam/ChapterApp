package com.web.Repository;

import com.web.entity.Transaction;

import java.sql.Timestamp;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TransactionRepo extends CrudRepository<Transaction,Long> {
    public List<Transaction> findAllByCustId(Long custId);
    public List<Transaction> findAllByCustIdAndTranDateBetween(Long custId, Timestamp from, Timestamp to);

}

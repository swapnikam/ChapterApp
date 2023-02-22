package com.web.service;

import com.web.Repository.TransactionRepo;
import com.web.constants.CharterAppConstant;
import com.web.entity.Transaction;
import com.web.model.CustomerRewards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardCalculatorServiceImpl implements RewardCalculatorService{
    @Autowired
    TransactionRepo transactionRepository;

    public CustomerRewards getRewardsByCustId(Long custId) {

        Timestamp lastMonth = GetDifferenceBetweenDates(CharterAppConstant.TOTAL_DAYS);
        Timestamp lastSecMonth = GetDifferenceBetweenDates(2*CharterAppConstant.TOTAL_DAYS);
        Timestamp lastThirdMonth = GetDifferenceBetweenDates(3*CharterAppConstant.TOTAL_DAYS);

        List<Transaction> lastMonthDetails = transactionRepository.findAllByCustIdAndTranDateBetween(
                custId, lastMonth, Timestamp.from(Instant.now()));
        List<Transaction> lastSecMonthDetails = transactionRepository
                .findAllByCustIdAndTranDateBetween(custId, lastSecMonth, lastMonth);
        List<Transaction> lastThirdMonthDetails = transactionRepository
                .findAllByCustIdAndTranDateBetween(custId, lastThirdMonth,
                        lastSecMonth);

        Long lastMonthRP = getMonthlyRewards(lastMonthDetails);
        Long lastSecMonthRP = getMonthlyRewards(lastSecMonthDetails);
        Long lastThirdMonthRP = getMonthlyRewards(lastThirdMonthDetails);

        CustomerRewards custRewards = new CustomerRewards();
        custRewards.setCustId(custId);
        custRewards.setLastMothRP(lastMonthRP);
        custRewards.setLastSecMonthRP(lastSecMonthRP);
        custRewards.setLastThirdMonthRP(lastThirdMonthRP);
        custRewards.setTotalRewards(lastMonthRP + lastSecMonthRP + lastThirdMonthRP);

        return custRewards;

    }

    private Long getMonthlyRewards(List<Transaction> transactions) {
        return transactions.stream().map(transaction -> calculateRewards(transaction))
                .collect(Collectors.summingLong(r -> r.longValue()));
    }

    private Long calculateRewards(Transaction t) {
        if (t.getTranAmount() > CharterAppConstant.ONE_REWARD_LIMIT && t.getTranAmount() <= CharterAppConstant.SECOND_REWARD_LIMIT) {
            return Math.round(t.getTranAmount() - CharterAppConstant.ONE_REWARD_LIMIT);
        } else if (t.getTranAmount() > CharterAppConstant.SECOND_REWARD_LIMIT) {
            return Math.round(t.getTranAmount() - CharterAppConstant.SECOND_REWARD_LIMIT) * 2
                    + (CharterAppConstant.SECOND_REWARD_LIMIT - CharterAppConstant.ONE_REWARD_LIMIT);
        } else
            return 0l;

    }

    public Timestamp GetDifferenceBetweenDates(int days) {
        return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
    }
}

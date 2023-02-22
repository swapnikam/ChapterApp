package com.web.controller;

import com.web.Repository.CustomerRepo;
import com.web.entity.Customer;
import com.web.model.CustomerRewards;
import com.web.service.RewardCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rewards")
public class RewardCalculatorController {
    @Autowired
    RewardCalculatorService rewardsService;

    @Autowired
    CustomerRepo customerRepository;

    @GetMapping(value = "/{custId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerRewards> getRewardsByCustomerId(@PathVariable("custId") Long custId){
        Customer customer = customerRepository.findByCustId(custId);
        if(customer == null)
        {
            throw new RuntimeException("Invalid / Missing customer Id ");
        }
        CustomerRewards custRewards = rewardsService.getRewardsByCustId(custId);
        return new ResponseEntity<>(custRewards, HttpStatus.OK);
    }
}

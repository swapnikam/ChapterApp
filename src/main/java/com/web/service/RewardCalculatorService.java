package com.web.service;

import com.web.model.CustomerRewards;

public interface RewardCalculatorService {
    public CustomerRewards getRewardsByCustId(Long custId);
}

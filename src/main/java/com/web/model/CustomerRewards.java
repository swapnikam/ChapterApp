package com.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRewards {
    private long custId;
    private long lastMothRP;
    private long lastSecMonthRP;
    private long lastThirdMonthRP;
    private long totalRewards;
}

package com.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "TRANSACTION_TABLE")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "TRAN_ID")
    private Long tranId;

    @Column(name="CUST_ID")
    private Long custId;

    @Column(name = "TRAN_DATE")
    private Timestamp tranDate;

    @Column(name = "TRAN_AMOUNT")
    private double tranAmount;

}

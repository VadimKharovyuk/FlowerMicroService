package com.example.flowershop.pay;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
@Getter
@Setter
public class PaymentRequest {
    private String cardNumber;
    private BigDecimal amount;
    private Date expirationDate;
    private String cvv;


}

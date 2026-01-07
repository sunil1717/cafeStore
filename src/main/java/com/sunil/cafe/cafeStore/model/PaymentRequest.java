package com.sunil.cafe.cafeStore.model;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private double amount;
}

package com.sunil.cafe.cafeStore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("payments")
@Data
public class Payment {

    @Id
    private String id;

    private String orderId;

    private double amount;
    private PaymentStatus status;

    private LocalDateTime paidAt;
}


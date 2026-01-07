package com.sunil.cafe.cafeStore.controller;

import com.sunil.cafe.cafeStore.model.Payment;
import com.sunil.cafe.cafeStore.model.PaymentRequest;
import com.sunil.cafe.cafeStore.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {


    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/pay")
    public Map<String, Object> pay(@RequestBody PaymentRequest request) {
        return paymentService.makePayment( request.getOrderId(),request.getAmount());
    }
}

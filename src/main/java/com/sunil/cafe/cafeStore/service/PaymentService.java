package com.sunil.cafe.cafeStore.service;

import com.sunil.cafe.cafeStore.model.Order;
import com.sunil.cafe.cafeStore.model.OrderStatus;
import com.sunil.cafe.cafeStore.model.Payment;
import com.sunil.cafe.cafeStore.model.PaymentStatus;
import com.sunil.cafe.cafeStore.repository.OrderRepository;
import com.sunil.cafe.cafeStore.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {


    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


    public PaymentService(PaymentRepository paymentRepository,OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }


    public Map<String, Object> makePayment(String orderId, double amount) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.INITIATED) {
            throw new RuntimeException("Payment already done or order invalid");
        }

        if (Double.compare(order.getTotalAmount(), amount) != 0) {
            throw new RuntimeException("Payment amount mismatch");
        }

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("orderId", orderId);

        return response;
    }
}

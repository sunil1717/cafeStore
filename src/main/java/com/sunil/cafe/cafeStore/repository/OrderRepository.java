package com.sunil.cafe.cafeStore.repository;

import com.sunil.cafe.cafeStore.model.Order;
import com.sunil.cafe.cafeStore.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCafeId(String cafeId);

    List<Order> findByWaiterIdAndStatus(
            String waiterId,
            OrderStatus status
    );

    List<Order> findByWaiterId(String waiterId);

    List<Order> findByStatusAndReadyFalse(OrderStatus status);

}


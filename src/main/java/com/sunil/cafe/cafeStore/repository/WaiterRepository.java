package com.sunil.cafe.cafeStore.repository;

import com.sunil.cafe.cafeStore.model.Waiter;
import com.sunil.cafe.cafeStore.model.WaiterStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;


@Repository
public interface WaiterRepository extends MongoRepository<Waiter, String> {




    // Get all waiters of cafe
    List<Waiter> findByCafeIdAndActiveTrue(String cafeId);


    // Find least busy FREE waiter
    Optional<Waiter> findFirstByStatusAndActiveTrueOrderByActiveOrdersAsc(
            WaiterStatus status
    );

    Optional<Waiter> findByUsername(String username);

    List<Waiter> findByStatus(WaiterStatus waiterStatus);

}


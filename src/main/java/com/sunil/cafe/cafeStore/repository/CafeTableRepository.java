package com.sunil.cafe.cafeStore.repository;

import com.sunil.cafe.cafeStore.model.CafeTable;
import com.sunil.cafe.cafeStore.model.TableStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface CafeTableRepository extends MongoRepository<CafeTable, String> {







    // Find best FREE table based on person count
    Optional<CafeTable> findFirstByStatusAndCapacityGreaterThanEqualAndActiveTrueOrderByCapacityAsc(
            TableStatus status,
            int capacity
    );

    List<CafeTable> findByStatus(TableStatus status);

}

package com.sunil.cafe.cafeStore.repository;

import com.sunil.cafe.cafeStore.model.Cafe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepository extends MongoRepository<Cafe, String> {
}

package com.sunil.cafe.cafeStore.repository;


import com.sunil.cafe.cafeStore.model.MenuItem;
import com.sunil.cafe.cafeStore.model.menuCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends MongoRepository<MenuItem, String> {

    List<MenuItem> findByAvailableTrue();

    List<MenuItem> findByCategoryAndAvailableTrue(menuCategory category);
}


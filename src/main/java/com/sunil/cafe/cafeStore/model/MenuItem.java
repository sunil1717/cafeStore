package com.sunil.cafe.cafeStore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("menu_items")
@Data
public class MenuItem {

    @Id
    private String id;

    private String cafeId;

    private String name;
    private double price;
    private menuCategory category;
    private String imageUrl;

    private boolean available = true;
}


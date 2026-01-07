package com.sunil.cafe.cafeStore.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("tables")
public class CafeTable {

    @Id
    private String id;

    private String cafeId;

    private int tableNumber;
    private int capacity;

    private TableType type;      // TWO, THREE, FOUR
    private TableStatus status;  // FREE, OCCUPIED

    private boolean active = true;
}

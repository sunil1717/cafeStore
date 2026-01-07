package com.sunil.cafe.cafeStore.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document("cafes")
@Data
public class Cafe {

    @Id
    private String id;

    private String name;
    private String openingTime;
    private String closingTime;

    private LocalDateTime createdAt;
}

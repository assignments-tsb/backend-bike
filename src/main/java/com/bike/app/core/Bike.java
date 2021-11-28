package com.bike.app.core;

import lombok.*;

import java.time.LocalDateTime;

@With @NoArgsConstructor @AllArgsConstructor
@Getter
public class Bike {

    String id;
    String label;

    String customerName;
    LocalDateTime dateRentedOut;
    LocalDateTime dateReturned;

}

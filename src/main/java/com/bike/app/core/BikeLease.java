package com.bike.app.core;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BikeLease {

    Bike bike;
    Customer customer;
    LocalDateTime checkOutTime;
    LocalDateTime checkInTime;
    LocalDateTime dateModified;
}
